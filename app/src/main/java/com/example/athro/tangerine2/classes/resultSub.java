package com.example.athro.tangerine2.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 * Created by athro on 10/22/2016.
 */

public class resultSub {
    Context context;
    String type ;

    int min,second;
    int tId;
    int nWords;
    int nwWords;
    int ifId;

    public resultSub(Context context, int second, int min, String type ,int nWords,int nwWords,int infId) {

        this.ifId = infId;
        this.context = context;
        this.second = second;
        this.min = min;

        this.type = type;
        this.nWords = nWords;
        this.nwWords = nwWords;

        resultSub.Background2 bc = new resultSub.Background2(context);
        bc.execute("testId");

    }

    public void testIdm (int id , int j){
        tId = id;
        tId ++ ;
        if(j ==1){
            String time = ""+Integer.toString(min)+":"+Integer.toString(second)+":00";
            resultSub.Background2 bc = new resultSub.Background2(context);
            bc.execute("creatText",Integer.toString(tId),type,time,Integer.toString(nWords),Integer.toString(nwWords),Integer.toString(ifId));
        }
        return;
    }


    public class Background2 extends AsyncTask<String ,Void , String> {



        Context context;
        AlertDialog alertDialog;
        String type;
        List<dataWord> wList= new LinkedList<>();
        String t="";
        String ur = "http://clt.aui.ma/egramaroc";

        int testId;
        Background2(Context cnx){
            context = cnx;
        }

        @Override
        protected String doInBackground(String... params) {
            this.type = params[0];

            if (type.equals("testId")) {

                String login_url = ur+"/getTestId.php";

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
                String login_url = ur+"/insertTest.php";
                t = params[2];
                Calendar c = Calendar.getInstance();
                String tdate = Integer.toString(c.get(Calendar.YEAR))+"-"+Integer.toString(c.get(Calendar.MONTH))+"-"+Integer.toString(c.get(Calendar.DAY_OF_MONTH));

                try {
                    URL url =  new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));
                    String post_data = URLEncoder.encode("date","UTF-8") +  "=" + URLEncoder.encode(tdate ,  "UTF-8")+ "&"
                            + URLEncoder.encode("id","UTF-8") +  "=" + URLEncoder.encode(params[1] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("type","UTF-8") +  "=" + URLEncoder.encode(params[2] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("time","UTF-8") +  "=" + URLEncoder.encode(params[3] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("nwrods","UTF-8") +  "=" + URLEncoder.encode(params[4] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("tId","UTF-8") +  "=" + URLEncoder.encode(params[6] ,  "UTF-8")+ "&"
                            + URLEncoder.encode("nwwrods","UTF-8") +  "=" + URLEncoder.encode(params[5] ,  "UTF-8");

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
                    Log.e("myErrorsEx",e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("myErrorsEx",e.getMessage());
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

           if (type.equals("testId")){
               testIdm(testId , 1);
               return;
           }
            else if (type.equals("creatText")){
               testIdm(testId , 2);

               int duration = Toast.LENGTH_SHORT;
               Toast toast = Toast.makeText(context, "تم التثثبيت", duration);
               toast.show();
               return;

           }

            return;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
