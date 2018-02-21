package com.example.athro.tangerine2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
 * Created by athro on 11/18/2016.
 */

public class test4 extends Activity implements  View.OnClickListener{

    String login_url ;
    Button submite;
    Context context = this;
    TextView t3 ;
    TextView t4 ;
    int min;
    int sec;
    int flag =0;
    List<Button> blist;
    List<dataWord> wList;
    String type;
    resultSub resultS;
    Button redB ;
    Button tbuton;
    int cnt = 0;
    int log;
    int pos = -1;
    int f = 0;
    int tId;
    int m ;
    int tstart = 0;
    CountDownTimer cn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle ur = getIntent().getExtras();

        login_url = getString(R.string.ur)+"/getTest4.php";
        log = ur.getInt("log");
        tId = ur.getInt("tId");
        wList =new LinkedList<>();
        test4.Background2 bc = new test4.Background2(this);
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
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("انته الواقت");
                alertDialog.setMessage("لقد انته الواقت، المرجو الضغط على الكلمة التي توقف فيها الطالب");

                alertDialog.show();

            }
        };
        cn.start();
    }

    public void liset ( List<dataWord> wList){

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
                if(tstart != 0 ) cn.cancel();
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
                b.setTag(total);
                b.setId(total);
                r.addView(b);
                b.setText(wList.get(total).getWord());

                b.setTextSize(30);
                b.setOnClickListener(this);
                //blist.add(b);
                m++;
                total ++;
            }
            tab.addView(r);
            ac.getWidth();
        }
        return;
    }
    public void endall (){
        if (tstart != 0) cn.cancel();
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(f != 0){
            endall();
        }
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
            pos= m;
            Intent intent = new Intent(this, textactivity.class);
            intent.putExtra("url",login_url);
            intent.putExtra("type",1);
            intent.putExtra("log",log);
            intent.putExtra("pos",pos);
            intent.putExtra("tId",tId);
            startActivity(intent);
            f++;
            return;

        }
        int id = view.getId();
        if(flag == 0 ){
            if(blist.contains(view)){
                view.setBackgroundColor(GRAY);
                blist.remove(view);
                if((int) view.getTag() == 0  ||(int) view.getTag() == 1 || (int)view.getTag()== 2 ||(int) view.getTag()== 3 || (int)view.getTag() == 4 ){

                    cnt--;
                }
                else if((int) view.getTag() == 5  ||(int) view.getTag() == 6 || (int)view.getTag()== 7){
                    cnt--;
                }

            }
            else {

                view.setBackgroundColor(2);
                blist.add((Button)view);

                if((int) view.getTag() == 0  ||(int) view.getTag() == 1 || (int)view.getTag()== 2 ||(int) view.getTag()== 3 || (int)view.getTag() == 4 ){

                    cnt++;
                }
                else if((int) view.getTag() == 5  ||(int) view.getTag() == 6 || (int)view.getTag()== 7){
                    cnt++;
                }

            }
            if(cnt == 8){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(" لم يقرأ الطفل أي كلمة صحيحة في السطر الأول");
                builder.setMessage("قل شكرا ومر إلى التمرين التالي")
                        .setCancelable(false)
                        .setPositiveButton("نهاية", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        }
        else{

            view.setBackgroundColor(RED);
            //redB = (Button) view.getTag();
            pos = (int) view.getTag();
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

                            wList . add(new dataWord(id , name,i));
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
