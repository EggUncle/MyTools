package uncle.egg.mytools.entities;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import uncle.egg.mytools.entities.weather.Root;

/**
 * Created by egguncle on 16-11-16.
 */

public class WeatherList {
    private static List<Root> mWeatherList;
    private static Context mContext;
    private static WeatherList instance;

    private WeatherList() {

    }

    public static WeatherList getInstance(Context context) {
        mContext = context;
        if (mWeatherList == null) {
            synchronized (WeatherList.class) {
                mWeatherList = new ArrayList<>();
                instance = new WeatherList();
            }
        }
        return instance;
    }

    public static void addWeatherItem(Root r){
        mWeatherList.add(r);
    }

    public static List<Root> getWeatherList(){
        return mWeatherList;
    }

    public static Root getLastRoot(){
        if (mWeatherList==null||mWeatherList.size()==0){
            return null;
        }
        return mWeatherList.get(mWeatherList.size()-1);
    }
}