package com.cryptix.cube_portal.shaders;

public class BasicFragmentShader extends Shader {

	@Override
	public ShaderType getShaderType() {
		return ShaderType.FRAGMENT_SHADER;
	}
	
	@Override
	public String shaderLocation() {
		return "shaders/BasicShader.ps";
	}
}
