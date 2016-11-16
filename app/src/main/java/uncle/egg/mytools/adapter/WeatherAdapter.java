package uncle.egg.mytools.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import uncle.egg.mytools.R;
import uncle.egg.mytools.entities.weather.HeWeather;
import uncle.egg.mytools.entities.weather.Root;
import uncle.egg.mytools.unities.SPUtils;

/**
 * Created by egguncle on 16-11-7.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private Context mContext;
    private List<Root> mListData;

    public WeatherAdapter(Context context, List<Root> listData) {
        mContext = context;
        mListData = listData;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_weather, parent, false));
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, final int position) {
        HeWeather heWeather = mListData.get(position).getHeWeather().get(0);
        holder.tvCity.setText(heWeather.getBasic().getCity());
        holder.tvWeahter.setText(heWeather.getNow().getCond().getTxt_d());
        holder.tvTemperature.setText(heWeather.getNow().getTmp());
        holder.tvTemperatureMin.setText(heWeather.getDaily_forecast().get(0).getTmp().getMin());
        holder.tvTemperatureMax.setText(heWeather.getDaily_forecast().get(0).getTmp().getMax());

    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public void removeItem(int position){
        mListData.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Root root){
        mListData.add(root);
        notifyItemInserted(mListData.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView tvTemperature;
        private TextView tvCity;
        private TextView tvWeahter;
        private TextView tvTemperatureMin;
        private TextView tvTemperatureMax;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTemperature = (TextView) itemView.findViewById(R.id.tv_temperature);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
            tvWeahter = (TextView) itemView.findViewById(R.id.tv_weahter);
            tvTemperatureMin = (TextView) itemView.findViewById(R.id.tv_temperature_min);
            tvTemperatureMax = (TextView) itemView.findViewById(R.id.tv_temperature_max);

            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {
//            view.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            },1000);
            Vibrator vibrator= (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(30);

            Log.v("MY_TAG", mListData.get(getLayoutPosition()).getHeWeather().get(0).getBasic().getCity() + "被长按了");
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext).setTitle("删除?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SPUtils spUtils = SPUtils.getInstance(mContext);
                    spUtils.detelData(mListData.get(getLayoutPosition()).getHeWeather().get(0).getBasic().getCity());
                    removeItem(getLayoutPosition());
                }
            }).setNegativeButton("取消", null);
            builder.create();
            builder.show();
            return true;
        }


    }
}
