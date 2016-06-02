
package org.coolnimesh.async.serviceImpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;
import org.coolnimesh.async.resource.WeatherResource;
import org.coolnimesh.async.service.WeatherDataService;
import org.coolnimesh.async.service.WeatherService;

@Singleton
@Startup
public class WeatherServiceImpl implements WeatherService {

    @Resource
    private TimerService timerService;

    @Inject
    private Logger logger;

    @Inject
    @Default
    private WeatherDataService weatherDataService;

    private WeatherResource weatherResource;

    @PostConstruct
    public void setTimer() {
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setPersistent(Boolean.FALSE);
        this.timerService.createCalendarTimer(new ScheduleExpression().hour("*").minute("0,10,20,30,40,50").second("*"), timerConfig);
    }

    @Override
    public void setWeatherResource(WeatherResource resource) {
        this.weatherResource = resource;

    }

    @Timeout
    private void setWeatherValues(Timer timer) {
        logger.debug("Setting weather values. Timeout occurred . Info is: {}", timer.getNextTimeout());
        if (this.weatherResource != null && !this.weatherResource.getAsyncContextQueue().isEmpty()) {
            String weatherData = this.weatherDataService.getWeatherData().toString();
            logger.debug("Weather data is: {}", weatherData);
            this.weatherResource.sendData(weatherData);
        }
    }
}
