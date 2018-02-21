package com.example.athro.tangerine2;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button log = (Button) findViewById(R.id.log) ;
        log.setOnClickListener(this);

    }

    public void check (String result){

        if(result.equals("login success")){
            CharSequence text = "مرحبا";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
           Intent intent = new Intent(this, testInfoN.class);
            startActivity(intent);
       }
        else{

            Log.e("myerror","im in post");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("التثبيت");
            builder.setMessage("الاسم او الرقم السري خاطئ")
                    .setCancelable(false)
                    .setPositiveButton("نهاية", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        Log.e("myerror","im in post");
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
        if(!isNetworkAvailable(this)){
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("شبكة الإنترنت");
            alertDialog.setMessage("لم يتم توصيل الهاتف إلى شبكة الإنترنت");
            alertDialog.show();
            return;
        }
        EditText UsernameEt,PasswordEt;
        UsernameEt = (EditText) findViewById(R.id.etUserName);
        PasswordEt = (EditText) findViewById(R.id.etPassword);
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        MainActivity.Background1 bc = new Background1(this);

        bc.execute("login",username,password);


    }

    public class Background1 extends AsyncTask<String ,Void , String> {

        Context context;

        String ur =getString(R.string.ur);
        Background1(Context cnx){
            context = cnx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];




            if (type.equals("login")){
                String username = params[1];
                String password = params[2];
                String login_url = ur+"/login.php";
                try {
                    URL url =  new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));
                    String post_data = URLEncoder.encode("user_name","UTF-8") +  "=" + URLEncoder.encode(username ,  "UTF-8")+ "&"
                            + URLEncoder.encode("user_pass","UTF-8") +  "=" + URLEncoder.encode(password ,  "UTF-8");
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
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                    return ("error2s");
                } catch (IOException e) {


                    e.printStackTrace();

                    return ("error2s");
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("myerror",result);
            if(result.equals("error2s")){
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("مشكلة الخادم");
                alertDialog.setMessage("لا يمكننا الوصول إلى خادمنا");
                alertDialog.show();
                return;
            }
            check(result);
            return;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
