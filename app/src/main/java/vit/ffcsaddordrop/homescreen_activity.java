package vit.ffcsaddordrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Sai Sree on 10/1/2016.
 */
public class homescreen_activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
      //  getActionBar().setTitle("HOME");
    }


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(homescreen_activity.this,homescreen_activity.class);
        startActivity(i);
    }

    public void onRegisterSubjects(View v)
    {
        Intent i = new Intent(homescreen_activity.this,Subjects_Activity.class);
        startActivity(i);
    }

    public void onOtherCourses(View v)
    {
        Intent i = new Intent(homescreen_activity.this,other_subjects.class);
        startActivity(i);
    }


    public void onMyProfile(View v)
    {
        Intent i = new Intent(homescreen_activity.this,profile_activity.class);
        startActivity(i);
    }

    public void onNotification(View v)
    {
        Intent i = new Intent(homescreen_activity.this,notification.class);
        startActivity(i);
    }
}
