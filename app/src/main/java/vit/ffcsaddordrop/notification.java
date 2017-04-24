package vit.ffcsaddordrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;


import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Sai Sree on 10/2/2016.
 */
public class notification extends AppCompatActivity {

    public static final String ROOT_URL = "https://f9a460b1.ngrok.io";
    private String regno,token;
    CheckBox cPush,cEmail,cSms;
    int nPush=0,nEmail=0,nSms=0;
    int notifytype;
    String phno="";
    String email="";
    EditText phntxt,mailtxt;
    private PrefManager prefManager;
    Button notify_button,sub_button;
    Timer timer;
    TimerTask timerTask;
    private ProgressDialog mProgressView;

    final Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_page);
        phntxt = (EditText)findViewById(R.id.phonetxt);
        mailtxt = (EditText)findViewById(R.id.emailtxt);
        phntxt.setVisibility(View.INVISIBLE);
        mailtxt.setVisibility(View.INVISIBLE);
        notify_button = (Button)findViewById(R.id.notify_but);
        sub_button = (Button)findViewById(R.id.submit_but);
        mProgressView = new ProgressDialog(notification.this);
        mProgressView.setMessage("Storing notification details !");
        mProgressView.setCancelable(false);
        mProgressView.setCanceledOnTouchOutside(false);

    }


    public void startTimer()
    {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask,5000,50000);
    }

    public void stoptimertask(View v) {
        if (timer != null) {
            timer.cancel();
            timer = null;

        }

    }

    public void initializeTimerTask()
    {
        timerTask = new TimerTask() {
            @Override
            public void run() {

                getnotification();

            }
        };
    }

        public void onClickSubmit(View v)
    {

        phno = phntxt.getText().toString();
        email = mailtxt.getText().toString();
        notify_method();


    }

    public void notify_method()
    {

        prefManager = new PrefManager(getApplicationContext());
        regno=prefManager.getRegNo();
        token=prefManager.getToken();

        mProgressView.show();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        AddDropAPI api =adapter.create(AddDropAPI.class);
        api.getNotificationDetails(regno,nPush,nEmail,nSms,phno,email,new Callback<JsonElement>()
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
                                Toast.makeText(getApplicationContext(),"Hello from notofication !",Toast.LENGTH_LONG).show();
                                sub_button.setVisibility(View.INVISIBLE);
                                notify_button.setVisibility(View.VISIBLE);

                            }

                        }catch (JSONException e)
                        {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        mProgressView.dismiss();
                        error.printStackTrace();
                    }
                }
        );
    }



    public void onClickNotification(View v)
    {
        startTimer();

    }


    public void getnotification()
    {

        prefManager = new PrefManager(getApplicationContext());
        regno=prefManager.getRegNo();
        token=prefManager.getToken();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        AddDropAPI api =adapter.create(AddDropAPI.class);
        api.scrapeffcs(new Callback<JsonElement>()
                {
                    @Override
                    public void success(JsonElement output, Response response)
                    {

                        JSONObject json;
                        try
                        {
                            json= new JSONObject(output.toString());

                            JSONObject status = json.getJSONObject("status");
                            if(status.getString("code")=="0")
                            {
                                Toast.makeText(getApplicationContext(),"Subjet Added !",Toast.LENGTH_LONG).show();

                                sms_subjectadded();
                                mail_subjectadded();
                                push_subjectadded();


                            }

                            if(status.getString("code")=="1")
                            {
                                Toast.makeText(getApplicationContext(),"Subjet Not Added !",Toast.LENGTH_LONG).show();

                            }



                        }catch (JSONException e)
                        {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        error.printStackTrace();
                    }
                }
        );

    }


    public void push_subjectadded()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        String msg="New Subject Added ,Check Add or Drop";
        AddDropAPI api =adapter.create(AddDropAPI.class);
        api.callfcm(new Callback<JsonElement>()
                       {
                           @Override
                           public void success(JsonElement output, Response response)
                           {
                               JSONObject json;
                               try
                               {
                                   json= new JSONObject(output.toString());

                                   JSONObject status = json.getJSONObject("status");
                                   if(status.getString("code")=="0")
                                   {
                                       Toast.makeText(getApplicationContext(),"Push Success !",Toast.LENGTH_LONG).show();

                                   }

                                   else
                                   {
                                       Toast.makeText(getApplicationContext(),"Push Failed !",Toast.LENGTH_LONG).show();

                                   }



                               }catch (JSONException e)
                               {
                                   e.printStackTrace();

                               }
                           }

                           @Override
                           public void failure(RetrofitError error)
                           {
                               error.printStackTrace();
                           }
                       }
        );
    }

    public void mail_subjectadded()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        String msg="New Subject Added ,Check Add or Drop";
        AddDropAPI api =adapter.create(AddDropAPI.class);
        api.callemail(email,msg,new Callback<JsonElement>()
                {
                    @Override
                    public void success(JsonElement output, Response response)
                    {
                        JSONObject json;
                        try
                        {
                            json= new JSONObject(output.toString());

                            JSONObject status = json.getJSONObject("status");
                            if(status.getString("code")=="0")
                            {
                                Toast.makeText(getApplicationContext(),"Email Success !",Toast.LENGTH_LONG).show();

                            }

                            if(status.getString("code")=="1")
                            {
                                Toast.makeText(getApplicationContext(),"Email Failed !",Toast.LENGTH_LONG).show();

                            }



                        }catch (JSONException e)
                        {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        error.printStackTrace();
                    }
                }
        );
    }

    public void sms_subjectadded()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        String msg="New Subject Added Check Add or Drop";
        AddDropAPI api =adapter.create(AddDropAPI.class);
        api.callsms(phno,msg,new Callback<JsonElement>()
                {
                    @Override
                    public void success(JsonElement output, Response response)
                    {
                        JSONObject json;
                        try
                        {
                            json= new JSONObject(output.toString());

                            JSONObject status = json.getJSONObject("status");
                            if(status.getString("code")=="0")
                            {
                                Toast.makeText(getApplicationContext(),"SMS Success !",Toast.LENGTH_LONG).show();

                            }

                            if(status.getString("code")=="1")
                            {
                                Toast.makeText(getApplicationContext(),"SMS Failed !",Toast.LENGTH_LONG).show();

                            }



                        }catch (JSONException e)
                        {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        error.printStackTrace();
                    }
                }
        );
    }

    public void onClickPush(View v)
    {
        cPush = (CheckBox)v;
        if(cPush.isChecked()) {

            nPush = 1;
            Toast.makeText(getApplicationContext(), "You Clicked Push" + nPush, Toast.LENGTH_LONG).show();
        }
        else {

            nPush = 0;
            Toast.makeText(getApplicationContext(), "You Clicked Push" + nPush, Toast.LENGTH_LONG).show();
        }

    }

    public void onClickEmail(View v)
    {

        cEmail = (CheckBox)v;
        if(cEmail.isChecked()) {
            mailtxt.setVisibility(v.VISIBLE);
            nEmail = 1;
            Toast.makeText(getApplicationContext(), "You Clicked Email" + nEmail, Toast.LENGTH_LONG).show();
        }
        else{
            mailtxt.setVisibility(v.INVISIBLE);
            nEmail = 0;
            Toast.makeText(getApplicationContext(), "You Clicked Email" + nEmail, Toast.LENGTH_LONG).show();
        }

    }

    public void onClickSms(View v)
    {
        cSms = (CheckBox)v;
        if(cSms.isChecked()) {
            phntxt.setVisibility(v.VISIBLE);
            nSms = 1;
            Toast.makeText(getApplicationContext(), "You Clicked Sms" + nSms, Toast.LENGTH_LONG).show();
        }
        else {
            phntxt.setVisibility(v.INVISIBLE);
            nSms = 0;
            Toast.makeText(getApplicationContext(), "You Clicked Sms" + nSms, Toast.LENGTH_LONG).show();
        }

    }
}
