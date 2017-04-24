package vit.ffcsaddordrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Sai Sree on 9/24/2016.
 */
public class profile_activity extends AppCompatActivity {

    private Menu menu;
    private PrefManager prefManager;
    private TextView mName;
    private TextView mRegno;
    private TextView mCampus;
    private TextView mPhno;
    private String regno;
    private String dob;
    private String mobile;

    public static final String ROOT_URL = "https://vitacademics-rel.herokuapp.com/api/v2/vellore";
    public static final String ROOT_URL1 = "https://f9a460b1.ngrok.io";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        mName = (TextView)findViewById(R.id.name);
        mRegno = (TextView)findViewById(R.id.regno1);
        mCampus = (TextView)findViewById(R.id.campus);
        mPhno = (TextView)findViewById(R.id.phno);

        profileInformaion();
    }


    public void onLogout(View v)
    {
        prefManager.setLoginFlag(false);

        logout_method();

    }

    public void logout_method()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL1)
                .build();

        AddDropAPI api = adapter.create(AddDropAPI.class);

        prefManager = new PrefManager(getApplicationContext());
        regno=prefManager.getRegNo();
        dob=prefManager.getDob();
        mobile=prefManager.getMobile();

        api.deleteDetails(regno,new Callback<JsonElement>() {
            @Override


            public void success(JsonElement output, Response response) {
                //Dismissing the loading progressbar

                JSONObject json;

                try {
                    json = new JSONObject(output.toString());
                    JSONObject status = json.getJSONObject("status");

                    if(status.getString("code")=="0"){



                        Toast.makeText(profile_activity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(profile_activity.this,Login_Activity.class);
                        startActivity(i);


                    }


                    if(status.getString("code")=="1"){

                        Toast.makeText(profile_activity.this, "Not Deleted", Toast.LENGTH_SHORT).show();

                    }

                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(profile_activity.this, "ERROR!", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void profileInformaion()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        AddDropAPI api = adapter.create(AddDropAPI.class);
        //prefManager = new PrefManager(getApplicationContext());
      //  final String mobile = prefManager.getMobile();
        //Defining the method
        prefManager = new PrefManager(getApplicationContext());
        regno=prefManager.getRegNo();
        dob=prefManager.getDob();
        mobile=prefManager.getMobile();

        api.getLoginDetails(regno,dob,mobile,new Callback<JsonElement>() {
            @Override


            public void success(JsonElement output, Response response) {
                //Dismissing the loading progressbar

                JSONObject json;

                try {
                    json = new JSONObject(output.toString());
                    JSONObject status = json.getJSONObject("status");

                    if(status.getString("code")=="0"){

                        mName.setText(json.getString("name"));
                        mRegno.setText(json.getString("reg_no"));
                        mCampus.setText(json.getString("campus"));
                        mPhno.setText(json.getString("mobile"));

                    }

                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(profile_activity.this, "ERROR!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
