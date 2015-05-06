package com.cryptix.cube_portal.shaders;

public class Shaders {
	
	public final BasicVertexShader basicVertexShader;
	public final BasicFragmentShader basicFragmentShader;
	
	public Shaders() {
		basicVertexShader = new BasicVertexShader();
		basicFragmentShader = new BasicFragmentShader();
	}
	
	public void loadShaders() {
		basicVertexShader.loadShader();
		basicFragmentShader.loadShader();
	}
	
	public void deleteShaders() {
		basicVertexShader.deleteShader();
		basicFragmentShader.deleteShader();
	}
}
