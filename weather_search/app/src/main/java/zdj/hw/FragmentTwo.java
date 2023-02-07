package zdj.hw;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTwo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public String mParam1;
//    public String loc;
    public String data;
    public String mParam2;

    public FragmentTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTwo newInstance(String param1, String param2) {
        FragmentTwo fragment = new FragmentTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void getInfo(View view) {
        TextView tv1=(TextView) view.findViewById(R.id.text11);
        TextView tv33=(TextView) view.findViewById(R.id.text13);
        tv33.setText(mParam1);
        DecimalFormat df= new DecimalFormat("######0.00");
        TextView tv2=(TextView) view.findViewById(R.id.text12);
        ImageView img1=(ImageView) view.findViewById(R.id.imageView11);
        TextView tv3=(TextView) view.findViewById(R.id.humidty1);
        TextView tv4=(TextView) view.findViewById(R.id.wind1);
        TextView tv5=(TextView) view.findViewById(R.id.visibility1);
        TextView tv6=(TextView) view.findViewById(R.id.pressure1);

        TextView date[]=new TextView[7];
        date[0]=(TextView) view.findViewById(R.id.date11);
        date[1]=(TextView) view.findViewById(R.id.date12);
        date[2]=(TextView) view.findViewById(R.id.date13);
        date[3]=(TextView) view.findViewById(R.id.date14);
        date[4]=(TextView) view.findViewById(R.id.date15);
        date[5]=(TextView) view.findViewById(R.id.date16);
        date[6]=(TextView) view.findViewById(R.id.date17);
        ImageView sun[]=new ImageView[7];
        sun[0]=(ImageView) view.findViewById(R.id.sun11);
        sun[1]=(ImageView) view.findViewById(R.id.sun12);
        sun[2]=(ImageView) view.findViewById(R.id.sun13);
        sun[3]=(ImageView) view.findViewById(R.id.sun14);
        sun[4]=(ImageView) view.findViewById(R.id.sun15);
        sun[5]=(ImageView) view.findViewById(R.id.sun16);
        sun[6]=(ImageView) view.findViewById(R.id.sun17);
        TextView low[]=new TextView[7];
        low[0]=(TextView) view.findViewById(R.id.low11);
        low[1]=(TextView) view.findViewById(R.id.low12);
        low[2]=(TextView) view.findViewById(R.id.low13);
        low[3]=(TextView) view.findViewById(R.id.low14);
        low[4]=(TextView) view.findViewById(R.id.low15);
        low[5]=(TextView) view.findViewById(R.id.low16);
        low[6]=(TextView) view.findViewById(R.id.low17);
        TextView high[]=new TextView[7];
        high[0]=(TextView) view.findViewById(R.id.high11);
        high[1]=(TextView) view.findViewById(R.id.high12);
        high[2]=(TextView) view.findViewById(R.id.high13);
        high[3]=(TextView) view.findViewById(R.id.high14);
        high[4]=(TextView) view.findViewById(R.id.high15);
        high[5]=(TextView) view.findViewById(R.id.high16);
        high[6]=(TextView) view.findViewById(R.id.high17);

        JSONArray temp2 = null;
        try {
            temp2 = new JSONArray(mParam2);
            JSONArray temp4=temp2.getJSONObject(0).getJSONArray("intervals");
            JSONObject temp6=temp4.getJSONObject(0).getJSONObject("values");
            float temper= (float) temp6.getDouble("temperature");
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
            System.out.println("fragment two json error!");
        }

    }

    public String getmParam1() {
        return mParam1;
    }

    public String getmParam2() {
        return mParam2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_two, container, false);
        System.out.println("-----parqam1: "+mParam1);
        getInfo(view);
        CardView card=(CardView) view.findViewById(R.id.card11);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DetailActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("data",mParam2);
                bundle.putString("loc", mParam1);
//                bundle.putString("tem", tem);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("my_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        return view;

    }
}