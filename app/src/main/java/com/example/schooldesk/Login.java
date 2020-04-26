package com.example.schooldesk;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Login extends AsyncTask<String, Void, String> {
    private final Context context;


    public Login(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        if(s.contentEquals("login successful")){
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(), DashboardActivity.class);
            context.startActivity(intent);

        } else {
            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String user = voids[0];
        String pass = voids[1];

        String connstr = "http://10.0.2.2:80/login.php";

        if (user.equals("kuldip") && pass.equals("vicky")){
            result = "login successful";
        }else {result = "login failed";}
/*
        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")
                    +"&&"+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line ="";
            while ((line = reader.readLine()) != null)
            {
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;

        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }
*/

        return result;

    }
}
