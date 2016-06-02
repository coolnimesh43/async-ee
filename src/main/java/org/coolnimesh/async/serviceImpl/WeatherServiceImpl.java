
package org.coolnimesh.async.serviceImpl;

import java.util.Date;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;
import org.coolnimesh.async.resource.WeatherResource;
import org.coolnimesh.async.service.WeatherDataService;
import org.coolnimesh.async.service.WeatherService;

@Singleton
@Startup
public class WeatherServiceImpl implements WeatherService {

    // @Resource
    // private TimerService timerService;

    @Inject
    private Logger logger;

    @Inject
    private WeatherDataService weatherDataService;

    private WeatherResource weatherResource;

    // @PostConstruct
    // public void setTimer() {
    // TimerConfig timerConfig = new TimerConfig();
    // timerConfig.setPersistent(Boolean.FALSE);
    // this.timerService.createIntervalTimer(5000, 300000, timerConfig);
    // }

    @Override
    public void setWeatherResource(WeatherResource resource) {
        this.weatherResource = resource;

    }

    // @Timeout
    @Schedule(hour = "*", minute = "*/5", persistent = false)
    private void setWeatherValues() {
        logger.debug("Setting weather values. Timeout occurred . Date is: {}", new Date());
        if (this.weatherResource != null) {
            String weatherData = this.weatherDataService.getWeatherData().toString();
            logger.debug("Weather data is: {}", weatherData);
            this.weatherResource.sendData(weatherData);
        }
    }
}
