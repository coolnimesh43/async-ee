
package org.coolnimesh.async.serviceImpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.coolnimesh.async.resource.WeatherResource;
import org.coolnimesh.async.service.WeatherDataService;
import org.coolnimesh.async.service.WeatherService;

@Singleton
@Startup
public class WeatherServiceImpl implements WeatherService {

    @Resource
    private TimerService timerService;

    @Inject
    @Default
    private WeatherDataService weatherDataService;

    private WeatherResource weatherResource;

    @PostConstruct
    public void setTimer() {
        this.timerService.createIntervalTimer(2000, 600000, new TimerConfig());
    }

    @Override
    public void setWeatherResource(WeatherResource resource) {
        this.weatherResource = resource;

    }

    @Timeout
    private void setWeatherValues() {
        this.weatherResource.sendData(this.weatherDataService.getWeatherData().toString());
    }
}
