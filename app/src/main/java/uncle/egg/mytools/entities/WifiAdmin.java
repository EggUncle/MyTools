package uncle.egg.mytools.entities;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * Created by egguncle on 16.7.15.
 */
public class WifiAdmin {

    //定义WifiManager对象
    private WifiManager mWifiManager;

    //定义WifiInfo对象
    private WifiInfo mWifiInfo;

    //扫描出的网络连接列表
    private List<ScanResult> mWifiList;

    //网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;

    //定义一个WifiLock
    WifiManager.WifiLock mWifiLock;

    //构造器
    public WifiAdmin(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    //打开Wifi
    public void OpenWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    //关闭wifi
    public void CloseWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    //锁定wifiLock，当下载大文件时需要锁定
    public void AcquireWifiLock() {
        mWifiLock.acquire();
    }

    //解锁wifiLock
    public void ReleaseWifiLock() {
        //判断是否锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.release();
        }
    }

    //创建一个wifilock
    public void CreatWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("Test");      //createWifiLock(lockName)
    }

    //得到配置好的网络
    public List<WifiConfiguration> GetConfiguration() {
        return mWifiConfiguration;
    }

    //制定配置好的网络进行连接
    public void ConnectionConfiguration(int index) {
        if (index > mWifiConfiguration.size()) {
            return;
        }
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId, true);
    }

    //开始扫描
    public void StartScan() {
        mWifiManager.startScan();
        //得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        //得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
    }

    //得到网络列表
    public List<ScanResult> GetWifiList() {
        return mWifiList;
    }

    //查看扫描结果
    public StringBuilder LookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        Log.v("MY_TAG",mWifiList.size()+"");
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder.append("Index " + new Integer(i + 1).toString() + ":");
            //将ScanResult信息转换成一个字符包
            //其中包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i)).toString());
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }

    //得到MAC地址
    public String GetMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    //得到接入点的BSSID
    public String GetBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }

    //得到IP地址
    public int GetIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    //得到连接的ID
    public int GetNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    //得到WifiInfo的所有信息包
    public String GetWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    //添加一个网络并链接
    public void AddNetwork(WifiConfiguration wcg){
        int wcgID= mWifiManager.addNetwork(wcg);
        mWifiManager.enableNetwork(wcgID,true);
    }

    //断开指定ID的网络
    public void DisconnectWifi(int netId){
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

}
