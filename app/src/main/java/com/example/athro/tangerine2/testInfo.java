package com.example.athro.tangerine2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.athro.tangerine2.classes.dataWord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by athro on 10/23/2016.
 */

public class testInfo  extends Activity implements View.OnClickListener {
    EditText texts0;
    EditText texts1;
    EditText texts2;
    EditText texts3;
    EditText texts4;
    EditText texts5;
    EditText texts6;
    EditText texts7;
    EditText texts8;

    RadioButton gr1;
    RadioButton gr2;
    RadioButton gr3;
    RadioButton gr4;
    RadioButton gr5;
    RadioButton gr6;
    RadioButton gr7;


    String ary0;
    String ary1;
    String ary2 ;
    String ary3 ;
    String ary4 ;
    String ary5 ;
    String ary6;
    String ary7 ;
    String ary8 ;
    //String ary[];
    int tId = 99;
    int re = 0;
    Context context = this;
    Button btn;
    Button btn1;
    String rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testinfo);
        texts0= (EditText) findViewById(R.id.editText6);
        texts1= (EditText) findViewById(R.id.editText5);
        texts2= (EditText) findViewById(R.id.editText7);
        texts3= (EditText) findViewById(R.id.editText8);
        texts4= (EditText) findViewById(R.id.editText9);
        texts5= (EditText) findViewById(R.id.editText10);

        rl=getString(R.string.ur);




        btn1 = (Button)findViewById(R.id.Button231) ;

        btn1.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button23) {

            if(gr1.isChecked()){
                ary4 = gr1.getText().toString();
            }
            else if(gr2.isChecked()){
                ary4 = gr2.getText().toString();
            }
            else if(gr3.isChecked()){
                ary4 = gr3.getText().toString();
            }
            if(gr4.isChecked()){
                ary5 = gr4.getText().toString();
            }
            else if(gr5.isChecked()){
                ary5 = gr5.getText().toString();
            }
            if(gr6.isChecked()){
                ary6 = gr6.getText().toString();
            }
            else if(gr7.isChecked()){
                ary6 = gr7.getText().toString();
            }

            if((!gr1.isChecked() & !gr2.isChecked() & !gr3.isChecked()) || (!gr4.isChecked() & !gr5.isChecked())||(!gr6.isChecked() & !gr7.isChecked())){


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("المرجو ملء جميع المعلومات")
                        .setCancelable(false)
                        .setPositiveButton("نهاية", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return;
            }

            testInfo.Background2 bc = new testInfo.Background2(context);
            bc.execute("testId");



            return;

        }
        else{
            ary7 = texts0.getText().toString();
            ary0 = texts1.getText().toString();
            ary1 = texts2.getText().toString();
            ary2 = texts3.getText().toString();
            ary3 = texts4.getText().toString();
            ary8 = texts5.getText().toString();
            if(ary0.isEmpty() ||ary1.isEmpty() || ary2.isEmpty() ||ary3.isEmpty() || ary7.isEmpty() || ary8.isEmpty()){

                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setMessage("المرجو ملء جميع المعلومات");
                alertDialog.show();
                return;
            }
            setContentView(R.layout.testinfo2);
            btn = (Button)findViewById(R.id.button23) ;
            btn.setOnClickListener(this);
            gr1 = (RadioButton) findViewById(R.id.rdb1) ;
            gr2 = (RadioButton) findViewById(R.id.rdb2) ;
            gr3 = (RadioButton) findViewById(R.id.rdb3) ;
            gr4 = (RadioButton) findViewById(R.id.rdb4) ;
            gr5 = (RadioButton) findViewById(R.id.rdb5) ;
            gr6 = (RadioButton) findViewById(R.id.rdb6) ;
            gr7 = (RadioButton) findViewById(R.id.rdb7) ;
        }
    }



    public void testIdm (int id , int j){

        if(j ==1){

            tId = id;
            tId ++ ;
            re ++ ;
            Intent intent = new Intent(this, menu.class);
            intent.putExtra("tId",tId);
            startActivity(intent);


            String idt = Integer.toString(tId);
            testInfo.Background2 bc = new testInfo.Background2(context);
            bc.execute("creatText",idt,ary0,ary1,ary2,ary3,ary4,ary5,ary6,ary7,ary8);

        }

        return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(re !=0 ){
            this.finish();
        }
    }

    public class Background2 extends AsyncTask<String ,Void , String> {
        Context context;
        AlertDialog alertDialog;
        String type;
        List<dataWord> wList= new LinkedList<>();
        String t="";
        int testId;
        Background2(Context cnx){
            context = cnx;
        }

        @Override
        protected String doInBackground(String... params) {
            this.type = params[0];

            if (type.equals("testId")) {

                String login_url = rl+"/getTestiId.php";

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
                            int id = jsonObject.optInt("id");

                            testId =id;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return "error";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "ERROR";
            } else if (type.equals("creatText")) {
                String login_url = rl+"/insertTesti.php";
                t = params[2];
                Calendar c = Calendar.getInstance();
                String tdate = Integer.toString(c.get(Calendar.YEAR))+"-"+Integer.toString(c.get(Calendar.MONTH))+"-"+Integer.toString(c.get(Calendar.MONTH));

                try {
                    URL url =  new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));
                    String post_data = URLEncoder.encode("id","UTF-8") +  "=" + URLEncoder.encode(params[1] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("pName","UTF-8") +  "=" + URLEncoder.encode(params[2] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("sName","UTF-8") +  "=" + URLEncoder.encode(params[3] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("rName","UTF-8") +  "=" + URLEncoder.encode(params[4] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("cName","UTF-8") +  "=" + URLEncoder.encode(params[5] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("tSchool","UTF-8") +  "=" + URLEncoder.encode(params[6] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("mClass","UTF-8") +  "=" + URLEncoder.encode(params[7] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("sLevel","UTF-8") +  "=" + URLEncoder.encode(params[8] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("tName","UTF-8") +  "=" + URLEncoder.encode(params[9] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("sAge","UTF-8") +  "=" + URLEncoder.encode(params[10] ,  "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream , "iso-8859-1"));
                    String result="" , line;
                    while ((line = bufferedReader.readLine()) != null ){
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.e("myError","result before passing:" +result);
                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Log in");
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("myError","result after passing :"+ result);
            try {
                testId = Integer.parseInt(result);
            }catch (NumberFormatException e){
                Log.e("myError","error in number parsing");
                //return;
            }

            if (type.equals("testId")){

                testIdm(testId , 1);

                return;
            }
            else if (type.equals("creatText")){

                testIdm(testId , 2);

                return;

            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
