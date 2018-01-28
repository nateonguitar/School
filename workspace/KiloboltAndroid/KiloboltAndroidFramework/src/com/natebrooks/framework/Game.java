package com.natebrooks.framework;

public interface Game {
	public Audio getAudio();
	public Input getInput();
	public FileIO getFileOI();
	public Graphics getGraphics();
	public void setScreen(Screen screen);
	public Screen getCurrentScreen();
	public Screen getInitScreen();
}
