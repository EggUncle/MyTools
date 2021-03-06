package uncle.egg.mytools.entities.weather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by egguncle on 16.8.20.
 */
public class Root implements Serializable {
    @SerializedName("HeWeather data service 3.0")
    private List<HeWeather> heWeatherList;

    public void setHeWeather(List<HeWeather> HeWeather){
        this.heWeatherList = HeWeather;
    }
    public List<HeWeather> getHeWeather(){
        return this.heWeatherList;
    }
}