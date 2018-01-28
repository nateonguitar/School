package com.natebrooks.doublebreaker;

import com.natebrooks.framework.Game;
import com.natebrooks.framework.Graphics;
import com.natebrooks.framework.Screen;
import com.natebrooks.framework.Graphics.ImageFormat;

public class SplashLoadingScreen extends Screen{
	
	public SplashLoadingScreen(Game game){
		super(game);
	}
	
	@Override
	public void update(float deltaTime){
		Graphics g = game.getGraphics();
		Assets.logo = g.newImage("logo.jpg", ImageFormat.RGB565);
		
		game.setScreen(new LoadingScreen(game));
	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		
	}
	
}
