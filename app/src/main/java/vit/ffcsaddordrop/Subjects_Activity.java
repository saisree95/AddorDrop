package vit.ffcsaddordrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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
 * Created by Sai Sree on 9/22/2016.
 */
public class Subjects_Activity extends AppCompatActivity implements Subjects_Adapter.customButtonListner {
    public static final String ROOT_URL = "https://vitacademics-rel.herokuapp.com/api/v2/vellore";
    ListView regsubjects;
    private String regno;
    private String dob;
    private String mobile;
    private PrefManager prefManager;
    List<String> cCodeList;
    List<String> cTitleList;
    private Menu menu;
    private ProgressDialog mProgressView;


    private TextView sel_name,sel_code;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_activity);

        prefManager = new PrefManager(getApplicationContext());
        regsubjects = (ListView)findViewById(R.id.regsubjects);
        regno=prefManager.getRegNo();
        dob=prefManager.getDob();
        mobile=prefManager.getMobile();
        mProgressView = new ProgressDialog(Subjects_Activity.this);
        mProgressView.setMessage("Loading");
        mProgressView.setCancelable(false);
        mProgressView.setCanceledOnTouchOutside(false);



        regsubjects = (ListView)findViewById(R.id.regsubjects);

        mProgressView.show();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        AddDropAPI api =adapter.create(AddDropAPI.class);
        api.getRegisteredSubjects(regno,dob,mobile,new Callback<JsonElement>()
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
                    JSONArray courses = json.getJSONArray("courses");
                    JSONObject details = null;
                    cCodeList = new ArrayList<String>();
                    cTitleList = new ArrayList<String>();

                    if(status.getString("code")=="0")
                    {
                        for(int i=0;i<courses.length();i++)
                        {
                            details = courses.getJSONObject(i);
                            String coursecode=details.getString("course_code");
                            String coursetitle=details.getString("course_title");
                            cCodeList.add(coursecode);
                            cTitleList.add(coursetitle);

                        }
                        Toast.makeText(getApplicationContext(),status.getString("message")+"Hello",Toast.LENGTH_LONG).show();
                    }

                    Subjects_Adapter adapter = new Subjects_Adapter(cCodeList,cTitleList,Subjects_Activity.this);
                    adapter.setCustomButtonListner(Subjects_Activity.this);
                    regsubjects.setAdapter(adapter);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error)
            {
                mProgressView.dismiss();
                Toast.makeText(Subjects_Activity.this, error.getCause().toString(), Toast.LENGTH_SHORT).show();

            }
        }
        );


    }


    @Override
    public void onButtonClickListner(int position,int value)
    {
        Toast.makeText(Subjects_Activity.this, "Button click " + value, Toast.LENGTH_SHORT).show();
        View view = getViewByPosition(position, regsubjects);

        sel_name = (TextView)view.findViewById(R.id.ctitle);
        sel_code=(TextView)findViewById(R.id.ccode);

        if(value==1)
        {
            Toast.makeText(getApplicationContext(),"hi hi hi",Toast.LENGTH_LONG);

        }

    }
    private View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
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

            Intent i = new Intent(Subjects_Activity.this,profile_activity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
