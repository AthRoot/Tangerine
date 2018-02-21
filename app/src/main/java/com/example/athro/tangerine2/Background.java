package com.example.athro.tangerine2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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
import java.util.LinkedList;
import java.util.List;

/**
 * Created by athro on 10/2/2016.
 */
public class Background extends AsyncTask<String ,Void , String> {
    Context context;
    AlertDialog alertDialog;
    List<String> le = new LinkedList<>();
    Background(Context cnx){
        context = cnx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];




        if (type.equals("display")){
            String data2 = "error";
            String login_url = "http://192.168.1.18/getLevels.php ";
            try {
                URL url =  new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                /*httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();*/
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream , "UTF-8"));
                String result="" , line;
                while ((line = bufferedReader.readLine()) != null ){
                    result += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                String data = "";
                try {
                    JSONObject jsonRootObject = new JSONObject(result);

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray = jsonRootObject.optJSONArray("result");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String name = jsonObject.optString("name").toString();

                        le.add(name);
                    }

                } catch (JSONException e) {e.printStackTrace();}

                return le.get(1);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "ERROR";
        }
        else if (type.equals("register")){
            String login_url = "http://clt.aui.ma/egramaroc/register.php ";
            try {
                URL url =  new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));
                //String post_data = URLEncoder.encode("username","UTF-8") +  "=" + URLEncoder.encode(username ,  "UTF-8")+ "&"
                //        + URLEncoder.encode("pass","UTF-8") +  "=" + URLEncoder.encode(password ,  "UTF-8");

               // bufferedWriter.write(post_data);
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

        return;

    }
    public List<String> getList(){

        return le;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}



