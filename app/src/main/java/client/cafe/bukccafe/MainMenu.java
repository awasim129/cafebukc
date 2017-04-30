package client.cafe.bukccafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button adduser,additem,listitem,orderstat,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        adduser = (Button)findViewById(R.id.adduser);
        additem = (Button)findViewById(R.id.additem);
        listitem = (Button)findViewById(R.id.listitem);
        orderstat = (Button)findViewById(R.id.orderstat);
        exit = (Button)findViewById(R.id.mm);

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,AddUser.class);
                startActivity(i);

            }
        });

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainMenu.this,AddFood.class);
                startActivity(i2);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}
