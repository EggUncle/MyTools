package uncle.egg.mytools.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import uncle.egg.mytools.R;
import uncle.egg.mytools.activity.MyApplication;


public class WeatherFragment extends Fragment {

    private View viewLayout;

    private EditText editCity;
    private Button btnSelect;
    private TextView txtWeatherMessage;
    // 完整的URL
    // private String strUrl = "http://v.juhe.cn/weather/index?format=2&cityname=%E5%AE%9C%E6%98%A5&key=ff1860c2a0255fea2cb737793f45c4fb";
    private String strCity;
    private static String key = "ff1860c2a0255fea2cb737793f45c4fb";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.fragment_weather, null);
        init();

        return viewLayout;
    }


    private void init() {
        editCity = (EditText) viewLayout.findViewById(R.id.edit_city);
        btnSelect = (Button) viewLayout.findViewById(R.id.btn_select);
        txtWeatherMessage = (TextView) viewLayout.findViewById(R.id.txt_weather_message);


        MyClick myClick = new MyClick();

        btnSelect.setOnClickListener(myClick);
    }

    private String getCity() {
        strCity = editCity.getText().toString();
        return strCity;
    }

    private class MyClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_select: {
                    setWeatherMessage(getCity(), key);
                }
            }
        }
    }

    private void setWeatherMessage(String city, String key) {
        String url = "http://v.juhe.cn/weather/index?format=2&cityname=" + city + "&key=" + key;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //   String json = null;
                        String resultCode = null;
                        String strWeatherMessage = null;
                        JSONObject json;
                        try {

                            resultCode = jsonObject.getString("resultcode");

                            if (!"200".equals(resultCode)) {
                                //若返回码不200，说明请求失败
                                txtWeatherMessage.setText("connection fail");
                                return;
                            }

                            json = jsonObject.getJSONObject("result").getJSONObject("today");
                            String weather = json.getString("weather");
                            String temperature = json.getString("temperature");
                            String city = json.getString("city");
                            strWeatherMessage="城市："+city+"\n"+"天气："+weather+"\n"+"温度："+temperature;
                            txtWeatherMessage.setText(strWeatherMessage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        txtWeatherMessage.setText("访问失败!");
                        //   text.setText(volleyError.toString());
                    }
                });

        request.setTag("weatherGet");
        MyApplication.getHttpQueues().add(request);

    }


}
