package uncle.egg.mytools.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uncle.egg.mytools.R;
import uncle.egg.mytools.model.weather.Daily_forecast;

/**
 * Created by egguncle on 16.8.21.
 */
public class WeatherFragmentAdapter extends RecyclerView.Adapter<WeatherFragmentAdapter.ViewHolder> {
    private Context context;
    private List<Daily_forecast> listDailyForecast;

    public WeatherFragmentAdapter(Context context, List<Daily_forecast> listDailyForecast) {
        this.context = context;
        this.listDailyForecast = listDailyForecast;
    }

    @Override
    public WeatherFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false));
    }


    @Override
    public void onBindViewHolder(final WeatherFragmentAdapter.ViewHolder holder, int position) {
        holder.tvItemWeatherDate.setText(listDailyForecast.get(position).getDate());
        holder.tvItemWeatherAstroSr.setText(listDailyForecast.get(position).getAstro().getSr());
        holder.tvItemWeatherAstroSs.setText(listDailyForecast.get(position).getAstro().getSs());
        holder.tvItemWeatherCondD.setText(listDailyForecast.get(position).getCond().getTxt_d());
        holder.tvItemWeatherCondN.setText(listDailyForecast.get(position).getCond().getTxt_n());
        holder.tvItemWeatherHum.setText(listDailyForecast.get(position).getHum());
        holder.tvItemWeatherPcpn.setText(listDailyForecast.get(position).getPcpn());
        holder.tvItemWeatherTmpMin.setText(listDailyForecast.get(position).getTmp().getMin());
        holder.tvItemWeatherTmpMax.setText(listDailyForecast.get(position).getTmp().getMax());
        holder.tvItemWeatherVis.setText(listDailyForecast.get(position).getVis());
        holder.tvItemWeatherWindDeg.setText(listDailyForecast.get(position).getWind().getDeg());
        holder.tvItemWeatherWindDir.setText(listDailyForecast.get(position).getWind().getDir());
        holder.tvItemWeatherWindSc.setText(listDailyForecast.get(position).getWind().getSc());
        holder.tvItemWeatherWindSpd.setText(listDailyForecast.get(position).getWind().getSpd());
    }

    @Override
    public int getItemCount() {
        //    return 1;
        return listDailyForecast == null ? 0 : listDailyForecast.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvWeather;
        private TextView tvItemWeatherDate;
        private TextView tvItemWeatherAstroSr;
        private TextView tvItemWeatherAstroSs;
        private TextView tvItemWeatherCondD;
        private TextView tvItemWeatherCondN;
        private TextView tvItemWeatherHum;
        private TextView tvItemWeatherPcpn;
        private TextView tvItemWeatherTmpMax;
        private TextView tvItemWeatherTmpMin;
        private TextView tvItemWeatherVis;
        private TextView tvItemWeatherWindDeg;
        private TextView tvItemWeatherWindDir;
        private TextView tvItemWeatherWindSc;
        private TextView tvItemWeatherWindSpd;


        public ViewHolder(View itemView) {
            super(itemView);
            cvWeather = (CardView) itemView.findViewById(R.id.cv_weather);
            tvItemWeatherDate = (TextView) itemView.findViewById(R.id.tv_item_weather_date);
            tvItemWeatherAstroSr = (TextView) itemView.findViewById(R.id.tv_item_weather_astro_sr);
            tvItemWeatherAstroSs = (TextView) itemView.findViewById(R.id.tv_item_weather_astro_ss);
            tvItemWeatherCondD = (TextView) itemView.findViewById(R.id.tv_item_weather_cond_d);
            tvItemWeatherCondN = (TextView) itemView.findViewById(R.id.tv_item_weather_cond_n);
            tvItemWeatherHum = (TextView) itemView.findViewById(R.id.tv_item_weather_hum);
            tvItemWeatherPcpn = (TextView) itemView.findViewById(R.id.tv_item_weather_pcpn);
            tvItemWeatherTmpMax = (TextView) itemView.findViewById(R.id.tv_item_weather_tmp_max);
            tvItemWeatherTmpMin = (TextView) itemView.findViewById(R.id.tv_item_weather_tmp_min);
            tvItemWeatherVis = (TextView) itemView.findViewById(R.id.tv_item_weather_vis);
            tvItemWeatherWindDeg = (TextView) itemView.findViewById(R.id.tv_item_weather_wind_deg);
            tvItemWeatherWindDir = (TextView) itemView.findViewById(R.id.tv_item_weather_wind_dir);
            tvItemWeatherWindSc = (TextView) itemView.findViewById(R.id.tv_item_weather_wind_sc);
            tvItemWeatherWindSpd = (TextView) itemView.findViewById(R.id.tv_item_weather_wind_spd);

        }
    }
}