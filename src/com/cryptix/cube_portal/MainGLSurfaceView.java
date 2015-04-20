package com.cryptix.cube_portal;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MainGLSurfaceView extends GLSurfaceView {

	private final GLRenderer glRenderer;
	
	public MainGLSurfaceView(Context context) {
		super(context);
		
		setEGLContextClientVersion(2);
		
		glRenderer = new GLRenderer();
		
		setRenderer(glRenderer);
		
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

}
