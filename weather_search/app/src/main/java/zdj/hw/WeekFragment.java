package zdj.hw;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.HIGradient;
import com.highsoft.highcharts.common.HIStop;
import com.highsoft.highcharts.core.*;
import com.highsoft.highcharts.common.hichartsclasses.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public String json_data;

    public WeekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekFragment newInstance(String param1, String param2) {
        WeekFragment fragment = new WeekFragment();
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

        View view = inflater.inflate(R.layout.fragment_week, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        json_data = bundle.getString("data");
        JSONArray temp2 = null;
        DecimalFormat df= new DecimalFormat("######0.00");
        Object[][] seriesData = new Object[15][3];

        try {
            temp2 = new JSONArray(json_data);
            JSONArray temp4=temp2.getJSONObject(2).getJSONArray("intervals");
            int num=temp4.length();

            for (int i=0; i<15; i++) {
                JSONObject temp6=temp4.getJSONObject(i).getJSONObject("values");
                String str_date=temp4.getJSONObject(i).getString("startTime").split("T")[0];
//                long str_dat=Long.valueOf(new Date(temp4.getJSONObject(i).getString("startTime")));
                double temper_high= temp6.getDouble("temperatureMax");
                double temper_low= temp6.getDouble("temperatureMin");

//                seriesData[i]= new Object[]{i, temper_low, temper_high};
                seriesData[i]= new Object[]{str_date, temper_low, temper_high};
//                df.format(temp6.getDouble("precipitationProbability"))
            }



        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("highcharts error!");
        }

        HIChartView chartView = (HIChartView) view.findViewById(R.id.hc);
        HIOptions options = new HIOptions();
        HIChart chart = new HIChart();
        chart.setType("arearange");
        chart.setZoomType("x");
        options.setChart(chart);

        HITitle title = new HITitle();
        title.setText("Temperature variation by day");
        options.setTitle(title);

        HIXAxis xaxis = new HIXAxis();
//            xaxis.setType("datetime");
        options.setXAxis(new ArrayList<HIXAxis>(){{add(xaxis);}});

        HIYAxis yaxis = new HIYAxis();
        yaxis.setTitle(new HITitle());
        options.setYAxis(new ArrayList<HIYAxis>(){{add(yaxis);}});

        HITooltip tooltip = new HITooltip();
        tooltip.setShadow(true);
        tooltip.setValueSuffix("°F");
        options.setTooltip(tooltip);

        HILegend legend = new HILegend();
        legend.setEnabled(false);
        options.setLegend(legend);
        HIGradient gradient=new HIGradient(0,0,0,1);
        LinkedList<HIStop> stops = new LinkedList<>();
        stops.add(new HIStop(0, HIColor.initWithRGB(247, 163, 92)));
        stops.add(new HIStop(1, HIColor.initWithRGB(124, 181, 236)));

        HIArearange series = new HIArearange();
        series.setName("Temperatures");

        series.setFillColor(HIColor.initWithLinearGradient(gradient,stops));
        series.setLineColor(HIColor.initWithLinearGradient(gradient,stops));
        HIMarker hiMarker=new HIMarker();
        hiMarker.setFillColor(HIColor.initWithLinearGradient(gradient,stops));
        series.setMarker(hiMarker);
        series.setData(new ArrayList<>(Arrays.asList(seriesData)));
        options.setSeries(new ArrayList<>(Arrays.asList(series)));

        chartView.setOptions(options);
        return view;
    }
}