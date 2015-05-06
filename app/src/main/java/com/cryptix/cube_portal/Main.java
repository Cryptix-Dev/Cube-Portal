package com.cryptix.cube_portal;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Main extends Activity {
	
	private GLSurfaceView mainGLSurfaceView;
	private static Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mainGLSurfaceView = new MainGLSurfaceView(this);
		Main.context = getApplicationContext();
		
		setContentView(mainGLSurfaceView);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mainGLSurfaceView.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mainGLSurfaceView.onPause();
	}
	
	public static Context getAppContext() {
		return Main.context;
	}
}
