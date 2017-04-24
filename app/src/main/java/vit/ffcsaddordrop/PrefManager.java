package vit.ffcsaddordrop;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sai Sree on 9/23/2016.
 */
public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "add-drop";
    private static final String IS_LOGIN = "IsRegister";
    private static final String REGNO = "RegNo";
    private static final String MOBILE = "Mobile";
    private static final String DOB = "DOB";
    private static final String TOKEN = "Token";
    private static final String URL="url";




    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLoginFlag(boolean flag)
    {

        editor.putBoolean(IS_LOGIN, flag);
        editor.commit();
    }

    public boolean getLoginFlag() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setURL()
    {
        editor.putString(URL,"gggg");
        editor.commit();
    }

    public void setToken(String token){

        editor.putString(TOKEN,token);
        editor.commit();

    }

    public String getToken(){
        return pref.getString(TOKEN, null);
    }


    public void setRegNo(String token){

        editor.putString(REGNO,token);
        editor.commit();

    }

    public String getRegNo(){
        return pref.getString(REGNO, null);
    }

    public void setMobile(String mobile){
        editor.putString(MOBILE,mobile);
        editor.commit();
    }

    public String getMobile(){
        return pref.getString(MOBILE,null);
    }


    public void setDob(String name){
        editor.putString(DOB,name);
        editor.commit();
    }

    public String getDob(){
        return pref.getString(DOB,null);
    }

}
