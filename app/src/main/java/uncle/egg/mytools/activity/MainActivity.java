package uncle.egg.mytools.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uncle.egg.mytools.R;
import uncle.egg.mytools.fragment.WeatherFragment;
import uncle.egg.mytools.fragment.WeatherFragment2;
import uncle.egg.mytools.fragment.WiFiFragment;

/*
* 一个小型的个人工具包
* 功能：
* 1.天气查询    （使用了volley框架，数据来源：聚合数据）
* 2.wifi强度查看
*
* */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

   // private WeatherFragment weatherFragment;
    private WeatherFragment2 weatherFragment;
    private WiFiFragment wiFiFragment;
    private View viewCityDialog;
    private EditText editCity;
    private String nowCity;

    private FragmentManager fm;
    private FragmentTransaction transaction;

    private static final int MY_PERMISSIONS_LOCATION_CODE = 1;
    private static final int MY_PERMISSIONS_PHONT_STATE_CODE=2;

    private static final int FAB_CITY_SET=1;
    private static final int FAB_WIFI_SET=2;
    private static int fabStatus=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (fabStatus){
                    case 1:{
                        viewCityDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_change_city,null);
                        editCity = (EditText) viewCityDialog.findViewById(R.id.ed_city);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("设置城市").setView(viewCityDialog).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // setWeatherMessage(editCity.getText().toString(), key);
                                nowCity = editCity.getText().toString();
                                weatherFragment.startRshWeather(nowCity);
                            }
                        });
                        builder.create().show();
                    }break;
                    case 2:{}break;
                    default:{
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }break;
                }


            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //--------------以上为编译器自动生成 --------------



        weatherFragment = new WeatherFragment2(this);
        wiFiFragment = new WiFiFragment(this);
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.my_layout, weatherFragment);
        transaction.commit();

        //申请一些权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0时
            if (this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_PHONT_STATE_CODE);
            } else {

            }
        }else { //6.0以下时

        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            viewCityDialog = LayoutInflater.from(this).inflate(R.layout.dialog_change_city,null);
            editCity = (EditText) viewCityDialog.findViewById(R.id.ed_city);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("设置城市").setView(viewCityDialog).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // setWeatherMessage(editCity.getText().toString(), key);
                    nowCity = editCity.getText().toString();
                    weatherFragment.startRshWeather(nowCity);
                }
            });
            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_waether) {
            // Handle the camera action
            transaction = fm.beginTransaction();
            transaction.replace(R.id.my_layout, weatherFragment);
            transaction.commit();
            fab.setImageResource(R.mipmap.ic_city);
            fabStatus = FAB_CITY_SET;
        } else if (id == R.id.nav_gallery) {

            //申请一些权限来显示wifi,6.0以后扫描wifi需要开启定位
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_LOCATION_CODE);
                } else {
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.my_layout, wiFiFragment);
                    transaction.commit();
                }
            }else {
                transaction = fm.beginTransaction();
                transaction.replace(R.id.my_layout, wiFiFragment);
                transaction.commit();
            }
            fab.setImageResource(R.mipmap.ic_wifi_2);
            fabStatus=FAB_WIFI_SET;

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        Log.v("MY_TAG", "回调权限申请");
        if (requestCode == MY_PERMISSIONS_LOCATION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                transaction = fm.beginTransaction();
                transaction.replace(R.id.my_layout, wiFiFragment);
                transaction.commitAllowingStateLoss();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if(requestCode==MY_PERMISSIONS_PHONT_STATE_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }





//    private void startWifi(){
//        transaction = fm.beginTransaction();
//        transaction.replace(R.id.my_layout, wiFiFragment);
//        transaction.commit();
//    }
}
