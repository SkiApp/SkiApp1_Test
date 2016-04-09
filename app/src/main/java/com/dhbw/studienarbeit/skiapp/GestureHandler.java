package com.dhbw.studienarbeit.skiapp;

import android.app.Activity;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

public abstract class GestureHandler extends Activity {

    public String swipe = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener
            = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            float sensitvity = 50;
            if((e1.getX() - e2.getX()) > sensitvity){
                swipe += "Swipe Left\n";
                onSwipeLeft();
            }else if((e2.getX() - e1.getX()) > sensitvity){
                swipe += "Swipe Right\n";
                onSwipeRight();
            }else{
                swipe += "\n";}
            if((e1.getY() - e2.getY()) > sensitvity){
                swipe += "Swipe Up\n";
                onSwipeUp();
                }else if((e2.getY() - e1.getY()) > sensitvity){
                swipe += "Swipe Down\n";
                onSwipeDown();
            }else{
                swipe += "\n";}
            return super.onFling(e1, e2, velocityX, velocityY);}
    };
    GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);
    public void onSwipeRight() {
        Intent naviDrawer = new Intent(GestureHandler.this, NavigationMenu.class);
        startActivity(naviDrawer);}
    public void onSwipeLeft() {
        Intent sosCall = new Intent(GestureHandler.this, SOSAnruf.class);
        startActivity(sosCall);}

    public void onSwipeUp() {
       return;
    }

    public void onSwipeDown() {
       return;
    }



}
