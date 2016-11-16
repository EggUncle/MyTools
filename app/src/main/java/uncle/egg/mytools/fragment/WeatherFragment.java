package uncle.egg.mytools.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uncle.egg.mytools.R;
import uncle.egg.mytools.adapter.WeatherAdapter;
import uncle.egg.mytools.entities.WeatherList;
import uncle.egg.mytools.entities.weather.Daily_forecast;
import uncle.egg.mytools.entities.weather.Root;
import uncle.egg.mytools.unities.NetWorkUnit;
import uncle.egg.mytools.unities.SPUtils;


//天气数据来源  http://apistore.baidu.com/apiworks/servicedetail/478.html
public class WeatherFragment extends Fragment {

    private View viewParent;
    private Context mContext;


    private ContentLoadingProgressBar progressBar;
    private RecyclerView rcvWeather;
    private FloatingActionButton fabAdd;
    private TextView tvPoint;
    private WeatherAdapter mWeatherAdapter;
    private List<Root> mWeatherData;
    private NetWorkUnit mNetWorkUnit;

    private View viewCityDialog;
    private EditText editCity;

    private String defaultCityName = "北京";
    // 完整的URL
    // private String strUrl = "http://v.juhe.cn/weather/index?format=2&cityname=%E5%AE%9C%E6%98%A5&key=ff1860c2a0255fea2cb737793f45c4fb";

    public WeatherFragment() {

    }

    public WeatherFragment(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewParent = inflater.inflate(R.layout.frag_weather, container, false);
        initVar();
        initView();


        SPUtils spUtils = SPUtils.getInstance(mContext);
        if (spUtils.getCities().size() != 0) {
            tvPoint.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        for (Iterator iterator = spUtils.getCities().iterator(); iterator.hasNext(); ) {
            setWeather(iterator.next().toString());
        }

        return viewParent;
    }


    private void initView() {

        progressBar = (ContentLoadingProgressBar) viewParent.findViewById(R.id.progress_bar);
        rcvWeather = (RecyclerView) viewParent.findViewById(R.id.rcv_weather);
        fabAdd = (FloatingActionButton) viewParent.findViewById(R.id.fab_add);
        tvPoint = (TextView) viewParent.findViewById(R.id.tv_point);


        rcvWeather.setLayoutManager(new LinearLayoutManager(mContext));
        rcvWeather.setHasFixedSize(true);
        rcvWeather.setAdapter(mWeatherAdapter);
        rcvWeather.setItemAnimator(new DefaultItemAnimator());


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCityDialog = LayoutInflater.from(mContext).inflate(R.layout.dialog_change_city, null);
                editCity = (EditText) viewCityDialog.findViewById(R.id.ed_city);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("设置城市").setView(viewCityDialog).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // setWeatherMessage(editCity.getText().toString(), key);
                        SPUtils spUtils = SPUtils.getInstance(mContext);
                        spUtils.saveCity(editCity.getText().toString());
                        setWeather(editCity.getText().toString());
                    }
                });
                builder.create().show();
            }
        });

    }

//    private void setWeatherData(String cityName) {
//        Observable.just(cityName)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        mWeatherAdapter.addItem(WeatherList.getLastRoot());
//                    }
//                });
//    }

    private void initVar() {
        mNetWorkUnit = new NetWorkUnit(mContext);
        mWeatherData = new ArrayList<>();
        mWeatherAdapter = new WeatherAdapter(mContext, mWeatherData);
    }

    private void setWeather(String cityName) {
        Parameters para = new Parameters();

        para.put("city", cityName);
        ApiStoreSDK.execute(NetWorkUnit.URL_WEATHER, ApiStoreSDK.GET, para,
                new ApiCallBack() {
                    @Override
                    public void onSuccess(int i, String json) {
                        Gson gson = new Gson();
                        Root root = gson.fromJson(json, Root.class);
                        if (root != null && root.getHeWeather().get(0).getStatus().equals("ok")) {
                            mWeatherAdapter.addItem(root);

                            tvPoint.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            rcvWeather.setVisibility(View.VISIBLE);
                            //  Log.v("MY_TAG",root.getHeWeather().get(0).getBasic().getCity());
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(int i, String s, Exception e) {
                        super.onError(i, s, e);
                    }
                });
    }


}
