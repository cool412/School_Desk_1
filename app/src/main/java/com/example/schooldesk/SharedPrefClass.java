package com.example.schooldesk;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefClass {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPrefClass(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }
    // Write Login status to the shared preferences.
    // All below methods are for writing to SharedPreferences.
    public void writeLoginStatus(boolean status){
        // Before writing we need to prepare editor for that.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        // here we can also use editor.commit() but android studio suggested to use editor.apply();
        editor.apply();
    }
    public void writeUserName(String userName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_name), userName);
        editor.apply();
    }
    public void writeSessionId(String sessionId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_session_id), sessionId);
        editor.apply();
    }
    // All below methods are for reading the values stored in SharedPreferences.
    public boolean readLoginStatus(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status),false);
    }
    public String readUserName(){
        return sharedPreferences.getString(context.getString(R.string.pref_user_name),"User");
    }
    public String readSessionId(){
        return sharedPreferences.getString(context.getString(R.string.pref_session_id),"");
    }
}
