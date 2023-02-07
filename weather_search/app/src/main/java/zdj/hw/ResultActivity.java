package zdj.hw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class ResultActivity extends AppCompatActivity {
    public String loc;
    public JSONArray temp2;
    public String tem;
    public SharedPreferences sharedPreferences;
    public String flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = this.getIntent().getExtras();
        loc = bundle.getString("loc");
        String temp =bundle.getString("data");

        sharedPreferences = getSharedPreferences("my_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        flag=sharedPreferences.getString(loc, "");

        Toolbar myToolbar=(Toolbar) findViewById(R.id.result_toolbar);
        myToolbar.setTitle(loc);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayout progress=(LinearLayout) findViewById(R.id.progress_layout_result);
        CoordinatorLayout mainContent=(CoordinatorLayout) findViewById(R.id.result_content);
        progress.setVisibility(View.VISIBLE);

        TextView tv33=(TextView) findViewById(R.id.text3);
        tv33.setText(loc);
        TextView tv1=(TextView) findViewById(R.id.text1);
        TextView tv2=(TextView) findViewById(R.id.text2);
        ImageView img1=(ImageView) findViewById(R.id.imageView1);
        TextView tv3=(TextView) findViewById(R.id.humidty);
        TextView tv4=(TextView) findViewById(R.id.wind);
        TextView tv5=(TextView) findViewById(R.id.visibility);
        TextView tv6=(TextView) findViewById(R.id.pressure);

        TextView date[]=new TextView[7];
        date[0]=(TextView) findViewById(R.id.date1);
        date[1]=(TextView) findViewById(R.id.date2);
        date[2]=(TextView) findViewById(R.id.date3);
        date[3]=(TextView) findViewById(R.id.date4);
        date[4]=(TextView) findViewById(R.id.date5);
        date[5]=(TextView) findViewById(R.id.date6);
        date[6]=(TextView) findViewById(R.id.date7);
        ImageView sun[]=new ImageView[7];
        sun[0]=(ImageView) findViewById(R.id.sun1);
        sun[1]=(ImageView) findViewById(R.id.sun2);
        sun[2]=(ImageView) findViewById(R.id.sun3);
        sun[3]=(ImageView) findViewById(R.id.sun4);
        sun[4]=(ImageView) findViewById(R.id.sun5);
        sun[5]=(ImageView) findViewById(R.id.sun6);
        sun[6]=(ImageView) findViewById(R.id.sun7);
        TextView low[]=new TextView[7];
        low[0]=(TextView) findViewById(R.id.low1);
        low[1]=(TextView) findViewById(R.id.low2);
        low[2]=(TextView) findViewById(R.id.low3);
        low[3]=(TextView) findViewById(R.id.low4);
        low[4]=(TextView) findViewById(R.id.low5);
        low[5]=(TextView) findViewById(R.id.low6);
        low[6]=(TextView) findViewById(R.id.low7);
        TextView high[]=new TextView[7];
        high[0]=(TextView) findViewById(R.id.high1);
        high[1]=(TextView) findViewById(R.id.high2);
        high[2]=(TextView) findViewById(R.id.high3);
        high[3]=(TextView) findViewById(R.id.high4);
        high[4]=(TextView) findViewById(R.id.high5);
        high[5]=(TextView) findViewById(R.id.high6);
        high[6]=(TextView) findViewById(R.id.high7);
        DecimalFormat df= new DecimalFormat("######0.00");

        try {
            temp2 = new JSONArray(temp);
            JSONArray temp4=temp2.getJSONObject(0).getJSONArray("intervals");
            JSONObject temp6=temp4.getJSONObject(0).getJSONObject("values");

            float temper= (float) temp6.getDouble("temperature");
            tem=String.valueOf(temper);
            tv1.setText(Math.round(temper)+"°F");
            WeatherCode weather=new WeatherCode();
            String wName=weather.weatherName(temp6.getInt("weatherCode"));
            tv2.setText(wName);
            img1.setImageResource(weather.weatherUrl(temp6.getInt("weatherCode")));

            tv3.setText(temp6.getInt("humidity")+"%");
            tv4.setText(df.format(temp6.getDouble("windSpeed"))+"mph");
            tv5.setText(df.format(temp6.getDouble("visibility"))+"mi");
            tv6.setText(df.format(temp6.getDouble("pressureSeaLevel"))+"inHg");


            JSONArray temp_week=temp2.getJSONObject(2).getJSONArray("intervals");
            for (int i=0; i<7; i++) {
                JSONObject temp_day=temp_week.getJSONObject(i);
                JSONObject temp_detail=temp_day.getJSONObject("values");
                String str_date=temp_day.getString("startTime").split("T")[0];
                date[i].setText(str_date);
                sun[i].setImageResource(weather.weatherUrl(temp_detail.getInt("weatherCode")));
                float temp_low= (float) temp_detail.getDouble("temperatureMin");
                low[i].setText(String.valueOf(Math.round(temp_low)));
                float temp_high= (float) temp_detail.getDouble("temperatureMax");
                high[i].setText(String.valueOf(Math.round(temp_high)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent= new Intent(ResultActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        CardView card=(CardView) findViewById(R.id.card1);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResultActivity.this, DetailActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("data",temp2.toString());
                bundle.putString("loc", loc);
                bundle.putString("tem", tem);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        if (flag== null || flag.length() == 0) {
            fab.setImageResource(R.drawable.map_marker_plus);
        }
        else {
            fab.setImageResource(R.drawable.map_marker_minus);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag== null || flag.length() == 0){
                    String str=loc+" was added to favorites";
                    fab.setImageResource(R.drawable.map_marker_minus);
                    Toast.makeText(ResultActivity.this, str, Toast.LENGTH_LONG).show();
                    editor.putString(loc, temp);
                    editor.commit();
                    flag=sharedPreferences.getString(loc, "");
                }
                else {
                    String str=loc+" was removed from favorites";
                    fab.setImageResource(R.drawable.map_marker_plus);
                    Toast.makeText(ResultActivity.this, str, Toast.LENGTH_LONG).show();
                    editor.remove(loc);
                    editor.commit();
                    flag="";
                }
            }
        });

        new Timer().schedule(new TimerTask() {
            public void run() {
                progress.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        progress.setVisibility(View.GONE);
                    }
                });
            }
        }, 4000);

    }
}