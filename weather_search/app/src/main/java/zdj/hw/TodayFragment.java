package zdj.hw;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public String json_data;

    public TodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayFragment newInstance(String param1, String param2) {
        TodayFragment fragment = new TodayFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        json_data = bundle.getString("data");

        TextView td1=(TextView) view.findViewById(R.id.today_1);
        TextView td2=(TextView) view.findViewById(R.id.today_2);
        TextView td3=(TextView) view.findViewById(R.id.today_3);
        TextView td4=(TextView) view.findViewById(R.id.today_4);
        TextView td5=(TextView) view.findViewById(R.id.today_5);
        TextView td6=(TextView) view.findViewById(R.id.today_6);
        TextView td7=(TextView) view.findViewById(R.id.today_7);
        TextView td8=(TextView) view.findViewById(R.id.today_8);
        TextView td9=(TextView) view.findViewById(R.id.today_9);
        ImageView td=(ImageView) view.findViewById(R.id.td_img);

        try {
            JSONArray temp2 = new JSONArray(json_data);
            JSONArray temp4=temp2.getJSONObject(0).getJSONArray("intervals");
            JSONObject temp6=temp4.getJSONObject(0).getJSONObject("values");
            DecimalFormat df= new DecimalFormat("######0.00");

            td1.setText(df.format(temp6.getDouble("windSpeed"))+" mph");
            td2.setText(df.format(temp6.getDouble("pressureSeaLevel"))+" inHg");
            td3.setText(df.format(temp6.getDouble("precipitationProbability"))+" %");
            float temper= (float) temp6.getDouble("temperature");
            td4.setText(Math.round(temper)+"°F");
            WeatherCode weather=new WeatherCode();
            String wName=weather.weatherName(temp6.getInt("weatherCode"));
            td5.setText(wName);
            td.setImageResource(weather.weatherUrl(temp6.getInt("weatherCode")));
            td6.setText(temp6.getInt("humidity")+" %");
            td7.setText(df.format(temp6.getDouble("visibility"))+" mi");
            td8.setText(temp6.getInt("cloudCover")+"%");
            td9.setText(df.format(temp6.getDouble("uvIndex")));

        } catch (JSONException e) {
            System.out.println("json error!");
            e.printStackTrace();
        }

        return view;

    }
}