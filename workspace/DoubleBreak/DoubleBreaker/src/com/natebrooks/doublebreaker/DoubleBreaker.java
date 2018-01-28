package com.natebrooks.doublebreaker;

import com.natebrooks.framework.Screen;
import com.natebrooks.framework.implementation.AndroidGame;

public class DoubleBreaker extends AndroidGame {
    @Override
    public Screen getInitScreen() {
        return new SplashLoadingScreen(this); 
    }
    
    @Override
    public void onBackPressed() {
    	getCurrentScreen().backButton();
    }
}