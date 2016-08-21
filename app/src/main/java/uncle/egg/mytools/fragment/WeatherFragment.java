package uncle.egg.mytools.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;

import java.util.List;

import uncle.egg.mytools.R;
import uncle.egg.mytools.adapter.WeatherFragmentAdapter;
import uncle.egg.mytools.model.weather.Daily_forecast;
import uncle.egg.mytools.model.weather.Root;


//天气数据来源  http://apistore.baidu.com/apiworks/servicedetail/478.html
public class WeatherFragment extends Fragment {

    private View viewParent;
    private Context context;
    private View viewDialog;

    private EditText editCity;
    private Button btnChange;
    private TextView txtWeatherMessage;
    private RecyclerView rcvWeather;
    private WeatherFragmentAdapter weatherFragmentAdapter;
    private List<Daily_forecast> listDailyForecast;

    private String defaultCityName = "北京";
    // 完整的URL
    // private String strUrl = "http://v.juhe.cn/weather/index?format=2&cityname=%E5%AE%9C%E6%98%A5&key=ff1860c2a0255fea2cb737793f45c4fb";

    public WeatherFragment(){

    }

    public WeatherFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewParent = inflater.inflate(R.layout.frag_weather, container,false);
        init();
        return viewParent;
    }


    private void init() {

        rcvWeather = (RecyclerView) viewParent.findViewById(R.id.rcv_weather);


        getWeatherMessage(defaultCityName);
        btnChange = (Button) viewParent.findViewById(R.id.btn_weather_change);
        txtWeatherMessage = (TextView) viewParent.findViewById(R.id.tv_weather_message);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDialog = LayoutInflater.from(context).inflate(R.layout.dialog_change_city,null);
                editCity = (EditText) viewDialog.findViewById(R.id.ed_city);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("设置城市").setView(viewDialog).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // setWeatherMessage(editCity.getText().toString(), key);
                        getWeatherMessage(editCity.getText().toString());

                    }
                });
                builder.create().show();
            }
        });
    }

    /**
     * @param cityName
     *
     * 调用百度APIStore的API，获取天气信息
     */
    private void getWeatherMessage(String cityName) {
        Parameters para = new Parameters();
        para.put("city", cityName);
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free", ApiStoreSDK.GET, para,
                new ApiCallBack(){
                    @Override
                    public void onSuccess(int i, String s) {  //请求成功时调用
                        //  txtWeatherMessage.setText(s);
                        String json = s;
                        Gson gson = new Gson();
                        Root root = gson.fromJson(json, Root.class);
                        listDailyForecast = root.getHeWeather().get(0).getDaily_forecast();
                        if(weatherFragmentAdapter==null){
                            txtWeatherMessage.setText(root.getHeWeather().get(0).getBasic().getCity());
                            weatherFragmentAdapter = new WeatherFragmentAdapter(context, listDailyForecast);
                            rcvWeather.setAdapter(weatherFragmentAdapter);
                        }else{
                            txtWeatherMessage.setText(root.getHeWeather().get(0).getBasic().getCity());
                            weatherFragmentAdapter.notifyDataSetChanged();
                            rcvWeather.setAdapter(weatherFragmentAdapter);
                        }
                       // weatherFragmentAdapter.notifyDataSetChanged();
                        //txtWeatherMessage.setText(root.getHeWeather().get(0).getDaily_forecast().get(0).getDate());
                    }

                    @Override
                    public void onComplete() {    //总是会调用
                        super.onComplete();
                    }

                    @Override
                    public void onError(int i, String s, Exception e) { //请求失败时调用
                        txtWeatherMessage.setText(s);
                    }
                }
        );

    }


}
