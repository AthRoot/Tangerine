package com.example.athro.tangerine2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.athro.tangerine2.classes.dataWord;
import com.example.athro.tangerine2.classes.resultSub;

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

import static android.graphics.Color.GRAY;
import static android.graphics.Color.RED;

/**
 * Created by athro on 10/7/2016.
 */

public class words extends Activity  implements View.OnClickListener{


    String login_url , type ;
    Button submite;
    Context context = this;
    TextView t3 ;
    TextView t4 ;
    int min,sec,flag =0,tId,tstart= 0 ,cnt = 0;
    List<Button> blist;
    List<dataWord> wList;
    resultSub resultS;
    dataWord redB ;
    Button tbuton;
    CountDownTimer cn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle ur = getIntent().getExtras();

        login_url = ur.getString("url");
        type = ur.getString("type");
        tId = ur.getInt("tId");
        wList =new LinkedList<>();
        words.Background2 bc = new words.Background2(this);
        bc.execute("display");
        submite = (Button) findViewById(R.id.sub);
        submite.setOnClickListener(this);



    }
    public void timer (){
        cn = new CountDownTimer(60* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                t4.setText("" + String.format("%02d", minutes));
                t4.setText("" + String.format("%02d", seconds));
                min=minutes;
                sec=seconds;
            }

            public void onFinish() {
                flag = 1;
                t3.setText("00");
                t4.setText("00");
                min = 00;
                sec = 00;
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("انته الوقت");
                alertDialog.setMessage("لقد انته الوقت، المرجو الضغط على الكلمة التي توقف فيها التلميذ");
                alertDialog.show();

            }
        };
        cn.start();
    }

    @Override
    protected void onDestroy() {
        if(tstart != 0) cn.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(tstart != 0) cn.cancel();
        super.onBackPressed();
    }

    public void liset (List<dataWord> wList){

        this.wList = wList;
        int ind = wList.size();
        int in ;
        int total=0;
        blist = new LinkedList<Button>();
        TableLayout tab = (TableLayout) findViewById(R.id.table);


        RelativeLayout ac = (RelativeLayout) findViewById(R.id.activity_main);
        t3 = (TextView) findViewById(R.id.textView3);
        t4 = (TextView) findViewById(R.id.textView4);
        tbuton = (Button) findViewById(R.id.tbutton);
        tbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tstart == 1) cn.cancel();
                timer();
                tstart = 1;
                /*tbuton.setClickable(false);
                tbuton.setVisibility(View.GONE);*/
            }
        });


        int xe = submite.getWidth()/400;
        in = ind / xe;
        for(int i =0 ; i<=in ; i++) {
            TableRow r = new TableRow(this);
            for(int j=0 ; ((j< xe) ) ; j++) {
                if(total >= ind) break;
                Button b = new Button(this);
                // b.setId(total);
                b.setTag(wList.get(total));
                b.setId(total);
                r.addView(b);
                b.setText(wList.get(total).getWord());
                b.setTextSize(30);
                b.setOnClickListener(this);
                //blist.add(b);
                total ++;
            }
            tab.addView(r);
            ac.getWidth();
        }
        return;
    }

    public void sunclick (){


    }


    public void endall (){
        if(tstart != 0) cn.cancel();
        this.finish();
    }


    @Override
    public void onClick(View view) {
        if(tstart == 0){
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("لم يبدأ العداد");
            alertDialog.setMessage("المرجو بدأ العداد أولا");
            alertDialog.show();
            return;
        }

        if(view.getId() == R.id.sub){
            int nWords;

            if(flag == 0){
                nWords = wList.size();
            }
            else{
                int i ;
                for( i=0; i<wList.size() ; i++){
                    if(wList.get(i).equals(redB)) break;
                }
                nWords = i;
            }


            resultS = new resultSub(this,sec,min,type,nWords,blist.size(),this.tId);
            endall();
            return;
        }
        int id = view.getId();
        if(flag == 0 ){
            if(blist.contains(view)){
                view.setBackgroundColor(GRAY);
                blist.remove(view);
                if((int) view.getId() == 0  ||(int) view.getId() == 1 || (int)view.getId()== 2 ||(int) view.getId()== 3 || (int)view.getId() == 4 ){
                    cnt--;
                }
                if((int) view.getId() == 5  ||(int) view.getId() == 6 || (int)view.getId()== 7){
                    cnt--;
                }
                if((int) view.getId() == 8  ||(int) view.getId() == 9){
                    cnt--;
                }
            }
            else {

                view.setBackgroundColor(2);
                blist.add((Button)view);
                if((int) view.getId() == 0  ||(int) view.getId() == 1 || (int)view.getId()== 2 ||(int) view.getId()== 3 || (int)view.getId() == 4 ){
                    cnt++;
                }
                if((int) view.getId() == 5  ||(int) view.getId() == 6 || (int)view.getId()== 7){
                    cnt++;
                }
                if((int) view.getId() == 8  ||(int) view.getId() == 9){
                    cnt++;
                }
            }
            if(cnt == 10){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(" لم يقرأ الطفل أي كلمة صحيحة في السطر الأول");
                builder.setMessage("قل شكرا ومر إلى التمرين التالي")
                        .setCancelable(false)
                        .setPositiveButton("نهاية", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                endall();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }
        }

        else{
            view.setBackgroundColor(RED);
            redB = (dataWord) view.getTag();
            //sunclick();
        }



    }

    public class Background2 extends AsyncTask<String ,Void , String> {
        Context context;
        AlertDialog alertDialog;

        List<dataWord> wList= new LinkedList<>();
        Background2(Context cnx){
            context = cnx;
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

                    String data = "";
                    try {
                        JSONObject jsonRootObject = new JSONObject(result);

                        //Get the instance of JSONArray that contains JSONObjects
                        JSONArray jsonArray = jsonRootObject.optJSONArray("result");

                        //Iterate the jsonArray and print the info of JSONObjects
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);


                            String name = jsonObject.optString("name").toString();
                            int id = jsonObject.optInt("id");

                            wList . add(new dataWord(id , name));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return wList.get(1).getWord();
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
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Log in");
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
