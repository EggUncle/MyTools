package uncle.egg.mytools.model.weather;

import java.util.List;

/**
 * Created by egguncle on 16.8.20.
 */
public class Root {
    private List<HeWeather> heWeatherList;

    public void setHeWeather(List<HeWeather> HeWeather){
        this.heWeatherList = HeWeather;
    }
    public List<HeWeather> getHeWeather(){
        return this.heWeatherList;
    }
}