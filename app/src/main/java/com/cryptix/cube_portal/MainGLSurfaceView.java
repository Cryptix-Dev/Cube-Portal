package com.cryptix.cube_portal;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;

public class MainGLSurfaceView extends GLSurfaceView {

	private final GLRenderer glRenderer;
	
	public MainGLSurfaceView(Context context) {
		super(context);
		glRenderer = new GLRenderer();
		
		final ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
		final boolean supportsES2 = configurationInfo.reqGlEsVersion >= 0x20000;
		
		if (supportsES2) {
			setEGLContextClientVersion(2);
			
			setRenderer(glRenderer);
		}
	}
}
