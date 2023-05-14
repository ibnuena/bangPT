package com.example.bangpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bangpt.banner.Banner1Fragment;
import com.google.android.material.navigation.NavigationBarView;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    String userID;
    String userPass;
    HomeFragment homeFragment;
    CalendarFragment calendarFragment;
    CommunityFragment communityFragment;
    MypageFragment mypageFragment;
    ChallengeFragment challengeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userPass = intent.getStringExtra("userPass");

        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        communityFragment = new CommunityFragment();
        mypageFragment = new MypageFragment();
        challengeFragment = new ChallengeFragment();



        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){

                switch(item.getItemId()){
                    case R.id.home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();

                        return true;
                    }
                    case R.id.mypage: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, mypageFragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("userID", userID);
                        bundle.putString("userPass", userPass);
                        mypageFragment.setArguments(bundle);
                        return true;
                    }
                    case R.id.calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, calendarFragment).commit();
                        return true;
                    case R.id.challenge:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, challengeFragment).commit();
                        return true;
                    case R.id.community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, communityFragment).commit();
                        return true;
                    default :{
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("userID", userID);
                        homeFragment.setArguments(bundle1);
                       // return true;
                    }
                }
                return false;
            }

        });

    }
}