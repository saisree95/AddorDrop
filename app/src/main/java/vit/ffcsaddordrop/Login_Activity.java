package vit.ffcsaddordrop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Login_Activity extends AppCompatActivity {


    public static final String ROOT_URL = "https://vitacademics-rel.herokuapp.com/api/v2/vellore";
    public static final String ROOT_URL1 = "https://f9a460b1.ngrok.io";
    private EditText eregno;
    private EditText edob;
    private EditText emobile;
    private String regno;
    private String dob;
    private String mobile;
    private String token;
    private PrefManager prefManager;    private ProgressDialog mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();
        mProgressView = new ProgressDialog(Login_Activity.this);
        mProgressView.setMessage("Logging in...");
        mProgressView.setCancelable(false);
        mProgressView.setCanceledOnTouchOutside(false);

    }


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Login_Activity.this,Login_Activity.class);
        startActivity(i);
    }
    public void onClickLogin(View v)
    {


        eregno = (EditText)findViewById(R.id.regno);
        edob = (EditText)findViewById(R.id.dob);
        emobile = (EditText)findViewById(R.id.mobile);
        regno = eregno.getText().toString();
        dob = edob.getText().toString();
        mobile = emobile.getText().toString();
        prefManager = new PrefManager(getApplicationContext());
        token = prefManager.getToken();
        Log.w("TOKEN",token);
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
        onConfirmSignin();

    }

    public void onConfirmSignin()
    {

            ConnectivityManager cm =
                    (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if(isConnected) {

                login();

            }
            else
                Toast.makeText(getApplicationContext(),"Verify Internet Connection", Toast.LENGTH_LONG).show();

    }


    public void login()
    {


        Toast.makeText(Login_Activity.this,"Login Reached -1 ", Toast.LENGTH_SHORT).show();
      //  prefManager = new PrefManager(getApplicationContext());
        mProgressView.show();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        AddDropAPI api =adapter.create(AddDropAPI.class);
        api.getLoginDetails(regno,dob,mobile,new Callback<JsonElement>()
                {

                    @Override
                    public void success(JsonElement output, Response response)
                    {
                        mProgressView.dismiss();
                        JSONObject json;
                        try
                        {
                            json= new JSONObject(output.toString());

                            JSONObject status = json.getJSONObject("status");
                            if(status.getString("code")=="0")
                            {
                                prefManager.setLoginFlag(true);
                                prefManager.setRegNo(regno);
                                prefManager.setDob(dob);
                                prefManager.setMobile(mobile);
                                Toast.makeText(getApplicationContext(),status.getString("message")+"Hi"+json.getString("name"),Toast.LENGTH_LONG).show();

                                Intent i = new Intent(Login_Activity.this,homescreen_activity.class);
                                startActivity(i);
                            }

                        }catch (JSONException e)
                        {
                            e.printStackTrace(); //time pass changes

                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        mProgressView.dismiss();
                        Toast.makeText(Login_Activity.this, "Helloooo"+error.getCause().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
