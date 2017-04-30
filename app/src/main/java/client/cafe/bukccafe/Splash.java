package client.cafe.bukccafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread wait = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                    Intent i = new Intent(Splash.this,Login.class);
                    startActivity(i);
                    finish();
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
                wait.start();
    }
}
