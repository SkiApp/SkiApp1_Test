package com.dhbw.studienarbeit.skiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;


public class StartScreen extends ActionBarActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        //Ladezeit
        Thread ladeZeit = new Thread() {
            public void run() {
                try {
                    sleep(2000);

                    Intent zuNavigationMenu = new Intent(StartScreen.this, NavigationMenu.class);

                    startActivity(zuNavigationMenu);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ladeZeit.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        return super.onCreateOptionsMenu(menu);
    }




}
