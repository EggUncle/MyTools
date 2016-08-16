package uncle.egg.mytools.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    private View viewParent;
    private Context context;
    private View viewDialog;

    private EditText editCity;
    private Button btnChange;
    private TextView txtWeatherMessage;
    // 完整的URL
    // private String strUrl = "http://v.juhe.cn/weather/index?format=2&cityname=%E5%AE%9C%E6%98%A5&key=ff1860c2a0255fea2cb737793f45c4fb";
    private String strCity;
    private static String key = "ff1860c2a0255fea2cb737793f45c4fb";


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
                        setWeatherMessage(editCity.getText().toString(), key);
                    }
                });
                builder.create().show();
            }
        });
    }
//
//    private String getCity() {
//        strCity = editCity.getText().toString();
//        return strCity;
//    }

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
