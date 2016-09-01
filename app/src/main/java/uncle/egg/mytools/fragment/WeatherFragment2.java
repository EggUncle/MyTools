package uncle.egg.mytools.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;

import java.util.List;

import uncle.egg.mytools.R;
import uncle.egg.mytools.model.weather.Daily_forecast;
import uncle.egg.mytools.model.weather.Now;
import uncle.egg.mytools.model.weather.Root;

/**
 * Created by egguncle on 16.9.2.
 * 改良后的weather界面
 */


public class WeatherFragment2 extends Fragment {

    private View viewParent;
    private Context context;


    private List<Daily_forecast> listDailyForecast;

    private SwipeRefreshLayout rshWeather2;
    private TextView tvTemperature;
    private TextView tvCity;
    private TextView tvWeahter;
    private TextView tvTemperatureMin;
    private TextView tvTemperatureMax;


    private String nowCityInFrg;

    private String defaultCityName = "北京";
    // 完整的URL
    // private String strUrl = "http://v.juhe.cn/weather/index?format=2&cityname=%E5%AE%9C%E6%98%A5&key=ff1860c2a0255fea2cb737793f45c4fb";


    public WeatherFragment2() {

    }

    public WeatherFragment2(Context context) {
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewParent = inflater.inflate(R.layout.frag_weather2, container, false);
        init();
        return viewParent;
    }


    private void init() {
        rshWeather2 = (SwipeRefreshLayout) viewParent.findViewById(R.id.rsh_weather_2);
        tvTemperature = (TextView) viewParent.findViewById(R.id.tv_temperature);
        tvCity = (TextView) viewParent.findViewById(R.id.tv_city);
        tvWeahter = (TextView) viewParent.findViewById(R.id.tv_weahter);
        tvTemperatureMin = (TextView) viewParent.findViewById(R.id.tv_temperature_min);
        tvTemperatureMax = (TextView) viewParent.findViewById(R.id.tv_temperature_max);

        rshWeather2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rshWeather2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if ("".equals(nowCityInFrg)) {
                            nowCityInFrg = defaultCityName;
                        }
                        getWeatherMessage(nowCityInFrg);
                        Log.v("MY_TAG", "刷新,城市为:" + nowCityInFrg);
                        rshWeather2.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        rshWeather2.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));


        getWeatherMessage(defaultCityName);
    }

    /**
     * @param cityName 调用百度APIStore的API，获取天气信息
     */
    private void getWeatherMessage(String cityName) {
        Parameters para = new Parameters();
        para.put("city", cityName);
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free", ApiStoreSDK.GET, para,
                new ApiCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {  //请求成功时调用
                        //  txtWeatherMessage.setText(s);
                        String json = s;
                        Gson gson = new Gson();
                        Root root = gson.fromJson(json, Root.class);
                        Now now = root.getHeWeather().get(0).getNow();
                        if (root != null && now != null) {
                            tvTemperature.setText(now.getTmp());
                            tvCity.setText(root.getHeWeather().get(0).getBasic().getCity());
                            tvWeahter.setText(now.getCond().getTxt_d());

                            if (root.getHeWeather().get(0).getDaily_forecast().get(0).getTmp() != null) {
                                tvTemperatureMin.setText(root.getHeWeather().get(0).getDaily_forecast().get(0).getTmp().getMin());
                                tvTemperatureMax.setText(root.getHeWeather().get(0).getDaily_forecast().get(0).getTmp().getMax());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {    //总是会调用
                        super.onComplete();
                    }

                    @Override
                    public void onError(int i, String s, Exception e) { //请求失败时调用
                        Log.v("MY_TAG", "Error!!!!! ");
                    }
                }
        );
    }

    public void startRshWeather(String nowCity) {
        nowCityInFrg = nowCity;
        rshWeather2.setRefreshing(true);
        getWeatherMessage(nowCity);
        rshWeather2.setRefreshing(false);
    }

}
