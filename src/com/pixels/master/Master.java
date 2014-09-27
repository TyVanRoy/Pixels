package com.pixels.master;

public class Master {
	private Game game;

	private Master(){
		game = new Game();
	}
	
	private void launch(){
		game.launch();
	}
	
	public static void main(String[] args){
		new Master().launch();
	}
}
