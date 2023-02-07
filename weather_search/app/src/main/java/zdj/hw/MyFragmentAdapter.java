package zdj.hw;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList=new ArrayList<>();

    public MyFragmentAdapter(@NonNull FragmentManager fm,List<Fragment> fragmentList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        System.out.println("----adpater constructor");
        this.fragmentList = fragmentList;
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }

    public void removeTabPage(int position) {
        System.out.println("------adapter remove"+position);
        if (!fragmentList.isEmpty() && position<fragmentList.size()) {
            System.out.println(fragmentList.get(position));
            fragmentList.remove(position);
            notifyDataSetChanged();
        }
    }
}

