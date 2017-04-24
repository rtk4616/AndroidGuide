package com.ssyijiu.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.ssyijiu.criminalintent.app.BaseActivity;
import com.ssyijiu.criminalintent.bean.Crime;
import com.ssyijiu.criminalintent.bean.CrimeLab;
import java.util.List;
import java.util.UUID;

/**
 * Created by ssyijiu on 2017/4/23.
 * Github : ssyijiu
 * Email  : lxmyijiu@163.com
 */

public class CrimePagerActivity extends BaseActivity {

    @BindView(R.id.crime_pager_root) ViewPager viewPagerRoot;

    private List<Crime> crimeList;

    private static final String EXTRA_CRIME_ID = "extra_crime_id";
    private static final String EXTRA_CRIME_POSITION = "extra_crime_position";
    private UUID crimeId;
    private int currentPosition;


    @Override protected void parseIntent(Intent intent) {
        crimeId = (UUID) intent.getSerializableExtra(EXTRA_CRIME_ID);
        currentPosition = intent.getIntExtra(EXTRA_CRIME_POSITION, 0);
    }


    @Override protected int getContentView() {
        return R.layout.activity_crime_pager;
    }

    @Override protected void initViewAndData(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        crimeList = CrimeLab.get().getCrimeList();

        viewPagerRoot.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override public Fragment getItem(int position) {

                Crime crime = crimeList.get(position);
                return CrimeFragment.newInstance(crime.id,position);
            }


            @Override public int getCount() {
                return crimeList.size();
            }
        });
        viewPagerRoot.setCurrentItem(currentPosition);
    }

    public static Intent newIntent(Context context, UUID crimeId, int position) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        intent.putExtra(EXTRA_CRIME_POSITION, position);
        return intent;
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override public int getCount() {
            return 0;
        }


        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }


        @Override public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}