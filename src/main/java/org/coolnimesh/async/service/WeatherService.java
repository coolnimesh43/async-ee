
package org.coolnimesh.async.service;

import org.coolnimesh.async.resource.WeatherResource;

public interface WeatherService {

    /**
     * Set the weather resource. Need to access the resource servlet to pass
     * weather data.
     * 
     * @author nimesh
     * @param resource
     *            {@link WeatherResource} The weather servlet.
     */
    public void setWeatherResource(WeatherResource resource);

}
