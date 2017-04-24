package vit.ffcsaddordrop;

import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Sai Sree on 9/23/2016.
 */
public interface AddDropAPI {

    @FormUrlEncoded
    @POST("/login")
    public void getLoginDetails(@Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<JsonElement> response);

    @GET("/insert_student_details/")
    public void insertLoginDetails(@Query("regno") String regno, @Query("dob") String dob, @Query("mobile") String mobile, Callback<JsonElement> response);


    @FormUrlEncoded
    @POST("/refresh")
    public void getRegisteredSubjects(@Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<JsonElement> response);

    @FormUrlEncoded
    @POST("/grades")
    public void getGrades(@Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<JsonElement> response);

    @GET("/delete_details/")
    public void deleteDetails(@Query("regno")String regno, Callback<JsonElement> response);

    @GET("/store_course_details/")
    public void storeCourseDetails(@Query("regno")String regno, @Query("ccode") ArrayList<String> ccode, Callback<JsonElement> response);


    @GET("/hello/")
    public void getNotificationDetails(@Query("regno")String regno,@Query("typePhone")int typePhone,@Query("typeEmail")int typeEmail,@Query("typeSms")int typeSms,@Query("phone")String phone,@Query("email")String email, Callback<JsonElement> response);

    @GET("/scrapeffcs/")
    public void scrapeffcs(Callback<JsonElement> response);

    @GET("/sms/")
    public void callsms(@Query("number")String number,@Query("msg")String msg,Callback<JsonElement> response);

    @GET("/mail/")
    public void callemail(@Query("email")String email,@Query("msg")String msg,Callback<JsonElement> response);


    @GET("/fcm/")
    public void callfcm(Callback<JsonElement> response);



}
