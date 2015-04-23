package com.cryptix.cube_portal;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private GLSurfaceView mainGLSurfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mainGLSurfaceView = new MainGLSurfaceView(this);
		setContentView(mainGLSurfaceView);
	}
}
