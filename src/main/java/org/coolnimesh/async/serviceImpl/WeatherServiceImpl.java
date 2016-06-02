
package org.coolnimesh.async.serviceImpl;

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

    @Inject
    private Logger logger;

    @Inject
    private WeatherDataService weatherDataService;

    private WeatherResource weatherResource;

    @Override
    public void setWeatherResource(WeatherResource resource) {
        this.weatherResource = resource;

    }

    @Schedule(hour = "*", minute = "*/5", persistent = false)
    private void setWeatherData() {
        logger.debug("Inside WeatherServiceImpl#setWeatherData method.");
        if (this.weatherResource != null) {
            String weatherData = this.weatherDataService.getWeatherData().toString();
            logger.debug("Weather data is: {}", weatherData);
            this.weatherResource.sendData(weatherData);
        }
    }
}
