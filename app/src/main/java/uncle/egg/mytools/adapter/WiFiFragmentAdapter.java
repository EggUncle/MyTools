package uncle.egg.mytools.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uncle.egg.mytools.R;

/**
 * Created by egguncle on 16.8.16.
 */
public class WiFiFragmentAdapter extends RecyclerView.Adapter<WiFiFragmentAdapter.ViewHolder> {
    private Context context;
    private List<ScanResult> listWifi;

    public WiFiFragmentAdapter(Context context, List<ScanResult> listWifi) {
        this.context = context;
        this.listWifi = listWifi;
    }

    @Override
    public WiFiFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_wifi, parent, false));
    }


    @Override
    public void onBindViewHolder(final WiFiFragmentAdapter.ViewHolder holder, int position) {
        holder.tvItemWifiSsid.setText(listWifi.get(position).SSID);
        holder.tvItemWifiBssid.setText(listWifi.get(position).BSSID);
        holder.tvItemWifiCapabilites.setText(listWifi.get(position).capabilities);
        holder.tvItemWifiLevel.setText(listWifi.get(position).level+"");
        holder.tvItemWifiFrequency.setText(listWifi.get(position).frequency+"");
    }

    @Override
    public int getItemCount() {
        return listWifi == null ? 0 : listWifi.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvItemWifiSsid;
        private TextView tvItemWifiBssid;
        private TextView tvItemWifiCapabilites;
        private TextView tvItemWifiLevel;
        private TextView tvItemWifiFrequency;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            tvItemWifiSsid = (TextView) itemView.findViewById(R.id.tv_item_wifi_ssid);
            tvItemWifiBssid = (TextView) itemView.findViewById(R.id.tv_item_wifi_bssid);
            tvItemWifiCapabilites = (TextView) itemView.findViewById(R.id.tv_item_wifi_capabilites);
            tvItemWifiLevel = (TextView) itemView.findViewById(R.id.tv_item_wifi_level);
            tvItemWifiFrequency = (TextView) itemView.findViewById(R.id.tv_item_wifi_frequency);

        }
    }
}
