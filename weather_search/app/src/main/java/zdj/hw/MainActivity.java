package zdj.hw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    JSONArray temp2;
    ViewPager vPage1;
    ListView listView;
    String lat;
    String lon;
    String[] full_state={"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
            "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
            "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
            "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
            "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
            "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
            "West Virginia", "Wisconsin", "Wyoming"};
    String[] part_state={"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS",
            "KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK",
            "OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
    String[] auto={"","","","",""};
    String[] country={"","","","",""};
    String[] example={" ", " ", " ", " ", " "};
    ArrayAdapter<String> arrayAdapter;
    String locs;
    Handler mHandler;
    LinearLayout progress;
    public SharedPreferences sharedPreferences;
    int local_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("main activity oncreate");
        setTheme(R.style.Theme_HW);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        progress=(LinearLayout) findViewById(R.id.progress_layout);
        RelativeLayout mainContent=(RelativeLayout) findViewById(R.id.main_content);
        progress.setVisibility(View.VISIBLE);

        sharedPreferences = getSharedPreferences("my_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        local_num=sharedPreferences.getAll().size();
//        flag=sharedPreferences.getString(loc, "");

        listView=findViewById(R.id.listView_search);
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, example);
        listView.setAdapter(arrayAdapter);

//        Toolbar myToolbar=(Toolbar) findViewById(R.id.my_toolbar);
//        Button btn_search=(Button) myToolbar.findViewById(R.id.btnSearch);
//        btn_search.setBackgroundResource(R.drawable.ic_search_24dp);
//        setSupportActionBar(myToolbar);

        vPage1=findViewById(R.id.page1);
        ArrayList<Fragment> fragments=new ArrayList<>();
        ArrayList<String> key_list=new ArrayList<>();
        fragments.add(FragmentHome.newInstance("example1", "example2"));
        Set<String> all=sharedPreferences.getAll().keySet();
        for (String key: all) {
            key_list.add(key);
            String json_trans=sharedPreferences.getString(key, "");
            fragments.add(FragmentTwo.newInstance(key, json_trans));
        }
        System.out.println(fragments.size());
        MyFragmentAdapter adapter= new MyFragmentAdapter(getSupportFragmentManager(),fragments);
        vPage1.setAdapter(adapter);
        vPage1.setOffscreenPageLimit(10);
//        vPage1.setSaveFromParentEnabled(false);

        TabLayout tab_lay1=(TabLayout) findViewById(R.id.tab1);
        tab_lay1.setupWithViewPager(vPage1,true);
        FloatingActionButton fab = findViewById(R.id.fab_two);

//        vPage1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_lay1));
        tab_lay1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("-------tab.getPosition"+tab.getPosition());
                if (tab.getPosition()==0) {
                    System.out.println("-------00000");
                    fab.setVisibility(View.INVISIBLE);
                }
                else {
                    System.out.println("-----no--00000");
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int pos=tab.getPosition();
                            String str=key_list.get(pos-1)+" was removed from favorites";
                            Toast.makeText(view.getContext(), str, Toast.LENGTH_LONG).show();
                            System.out.println("-----delete"+key_list.get(pos-1));
                            editor.remove(key_list.get(pos-1));
                            editor.commit();
                            System.out.println("------key_list"+key_list);
                            System.out.println("------key_list"+key_list.get(pos-1));
                            key_list.remove(pos-1);
                            System.out.println("------key_list2"+key_list);
                            System.out.println("------remove tab.getPosition"+pos);
                            tab_lay1.removeTabAt(pos);
                            adapter.removeTabPage(pos);
                            System.out.println("------adapter.getCount"+adapter.getCount());
//                            fragments.remove(pos);
//                            adapter.notifyDataSetChanged();
                            System.out.println("------fragment"+fragments.size());
                            System.out.println("------adapter.getCount"+adapter.getCount());
//                            tab_lay1.selectTab(tab_lay1.getTabAt(pos-1));
                        }
                });
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
        }, 6500);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String queryString=(String)parent.getItemAtPosition(position);
                searchView.setQuery(queryString, false);
//                FloatingActionButton fab = findViewById(R.id.fab_two);
//                fab.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.INVISIBLE);
                locs=queryString;
                System.out.println(locs);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query == null || query.length() == 0) {
                    System.out.println("nooooooo!");
                }
                else {
                    String temp=query.replace(" ", "+").replace(",", "");
                    System.out.println(temp);
                    String map_key="AIzaSyAp3S2F2HGiXRNsaLzYmDZjms5j3LiLsq0";
                    String mapURL="https://maps.googleapis.com/maps/api/geocode/json?address="+temp+"&key="+map_key;
                    System.out.println(mapURL);
//                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.GET, mapURL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject results=response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                                lat=results.getString("lat");
                                System.out.println(lat);
                                lon=results.getString("lng");
                                System.out.println(lon);

                                String url = "https://hw9try2.ue.r.appspot.com/back?lat="+lat+"&lon="+lon;
                                System.out.println(url);
                                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            temp2 =response.getJSONObject("data").getJSONArray("timelines");
                                            Intent intent=new Intent(MainActivity.this, ResultActivity.class);
                                            Bundle bundle =new Bundle();
                                            bundle.putString("data",temp2.toString());
                                            bundle.putString("loc", locs);
                                            intent.putExtras(bundle);
                                            startActivity(intent);

                                        } catch (JSONException e) {
                                            System.out.println("getDetail wrong!");
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO: Handle error
                                        System.out.println("174 error!");
                                    }
                                });
                                requestQueue.add(jsonObjectRequest1);

                            } catch (JSONException e) {
                                System.out.println("auto google search wrong!");
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            System.out.println("143 error!");
                        }
                    });
                    requestQueue.add(jsonObjectRequest);

                }

                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("--change!!");
                if(newText == null || newText.length() == 0) {
                    listView.setVisibility(View.INVISIBLE);
                }
                else {
                    String http_auto="https://hw9try2.ue.r.appspot.com/auto?loc="+newText;
//                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.GET, http_auto, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray predictions=response.getJSONArray("predictions");
                                for(int i=0; i<predictions.length(); i++) {
                                    JSONArray terms=predictions.getJSONObject(i).getJSONArray("terms");
                                    String locality=terms.getJSONObject(0).getString("value");
                                    String state=terms.getJSONObject(1).getString("value");
                                    String temp_state="";
                                    for (int j=0; j<part_state.length; j++) {
                                        if (part_state[j].equals(state))
                                            auto[i]=locality+", "+full_state[j];
                                    }
                                }

                                arrayAdapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, auto);
                                listView.setAdapter(arrayAdapter);
                            } catch (JSONException e) {
                                System.out.println("auto search wrong!");
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

                    listView.setVisibility(View.VISIBLE);
                }

//                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

//    public JSONArray getDetail(String lat, String lon, RequestQueue requestQueue) {
////        https://hw9try2.ue.r.appspot.com/back?lat=34&lon=-118
//
//
//        return temp2;
//
//    }


    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("-------main activity onstart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("-------main activity onresume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("--------main activity onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("--------main activity onstop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("------main activity onrestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}