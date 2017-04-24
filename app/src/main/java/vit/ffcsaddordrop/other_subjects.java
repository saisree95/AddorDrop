package vit.ffcsaddordrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Sai Sree on 10/2/2016.
 */
public class other_subjects extends AppCompatActivity {

    public static final String ROOT_URL = "https://vitacademics-rel.herokuapp.com/api/v2/vellore";
    public static final String ROOT_URL1 = "https://f9a460b1.ngrok.io";

    ListView regsubjects;
    private String regno;
    private PrefManager prefManager;
    ArrayList<String> cCodeList;
    List<String> cTitleList;
    private Menu menu;
    TextView c_code1,c_code2,c_code3,c_code4,c_code5,c_code6,c_code7,c_code8,c_title1,c_title2,c_title3,c_title4,c_title5,c_title6,c_title7,c_title8;
    RadioButton c_button1,c_button2,c_button3,c_button4,c_button5,c_button6,c_button7,c_button8;
    String cc_code1,cc_code2,cc_code3,cc_code4,cc_code5,cc_code6,cc_code7,cc_code8;
    Button but_submit1;
    private ProgressDialog mProgressView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_courses);

        prefManager = new PrefManager(getApplicationContext());
        regsubjects = (ListView)findViewById(R.id.regsubjects);
        regno=prefManager.getRegNo();

        but_submit1 = (Button)findViewById(R.id.but_submit);
        cCodeList = new ArrayList<String>();
        cTitleList = new ArrayList<String>();
        mProgressView = new ProgressDialog(other_subjects.this);
        mProgressView.setMessage("Storing Subjects !!");
        mProgressView.setCancelable(false);
        mProgressView.setCanceledOnTouchOutside(false);


        c_code1 = (TextView)findViewById(R.id.ccode1);
        c_code2 = (TextView)findViewById(R.id.ccode2);
        c_code3 = (TextView)findViewById(R.id.ccode3);
        c_code4 = (TextView)findViewById(R.id.ccode4);
        c_code5 = (TextView)findViewById(R.id.ccode5);
        c_code6 = (TextView)findViewById(R.id.ccode6);
        c_code7 = (TextView)findViewById(R.id.ccode7);
        c_code8 = (TextView)findViewById(R.id.ccode8);


        c_title1 = (TextView)findViewById(R.id.ctitle1);
        c_title2 = (TextView)findViewById(R.id.ctitle2);
        c_title3 = (TextView)findViewById(R.id.ctitle3);
        c_title4 = (TextView)findViewById(R.id.ctitle4);
        c_title5 = (TextView)findViewById(R.id.ctitle5);
        c_title6 = (TextView)findViewById(R.id.ctitle6);
        c_title7 = (TextView)findViewById(R.id.ctitle7);
        c_title8 = (TextView)findViewById(R.id.ctitle8);

        c_button1 = (RadioButton) findViewById(R.id.cbutton1);
        c_button2 = (RadioButton) findViewById(R.id.cbutton2);
        c_button3 = (RadioButton) findViewById(R.id.cbutton3);
        c_button4 = (RadioButton) findViewById(R.id.cbutton4);
        c_button5 = (RadioButton) findViewById(R.id.cbutton5);
        c_button6 = (RadioButton) findViewById(R.id.cbutton6);
        c_button7 = (RadioButton) findViewById(R.id.cbutton7);
        c_button8 = (RadioButton) findViewById(R.id.cbutton8);


    }

    public void onClickSubmit(View v)
    {
        Toast.makeText(getApplicationContext(),cCodeList.size()+"",Toast.LENGTH_LONG).show();
        mProgressView.show();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL1)
                .build();

        AddDropAPI api = adapter.create(AddDropAPI.class);

        prefManager = new PrefManager(getApplicationContext());
        regno=prefManager.getRegNo();


        api.storeCourseDetails(regno,cCodeList,new Callback<JsonElement>() {
            @Override


            public void success(JsonElement output, Response response) {
                mProgressView.dismiss();
                //Dismissing the loading progressbar

                JSONObject json;

                try {
                    json = new JSONObject(output.toString());
                    JSONObject status = json.getJSONObject("status");

                    if(status.getString("code")=="0"){

                        Toast.makeText(other_subjects.this, "Subjects Inserted", Toast.LENGTH_SHORT).show();

                    }

                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mProgressView.dismiss();
                Toast.makeText(other_subjects.this, "ERROR!", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void onClickb1(View v)
    {
        if(c_button1.isChecked())
        {
            cc_code1 = c_code1.getText().toString();
            cCodeList.add(cc_code1);
        }
        else
        {
            cc_code1 = "";
        }
    }


    public void onClickb2(View v)
    {
        if(c_button2.isChecked())
        {
            cc_code2 = c_code2.getText().toString();
            cCodeList.add(cc_code2);
        }
        else
        {
            cc_code2 = "";
        }
    }

    public void onClickb3(View v)
    {
        if(c_button3.isChecked())
        {
            cc_code3 = c_code3.getText().toString();
            cCodeList.add(cc_code3);
        }
        else
        {
            cc_code3 = "";
        }
    }

    public void onClickb4(View v)
    {
        if(c_button4.isChecked())
        {
            cc_code4 = c_code4.getText().toString();
            cCodeList.add(cc_code4);
        }
        else
        {
            cc_code4 = "";
        }
    }


    public void onClickb5(View v)
    {
        if(c_button5.isChecked())
        {
            cc_code5 = c_code5.getText().toString();
            cCodeList.add(cc_code5);
        }
        else
        {
            cc_code5 = "";
        }
    }

    public void onClickb6(View v)
    {
        if(c_button6.isChecked())
        {
            cc_code6 = c_code6.getText().toString();
            cCodeList.add(cc_code6);
        }
        else
        {
            cc_code6 = "";
        }
    }

    public void onClickb7(View v)
    {
        if(c_button7.isChecked())
        {
            cc_code7 = c_code7.getText().toString();
            cCodeList.add(cc_code7);
        }
        else
        {
            cc_code7 = "";
        }
    }

    public void onClickb8(View v)
    {
        if(c_button8.isChecked())
        {
            cc_code8 = c_code8.getText().toString();
            cCodeList.add(cc_code8);
        }
        else
        {
            cc_code8 = "";
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {

            Intent i = new Intent(other_subjects.this,profile_activity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
