package com.example.athro.tangerine2;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athro.tangerine2.classes.resultSub;
import com.example.athro.tangerine2.classes.testList;
import com.example.athro.tangerine2.classes.textdata;

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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by athro on 10/28/2016.
 */

public class textactivity extends Activity implements View.OnClickListener{
    testList tests ;
    List <textdata> mlist;
    Button btn;
    Iterator<textdata> iter;
    TextView quest ;
    RadioButton ans ;
    RadioButton fls ;
    RadioButton nth ;
    int type;
    String login_url ;
    String urs;
    int log;
    int pos =-1;
    int total = 0;
    int car = 0;
    int tId;
    public textactivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        super.onCreate(savedInstanceState);
        Bundle ur = getIntent().getExtras();
        this.type = ur.getInt("type");
        log = ur.getInt("log");
        tId = ur.getInt("tId");


        urs =  getString(R.string.ur);
        if(log == 4){

            login_url = urs+"/getTextq.php";
        }
        else {
            pos = ur.getInt("pos");

            login_url = urs+"/getTextq2.php";
        }

        if (type==2){
            setContentView(R.layout.text);
            Button btn = (Button) findViewById(R.id.buttonnext);
            btn.setOnClickListener(this);
        }
        else{
            textactivity.Background2 bc = new textactivity.Background2(this);
            bc.execute("display");
        }

    }


    private void iterate (){
        textdata te ;
        ans.setChecked(false);
        fls.setChecked(false);
        nth.setChecked(false);
        if(log != 4){

            if(iter.hasNext() ){

                te = iter.next();
                if(te.getNwords() >= pos){
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(this, "تم التثثبيت", duration);
                    toast.show();
                    resultSub resultS;
                    resultS = new resultSub(this,00,00,Integer.toString(type),car,total,this.tId);
                    this.finish();
                }

                quest.setText(te.getQuestion());
                String st = "الصحيح: " + te.getAnswert();
                ans.setText(st);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ans.isChecked()){
                            car++;
                            total ++ ;
                        }
                        else {
                            total ++ ;
                        }
                        iterate();

                    }
                });
                return;
            }
            else {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, "تم التثثبيت", duration);
                toast.show();
                resultSub resultS;
                resultS = new resultSub(this,00,00,Integer.toString(type),total,total-car,this.tId);
                this.finish();

                //save data
            }
        }

        else {
            if(iter.hasNext()){

                te = iter.next();
                quest.setText(te.getQuestion());
                String st = "الإجابة الصحيحة: " + te.getAnswert();
                ans.setText(st);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ans.isChecked()){
                            car++;
                            total ++ ;
                        }
                        else {
                            total ++ ;
                        }
                        iterate();
                    }
                });
                return;
            }
            else {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, "تم التثثبيت", duration);
                toast.show();
                resultSub resultS;
                resultS = new resultSub(this,00,00,Integer.toString(type),total,total-car,this.tId);
                this.finish();

                //save data
            }
        }

        return;

    }

    public void func (List<textdata> test){

        mlist = test;

        setContentView(R.layout.textlayout);
        iter = mlist.iterator();
        quest = (TextView) findViewById(R.id.quesview);
        ans = (RadioButton) findViewById(R.id.choice1);
        fls = (RadioButton) findViewById(R.id.choice2);
        nth = (RadioButton) findViewById(R.id.choice3);
        btn = (Button) findViewById(R.id.submit);
        iterate();
        return;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonnext){
            textactivity.Background2 bc = new textactivity.Background2(this);
            bc.execute("display");
        }
    }


    public class Background2 extends AsyncTask<String ,Void , String> {



        Context context;
        List<textdata> wList= new LinkedList<>();
        Background2(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];


            if (type.equals("display")) {

                try {
                    URL url = new URL(login_url);
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


                            String quest = jsonObject.optString("quest").toString();
                            String aquest = jsonObject.optString("aquest").toString();
                            int nwords = jsonObject.optInt("nwords");
                            int id = jsonObject.optInt("id");

                            wList . add(new textdata(aquest,id , quest,nwords));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return"correct";
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
            //alertDialog = new AlertDialog.Builder(context).create();
            //alertDialog.setTitle("Log in");
        }

        @Override
        protected void onPostExecute(String result) {


            func( wList);
            return;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}


