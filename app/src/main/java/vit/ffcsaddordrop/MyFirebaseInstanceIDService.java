package vit.ffcsaddordrop;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Sai Sree on 7/31/2016.
 */

//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private PrefManager prefManager;
    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);


        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + token);

    }

    private void registerToken(String token)
    {
        prefManager = new PrefManager(getApplicationContext());
        prefManager.setToken(token);

    }

}





