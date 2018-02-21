package com.example.athro.tangerine2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athro.tangerine2.classes.dataWord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by athro on 11/2/2016.
 */

public class instrAcitivty extends Activity implements View.OnClickListener{
    String login_url ;
    String urll ;
    String rl;
    String type;
    int log;
    String text;
    Button btn ;
    int f = 0;
    int tId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruct);
        Bundle ur = getIntent().getExtras();
        rl = getString(R.string.ur);
        login_url = ur.getString("url");
        type = ur.getString("type");
        log = ur.getInt("log");
        tId = ur.getInt("tId");
        if(log == 1){
            urll = rl+"/getinst1.php";
        }
        else if (log == 2){
            urll = rl+"/getinst2.php";
        }
        else if ( log == 3){
            urll = rl+"/getinst3.php";
        }
        else {
            urll = rl+"/getinst4.php";
        }
        instrAcitivty.Background2 bc = new instrAcitivty.Background2(this);
        bc.execute("display");
    }
    void liset( String str){
        this.text = str;
        TextView tex = (TextView) findViewById(R.id.textView8);
        tex.setText(str);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
        return;
    }



    @Override
    public void onClick(View view) {

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, "ابدأ الامتحان", duration);
        toast.show();
        if(log == 1){

            Intent intent = new Intent(this, words.class);
            intent.putExtra("url",login_url);
            intent.putExtra("type",type);
            intent.putExtra("tId",tId);
            startActivity(intent);
            f++;
        }
        else if (log == 2) {


            // url =  ur+"/getlongword.php ";
            //t = "2";
            /*Intent intent = new Intent(this, words.class);
            intent.putExtra("url",url);
            intent.putExtra("type",t);
            startActivity(intent);*/
            Intent intent = new Intent(this, test4.class);
            intent.putExtra("url",login_url);
            intent.putExtra("type",1);
            intent.putExtra("log",log);
            intent.putExtra("tId",tId);
            startActivity(intent);
            f++;
        }
        else if (log == 3) {


            Intent intent = new Intent(this, words.class);
            intent.putExtra("url",login_url);
            intent.putExtra("type",type);
            intent.putExtra("tId",tId);
            startActivity(intent);
            f++;
            //Intent intent = new Intent(this, textactivity.class);
            //startActivity(intent);
        }
        else if (log == 4) {


            //url =  ur+"/getSha.php ";
            //t ="3";0
            Intent intent = new Intent(this, textactivity.class);
            intent.putExtra("url",login_url);
            intent.putExtra("type",2);
            intent.putExtra("log",log);
            intent.putExtra("tId",tId);
            startActivity(intent);
            f++;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(f != 0){
            this.finish();
        }
    }

    public class Background2 extends AsyncTask<String ,Void , String> {
        Context context;
        AlertDialog alertDialog;

        String name = "nothing";
        Background2(Context cnx){
            context = cnx;
        }



        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
             String name="";
            if (type.equals("display")) {
                String data2 = "error";

                try {
                    URL url = new URL(urll);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String result = "", line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();


                    try {
                        JSONObject jsonRootObject = new JSONObject(result);

                        //Get the instance of JSONArray that contains JSONObjects
                        JSONArray jsonArray = jsonRootObject.optJSONArray("result");

                        //Iterate the jsonArray and print the info of JSONObjects
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);


                             name = jsonObject.optString("textt").toString();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return name;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "ERROR";
            }



            return null;
        }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {



            liset(result);
            return;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
