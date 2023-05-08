package com.example.bangpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bangpt.banner.Banner1Fragment;
import com.google.android.material.navigation.NavigationBarView;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;
    private CircleIndicator3 mIndicator;
    HomeFragment homeFragment;
    CalendarFragment calendarFragment;
    CommunityFragment communityFragment;
    MypageFragment mypageFragment;
    ChallengeFragment challengeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        communityFragment = new CommunityFragment();
        mypageFragment = new MypageFragment();
        challengeFragment = new ChallengeFragment();


//ViewPager2
        mPager = findViewById(R.id.viewpager);
        //Adapter
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(1002); //시작 지점
        mPager.setOffscreenPageLimit(3); //최대 이미지 수

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                @Override
                                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                                                    if (positionOffsetPixels == 0) {
                                                        mPager.setCurrentItem(position);
                                                    }
                                                }

                                                @Override
                                                public void onPageSelected(int position) {
                                                    super.onPageSelected(position);
                                                    mIndicator.animatePageSelected(position % num_page);
                                                }
                                            });

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){

                switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        return true;
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, mypageFragment).commit();
                        return true;
                    case R.id.calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, calendarFragment).commit();
                        return true;
                    case R.id.challenge:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, challengeFragment).commit();
                        return true;
                    case R.id.community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, communityFragment).commit();
                        return true;
                    default :
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        //return true;
                }
                return false;
            }

        });
    }
}