package client.cafe.bukccafe;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    Button sin, exit;
    EditText uname, pwd;
    String user, pass,verifypass;
    String name;
    InputStream is = null;
    String result = null;
    String line = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sin = (Button) findViewById(R.id.signin);
        exit = (Button) findViewById(R.id.exit);
        uname = (EditText) findViewById(R.id.uname);
        pwd = (EditText) findViewById(R.id.pwd);

        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = uname.getText().toString();
                pass = pwd.getText().toString();


                if ("xterminate".equals(user) && "admin".equals(pass)) {
                    Intent i = new Intent(Login.this, MainMenu.class);
                    startActivity(i);
                }
                else {
                    login(user,pass);
                }
            }
        });



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });




}

    private void login(final String username, String password) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Login.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {

                String urname = params[0];
                String passs = params[1];
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username",urname));
                nameValuePairs.add(new BasicNameValuePair("password", passs));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://attribes.com/b_login.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(Login.this, TeacherMenu.class);
                    intent.putExtra("username", username);
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(username, password);

    }

   /* public void select() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("u_phone", user));
        nameValuePairs.add(new BasicNameValuePair("u_pass", pass));

        try {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://attribes.com/a_login.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "No Internet Access!",
                    Toast.LENGTH_LONG).show();
        }

        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line );
            }
            is.close();
            result = sb.toString();
            Log.e("pass 2", result);
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());
        }

        try {
            JSONObject json_data = new JSONObject(result);
            verifypass = (json_data.getString("name"));
            Toast.makeText(getBaseContext(), "Name : " + verifypass,
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Fail 3", e.toString());
        }
    } */
}