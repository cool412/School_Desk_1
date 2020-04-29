package com.example.schooldesk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {
    // First we will define Object of SharedPrefClass object.
    @SuppressLint("StaticFieldLeak")
    private static SharedPrefClass sharedPrefClass;
    EditText EditUserName, EditPassword;
    ImageView imagePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefClass = new SharedPrefClass(this);

        EditUserName = findViewById(R.id.login_user);
        EditPassword = findViewById(R.id.login_password);
        imagePass = findViewById(R.id.pass_visibility);

        moveToNextActivityIfLogIn();
    }
    private void moveToNextActivityIfLogIn(){
        // Check, if user has logged out of the application or not.
        // Use is still logged in then we will continue with DashboardActivity.
        if (sharedPrefClass.readLoginStatus()) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
            // The below finish will destroy the MainActivity as soon as the new activity is invoked.
            finish();
        }
    }

    public void signInClicked(View view) {

        String UserName = EditUserName.getText().toString().trim();
        String PassWord = EditPassword.getText().toString().trim();

        Login lg = new Login(this);
        lg.execute(UserName, PassWord);
        EditUserName.setText("");
        EditPassword.setText("");

    }
    /*
    * This method is fro make password visible and invisible to user.
    * */
    public void ShowHidePass(View view) {
        if(view.getId()==R.id.pass_visibility){

            if(EditPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                imagePass.setImageResource(R.drawable.invisible);

                //Show Password
                EditPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                imagePass.setImageResource(R.drawable.visible);

                //Hide Password
                EditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private static class Login extends AsyncTask<String, Void, String> {
        private WeakReference<MainActivity> activityWeakReference;
        //private final Context context;

        // Constructor
        Login(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }
        /*
        * MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) return;
          The above sample code will create strong reference inside process.
          If any time while this background task is running MainActivity is destroyed
          then it will also terminate the background process.
          The strong reference is for accessing the fre methods and variables of main activity.
        * */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) return;
            if (sharedPrefClass.readLoginStatus()) {
                return;
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) return;
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            String user = voids[0];
            String pass = voids[1];

            String connstr = "http://10.0.2.2:80/login.php";

            if (user.equals("kuldip") && pass.equals("vicky")) {
                result = "login successful";
            } else {
                result = "login failed";
            }
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
        /* All the return value from doInBackground will go into this below method.
        So, after completing the background task we can perform our next execution.
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) return;
            else if (s.contentEquals("login successful")) {
                Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show();
                // Let us write the content in sharedpreference.
                sharedPrefClass.writeUserName("kuldip");
                sharedPrefClass.writeLoginStatus(true);
                sharedPrefClass.writeSessionId("session active");

                activity.moveToNextActivityIfLogIn();

            } else Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show();
        }

    }
}
