/**
 * Javascript file for weather
 */
$(document).ready(function(){
    var promise;
    promise=getWeatherData();
    
    promise.done(function(data){
        console.log(data);
        let weather=data.weather;
        let main=data.main;
        let sys=data.sys;
        $("#time").text(new Date().toLocaleString());
        $("#weather").text(weather[0].main +" : "+weather[0].description);
        $("#weather-image").html("<img src='http://openweathermap.org/img/w/"+weather[0].icon+".png' title='"+weather[0].description+"'>");
        $("#temprature").text(main.temp-273.15);
        $("#min-temprature").text(main.temp_min-273.15);
        $("#max-temprature").text(main.temp_max-273.15);
        $("#pressure").text(main.pressure);
        $("#humidity").text(main.humidity);
        $("#visibility").text(data.visibility);
        $("#wind").text(data.wind.speed);
        $("#rise").text(getDate(sys.sunrise));
        $("#set").text(getDate(sys.sunset));
        promise=getWeatherData();
    });
    promise.fail(function(){
       console.log('failure');
       promise=getWeatherData();
    });
    
});

function getWeatherData(){
    var appWeatherURl='http://localhost:8080/async/weather';
    var promise= $.ajax({method:'GET',url:appWeatherURl, beforeSend:function(){console.log('sending request');}});
    return promise;
}

function getDate(second){
    return new Date(second*1000).toString();
}