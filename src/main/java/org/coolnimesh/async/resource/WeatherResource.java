
package org.coolnimesh.async.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.Logger;
import org.coolnimesh.async.qualifiers.OpenWeatherMap;
import org.coolnimesh.async.service.WeatherService;

@WebServlet(urlPatterns = { "/weather" }, asyncSupported = true)
public class WeatherResource extends HttpServlet {

    private Queue<AsyncContext> asyncContextQueue = null;

    @Inject
    @OpenWeatherMap
    private WeatherService weatherService;

    @Inject
    private Logger logger;

    @Override
    public void init()
        throws ServletException {
        this.asyncContextQueue = new ConcurrentLinkedQueue<>();
        this.weatherService.setWeatherResource(this);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        logger.debug("Inside doGet method.");
        resp.setContentType(MediaType.APPLICATION_JSON.toString());

        final AsyncContext requestAsyncContext = req.startAsync();
        requestAsyncContext.addListener(new AsyncListener() {

            @Override
            public void onTimeout(AsyncEvent event)
                throws IOException {
                asyncContextQueue.remove(requestAsyncContext);
            }

            @Override
            public void onStartAsync(AsyncEvent event)
                throws IOException {
                asyncContextQueue.remove(requestAsyncContext);
            }

            @Override
            public void onError(AsyncEvent event)
                throws IOException {
                asyncContextQueue.remove(requestAsyncContext);
            }

            @Override
            public void onComplete(AsyncEvent event)
                throws IOException {
                asyncContextQueue.remove(requestAsyncContext);
            }
        });
        this.asyncContextQueue.add(requestAsyncContext);

        logger.debug("Request queue is: {}", this.asyncContextQueue.size());
    }

    public void sendData(String data) {
        for (AsyncContext context : asyncContextQueue) {
            try {
                PrintWriter writer = context.getResponse().getWriter();
                writer.write(data);
                context.complete();
            }
            catch (IOException e) {
                logger.error("Inside WeatherService#sendData while writing data. Exception is: {}", e);
            }
        }
    }

    public Queue<AsyncContext> getAsyncContextQueue() {
        return asyncContextQueue;
    }

    public void setAsyncContextQueue(Queue<AsyncContext> asyncContextQueue) {
        this.asyncContextQueue = asyncContextQueue;
    }

}
