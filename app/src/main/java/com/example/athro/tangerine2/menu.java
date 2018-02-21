package com.example.athro.tangerine2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.graphics.Color.GREEN;


/**
 * Created by athro on 10/7/2016.
 */

public class menu extends Activity implements View.OnClickListener{
    String rl ;
    String t="999";
    int tId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button btn1 = (Button) findViewById(R.id.letters);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        Button btn4 = (Button) findViewById(R.id.button4);
        rl =getString(R.string.ur);
        Bundle ur = getIntent().getExtras();

        tId = ur.getInt("tId");
        /*Log.e("myError" , ""+tId);
        Log.e("myId",""+tId);*/
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String url="";
        if(view.getId() == R.id.letters){

            url= rl+"/getLevels.php ";
            t ="1";
            Intent intent = new Intent(this, instrAcitivty.class);
            intent.putExtra("url",url);
            intent.putExtra("type",t);
            intent.putExtra("log",1);
            intent.putExtra("tId",tId);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button2) {


           // url =  ur+"/getlongword.php ";
            //t = "2";
            /*Intent intent = new Intent(this, words.class);
            intent.putExtra("url",url);
            intent.putExtra("type",t);
            startActivity(intent);*/
            Intent intent = new Intent(this, instrAcitivty.class);
            intent.putExtra("type",1);
            intent.putExtra("log",2);
            intent.putExtra("tId",tId);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button3) {


            url =  rl+"/getSha.php ";
            t ="3";
            Intent intent = new Intent(this, instrAcitivty.class);
            intent.putExtra("url",url);
            intent.putExtra("type",t);
            intent.putExtra("log",3);
            intent.putExtra("tId",tId);
            startActivity(intent);
            //Intent intent = new Intent(this, textactivity.class);
            //startActivity(intent);
        }
        else if (view.getId() == R.id.button4) {


            //url =  ur+"/getSha.php ";
            //t ="3";0
            //Intent intent = new Intent(this, textactivity.class);
            Intent intent = new Intent(this, instrAcitivty.class);
            intent.putExtra("type",1);
            intent.putExtra("log",4);
            intent.putExtra("tId",tId);
            startActivity(intent);
        }
        view.setBackgroundColor(GREEN);

    }
}
