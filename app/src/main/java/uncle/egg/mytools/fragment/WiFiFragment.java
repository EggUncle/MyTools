package uncle.egg.mytools.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uncle.egg.mytools.R;
import uncle.egg.mytools.adapter.WiFiFragmentAdapter;
import uncle.egg.mytools.entities.WifiAdmin;

/**
 * Created by egguncle on 16.8.16.
 * 查看周围wifi强度
 */
public class WiFiFragment extends Fragment {
    private View parentView;
    private RecyclerView rcv_wifi;
    private Context context;
    private TextView tvWifi;
    private WiFiFragmentAdapter wiFiFragmentAdapter;
    private WifiAdmin wifiAdmin;
    private SwipeRefreshLayout rshWifi;

    public WiFiFragment() {

    }

    public WiFiFragment(Context context) {
        this.context = context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.frag_wifi, null);
        initView();
        initVars();
        return parentView;
    }

    private void initView(){
      //  tvWifi = (TextView) parentView.findViewById(R.id.tv_wifi);
        rcv_wifi = (RecyclerView) parentView.findViewById(R.id.rcv_wifi);
        rshWifi = (SwipeRefreshLayout) parentView.findViewById(R.id.rsh_wifi);
        rshWifi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rshWifi.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wifiAdmin.StartScan();
                        wiFiFragmentAdapter = new WiFiFragmentAdapter(context,wifiAdmin.GetWifiList());
                        rcv_wifi.setAdapter(wiFiFragmentAdapter);
                        Log.v("MY_TAG", "刷新 wifi");
                        rshWifi.setRefreshing(false);
                    }
                },2000);
            }
        });

        rshWifi.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
    }

    private void initVars(){
        wifiAdmin = new WifiAdmin(context);
        wifiAdmin.StartScan();
        wiFiFragmentAdapter = new WiFiFragmentAdapter(context,wifiAdmin.GetWifiList());
        rcv_wifi.setAdapter(wiFiFragmentAdapter);
    //    tvWifi.setText(wifiAdmin.LookUpScan());
    }

}
