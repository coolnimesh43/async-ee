
package org.coolnimesh.async.service;

/**
 * Public interface to get weather data.
 * 
 * @author nimesh
 */
public interface WeatherDataService {

    /**
     * Method to get weather data. The Class implementing this interface must
     * override this method and return the data as json string.
     * 
     * @author nimesh
     * @return {@link String} The json String data.
     */
    public String getWeatherData();
}
