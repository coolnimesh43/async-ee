
package org.coolnimesh.async.serviceImpl;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.coolnimesh.async.service.WeatherDataService;
import org.coolnimesh.async.util.ConfigurationProperties;

@Stateless
public class OpenWeatherMapServiceImpl implements WeatherDataService {

    private Client client;
    private WebTarget webTarget;

    private ConfigurationProperties configs = ConfigurationProperties.getInstance();

    @PostConstruct
    public void setProperties() {
        String openWeatherMapApiUrl = this.buildOpenWeatherMapUrl();
        this.client = ClientBuilder.newClient();
        this.webTarget = this.client.target(openWeatherMapApiUrl);
    }

    @Override
    public String getWeatherData() {
        Response response = this.webTarget.request().get();
        return response.readEntity(String.class);
    }

    private String buildOpenWeatherMapUrl() {
        StringBuilder urlBuilder = new StringBuilder(this.configs.getProperty("open-weather-api-url").toString());
        urlBuilder.append("?appId=");
        urlBuilder.append(this.configs.getProperty("open-weather-api-key").toString());
        urlBuilder.append("&id=");
        urlBuilder.append(this.configs.getProperty("open-weather-city-coord"));
        return urlBuilder.toString();

    }

}
