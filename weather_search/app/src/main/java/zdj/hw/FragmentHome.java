package zdj.hw;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public JSONArray temp2;
    public String pass_location;
    public String tem;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        System.out.println("------frag new instance");
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("------frag oncreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public void getSelfLocation(View view) {
        String url = "https://ipinfo.io?token=3a5ed5e939230a";
        TextView tv1=(TextView) view.findViewById(R.id.text1);
        TextView tv3=(TextView) view.findViewById(R.id.text3);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String city=response.getString("city");
                            String region=response.getString("region");
                            String location=city+", "+region;
                            pass_location=location;
                            String loc=response.getString("loc");
                            String[] str_list=loc.split(",");
                            String lat=str_list[0];
                            String lon=str_list[1];
                            tv3.setText(location);
                            getDetail(view, lat, lon, requestQueue);

                        } catch (JSONException e) {
                            System.out.println("getSelfLocation wrong!");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.println("error!");
                    }
                });
        requestQueue.add(jsonObjectRequest);

    }

    public void getDetail(View view, String lat, String lon, RequestQueue requestQueue) {
//        https://hw9try2.ue.r.appspot.com/back?lat=34&lon=-118
        String url = "https://hw9try2.ue.r.appspot.com/back?lat="+lat+"&lon="+lon;
        System.out.println(url);
        DecimalFormat df= new DecimalFormat("######0.00");
        TextView tv1=(TextView) view.findViewById(R.id.text1);
        TextView tv2=(TextView) view.findViewById(R.id.text2);
        ImageView img1=(ImageView) view.findViewById(R.id.imageView1);
        TextView tv3=(TextView) view.findViewById(R.id.humidty);
        TextView tv4=(TextView) view.findViewById(R.id.wind);
        TextView tv5=(TextView) view.findViewById(R.id.visibility);
        TextView tv6=(TextView) view.findViewById(R.id.pressure);

        TextView date[]=new TextView[7];
        date[0]=(TextView) view.findViewById(R.id.date1);
        date[1]=(TextView) view.findViewById(R.id.date2);
        date[2]=(TextView) view.findViewById(R.id.date3);
        date[3]=(TextView) view.findViewById(R.id.date4);
        date[4]=(TextView) view.findViewById(R.id.date5);
        date[5]=(TextView) view.findViewById(R.id.date6);
        date[6]=(TextView) view.findViewById(R.id.date7);
        ImageView sun[]=new ImageView[7];
        sun[0]=(ImageView) view.findViewById(R.id.sun1);
        sun[1]=(ImageView) view.findViewById(R.id.sun2);
        sun[2]=(ImageView) view.findViewById(R.id.sun3);
        sun[3]=(ImageView) view.findViewById(R.id.sun4);
        sun[4]=(ImageView) view.findViewById(R.id.sun5);
        sun[5]=(ImageView) view.findViewById(R.id.sun6);
        sun[6]=(ImageView) view.findViewById(R.id.sun7);
        TextView low[]=new TextView[7];
        low[0]=(TextView) view.findViewById(R.id.low1);
        low[1]=(TextView) view.findViewById(R.id.low2);
        low[2]=(TextView) view.findViewById(R.id.low3);
        low[3]=(TextView) view.findViewById(R.id.low4);
        low[4]=(TextView) view.findViewById(R.id.low5);
        low[5]=(TextView) view.findViewById(R.id.low6);
        low[6]=(TextView) view.findViewById(R.id.low7);
        TextView high[]=new TextView[7];
        high[0]=(TextView) view.findViewById(R.id.high1);
        high[1]=(TextView) view.findViewById(R.id.high2);
        high[2]=(TextView) view.findViewById(R.id.high3);
        high[3]=(TextView) view.findViewById(R.id.high4);
        high[4]=(TextView) view.findViewById(R.id.high5);
        high[5]=(TextView) view.findViewById(R.id.high6);
        high[6]=(TextView) view.findViewById(R.id.high7);

// ！！！！！！！！！！！！！
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    temp2=response.getJSONObject("data").getJSONArray("timelines");
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
                    System.out.println("getDetail wrong!");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                System.out.println("error!");
            }
        });
        requestQueue.add(jsonObjectRequest);
// ！！！！！！！！！！！！！！！！！

//        File file = new File(TestJson2.class.getResource("/jsonText.json").getFile());//获取项目根路径下的文件
//        String content = FileUtils.readFileToString(file);
//        JSONObject jsonObject = JSONObject.fromObject(content);
//        System.out.println("jsonObject="+jsonObject);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("------frag oncreateview");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        FrameLayout frameLayout=(FrameLayout) view.findViewById(R.id.home_layout);
//        frameLayout.setVisibility(View.GONE);
        getSelfLocation(view);
        System.out.println("------frag oncreateview getlocation done");
        // Inflate the layout for this fragment
        CardView card=(CardView) view.findViewById(R.id.card1);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DetailActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("data",temp2.toString());
                bundle.putString("loc", pass_location);
                bundle.putString("tem", tem);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;

    }
}