package com.example.sonaai_11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class introActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;

    TabLayout tabIndicator;
    Button btnNext;
    Button btnSkip;
    int position=0;
    Button btnGetStarted;
    Animation btnAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //check if already opened

        if(restorePrefData())
        {
            Intent i=new Intent(getApplicationContext(),signIn.class);
            startActivity(i);
            finish();
        }



        setContentView(R.layout.activity_intro);


        //hide action bar

        getSupportActionBar().hide();
        //ini views
        btnNext=(Button)findViewById(R.id.btn_next);
        tabIndicator=findViewById(R.id.tab_indicator);
        btnGetStarted=findViewById(R.id.btn_get_started);
        btnSkip=findViewById(R.id.btn_skip);
        btnAnimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        //fill list scren
        final List<ScreenItem> mList=new ArrayList<>();
        String t1="HUMAN RESOURCES";
        String t2="HOTEL MANAGEMENT";
        String t3="EDUCATONAL COMM.";
        String d1="Automates your mundane tasks and enable you to focus on developing business with an increase in productivity";
        String d2="Get rids of the long check-in and check-out queues and time-consuming guest requests.";
        String d3="Connects parents, teachers and students making an extraordinary community of learners";
         mList.add(new ScreenItem(t1,d1,R.drawable.a1));
         mList.add(new ScreenItem(t2,d2,R.drawable.a2));
        mList.add(new ScreenItem(t3,d3,R.drawable.a3));

        //setup viewpage

        screenPager=findViewById(R.id.screen_viewpager);
        introViewPagerAdapter=new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter );


        //setup tablayout

        tabIndicator.setupWithViewPager(screenPager);

        //next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                position=screenPager.getCurrentItem();
                if(position<mList.size())
                {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if(position==mList.size()-1)
                {
                    lastscreen();
                }

            }
        });
        //tablayout add change listner
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==mList.size()-1)
                {
                    lastscreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        //get started button

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity=new Intent(getApplicationContext(),signIn.class);
                startActivity(mainActivity);

                //already opened once

                savePrefData();
                finish();

            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity=new Intent(getApplicationContext(),signIn.class);
                startActivity(mainActivity);

                //already opened once

                savePrefData();
                finish();
            }
        });

    }

    private boolean restorePrefData()
    {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore=pref.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefData()
    {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();


    }

    private void lastscreen()
    {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnSkip.setVisibility(View.INVISIBLE);

        //animation
        btnGetStarted.setAnimation(btnAnimation);
        btnAnimation.setDuration(500);

    }
}
