package zdj.hw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DetailActivity extends AppCompatActivity {
    ViewPager vPage;
    List<String> titles;
    String loc;
    String tem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        loc =intent.getStringExtra("loc");
        tem=intent.getStringExtra("tem");

        Toolbar myToolbar=(Toolbar) findViewById(R.id.detail_toolbar);
        Button twitter=(Button) myToolbar.findViewById(R.id.btn_twitter);
        twitter.setBackgroundResource(R.drawable.ic_twitter_icon);
        myToolbar.setTitle(loc);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent= new Intent(DetailActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        titles = new ArrayList<>();
        titles.add("TODAY");
        titles.add("WEEKLY");
        titles.add("WEATHER DATA");

        vPage=findViewById(R.id.page_detail);
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(TodayFragment.newInstance("example1", "example2"));
        fragments.add(WeekFragment.newInstance("example3", "example4"));
        fragments.add(DataFragment.newInstance("example5", "example6"));
        System.out.println("fragment add done!");
        TabLayout tab_lay=(TabLayout) findViewById(R.id.tab_detail);
        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i);
            TabLayout.Tab tab = tab_lay.newTab();
            if (tab!=null){
                tab.setCustomView(getTabView(i));
            }
            tab_lay.addTab(tab);
            if (i==0){
                TextView tv_tab = tab.getCustomView().findViewById(R.id.tv_title);
                tv_tab.setTextColor(getResources().getColor(R.color.white));
                TextView tv_bk=tab.getCustomView().findViewById(R.id.tv_blank);
                tv_bk.setVisibility(View.VISIBLE);
            }
        }
        MyDetailAdapter adapter= new MyDetailAdapter(getSupportFragmentManager(),fragments, titles);
        vPage.setAdapter(adapter);
        vPage.setOffscreenPageLimit(3);
        vPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_lay));
        tab_lay.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vPage));

        tab_lay.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout ll=tab.getCustomView().findViewById(R.id.line_bg);
                ll.setBackgroundColor(getResources().getColor(R.color.dot_grey));
                TextView tv_tab = tab.getCustomView().findViewById(R.id.tv_title);
                tv_tab.setTextColor(getResources().getColor(R.color.white));
                TextView tv_bk=tab.getCustomView().findViewById(R.id.tv_blank);
                tv_bk.setVisibility(View.VISIBLE);
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        ll.setBackgroundColor(getResources().getColor(R.color.black));
                    }
                }, 150);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout ll=tab.getCustomView().findViewById(R.id.line_bg);
                ll.setBackgroundColor(getResources().getColor(R.color.black));
                TextView tv_tab = tab.getCustomView().findViewById(R.id.tv_title);
                tv_tab.setTextColor(getResources().getColor(R.color.dot_grey));
                TextView tv_bk=tab.getCustomView().findViewById(R.id.tv_blank);
                tv_bk.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        twitter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String info="Check Out "+loc+", USA’s Weather! It is "+tem+" °F! &hashtags=CSCI571WeatherSearch";
                String turl="https://twitter.com/intent/tweet?text="+info;
                Uri uri = Uri.parse(turl);
                Intent intent1 = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent1);
            }
        });

    }

    public View getTabView(int position) {

        View v = LayoutInflater.from(this).inflate(R.layout.tab_detail, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_title);
        tv.setText(titles.get(position));
        ImageView img=(ImageView) v.findViewById(R.id.tv_img) ;
        if (position==0)
            img.setImageResource(R.drawable.calendar_today);
        else if (position==1)
            img.setImageResource(R.drawable.trending_up);
        else
            img.setImageResource(R.drawable.thermometer_low);
//        tv.setCompoundDrawablesWithIntrinsicBounds(null,icons.get(position),null,null);
        return v;
    }


}