package uncle.egg.mytools.activity;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by egguncle on 16.7.19.
 */
public class MyApplication extends Application {

  //  public static RequestQueue queue;

    private String MY_API_KEY="994f4d03499d618879bd337a94bce437";

    @Override
    public void onCreate() {
        super.onCreate();
   //     queue = Volley.newRequestQueue(getApplicationContext());
        ApiStoreSDK.init(this,MY_API_KEY);

    }

  //  public static RequestQueue getHttpQueues(){
   //     return queue;
  //  }

}
