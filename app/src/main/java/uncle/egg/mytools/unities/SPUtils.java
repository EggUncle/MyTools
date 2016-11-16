package uncle.egg.mytools.unities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by egguncle on 16-11-7.
 */

public class SPUtils {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SPUtils instance;
    private static final String SP_NAME = "cityInfo";
    private static final String CITY = "city";
    private static Set<String> cityData;
    private static Set<String> deafultData;


    private SPUtils() {
        // deafultData=new HashSet<>();
        //  deafultData.add("北京");
        //  cityData=mSharedPreferences.getStringSet(CITY,deafultData);
    }

    public static SPUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SPUtils.class) {
                instance = new SPUtils();
                mSharedPreferences = context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
                mEditor = mSharedPreferences.edit();
                cityData = mSharedPreferences.getStringSet(CITY, new HashSet<String>());
                //  cityData = new HashSet<>();
            }
        }
        return instance;
    }


    public void saveCity(String cityName) {
        cityData.add(cityName);
        mEditor.putStringSet(CITY, cityData);
        mEditor.commit();
    }

    public Set<String> getCities() {
        return mSharedPreferences.getStringSet(CITY, cityData);
    }

    public void detelData(String cityName) {
        cityData.remove(cityName);

        Log.v("MY_TAG", cityName + "被删除了");

        Log.v("MY_TAG", cityData.size() + " 数据长度为");


        mEditor.putStringSet(CITY, cityData);

        Log.v("MY_TAG", getCities().size() + "保存的 数据长度为");
        mEditor.commit();
    }

}
