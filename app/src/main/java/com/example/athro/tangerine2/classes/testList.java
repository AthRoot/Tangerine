package com.example.athro.tangerine2.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
 * Created by athro on 10/29/2016.
 */

public class testList {
    List<textdata> dlist;
Context context;
    public testList(Context context) {
        this.context = context;
        this.dlist = new LinkedList<>();

    }
    public List<textdata> run(Context context){

        func();

        return dlist;
    }
    private void func (){
        testList.Background2 bc = new testList.Background2(context);
        bc.execute("display");

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "im in 2", duration);
        toast.show();

        return;
    }
    public void liset ( List<textdata> wList) {

        this.dlist = wList;
        return;
    }

    public class Background2 extends AsyncTask<String ,Void , String> {


        String login_url = "http://clt.aui.ma/egramaroc/getTextq.php";
        Context context;
        List<textdata> wList= new LinkedList<>();
        Background2(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];


            if (type.equals("display")) {
                String data2 = "error";

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
                            int id = jsonObject.optInt("id");

                            wList . add(new textdata(aquest,id , quest));

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


            liset( wList);
            return;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
