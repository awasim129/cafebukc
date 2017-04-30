package client.cafe.bukccafe;

import android.Manifest;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AddUser extends AppCompatActivity {

    Button add,mm;
    String u_name;
    String u_desk,u_phone,u_pass;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    EditText name,desk,phone,password;
    String smsmsg1,smsmsg2,smsmsg3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        add = (Button)findViewById(R.id.add);
        mm = (Button)findViewById(R.id.mm);
        name = (EditText)findViewById(R.id.name) ;
        desk = (EditText)findViewById(R.id.desk);
        phone = (EditText)findViewById(R.id.phone);
        password = (EditText)findViewById(R.id.passwd);

        mm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                u_name = name.getText().toString();
                u_desk = desk.getText().toString();
                u_phone = phone.getText().toString();
                u_pass = password.getText().toString();

                smsmsg1 = "Dear "+u_name+", Your Bahria University Cafe Account has been registered.";
                smsmsg2 =  "Your Username is: "+ u_phone +" and your Password is: "+ u_pass+".";
                smsmsg3 =  "Please Contact the University Administration if you have any issue in logging in your Account !";

                if (u_name.equals("")||u_desk.equals("")||u_phone.equals("")||u_pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    insert();
                }
            }
        }); }



    public void insert() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("u_name",u_name));
        nameValuePairs.add(new BasicNameValuePair("u_desk",u_desk));
        nameValuePairs.add(new BasicNameValuePair("phone",u_phone));
        nameValuePairs.add(new BasicNameValuePair("password",u_pass));

        try
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://attribes.com/insertt.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
           is = entity.getContent();
            //InputStreamReader inputStreamReader = new InputStreamReader(is);
            Log.e("pass 1", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Internet Connectivity Error",
                    Toast.LENGTH_LONG).show();
        }

        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("pass 2", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }

        try
        {
            JSONObject json_data = new JSONObject(result);
            code=(json_data.getInt("code"));

            if(code==1)
            {
                Toast.makeText(getBaseContext(), "Member Added Successfully",
                        Toast.LENGTH_SHORT).show();

                    SmsManager smsmanager = SmsManager.getDefault();
                    smsmanager.sendTextMessage(u_phone, null, smsmsg1, null, null);
                    smsmanager.sendTextMessage(u_phone, null, smsmsg2, null, null);
                    smsmanager.sendTextMessage(u_phone, null, smsmsg3, null, null);
            }
            else
            {
                Toast.makeText(getBaseContext(), "Another Account Already Registered on this Phone Number",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }




    }

