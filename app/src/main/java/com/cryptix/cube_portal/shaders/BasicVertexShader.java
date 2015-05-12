package com.cryptix.cube_portal.shaders;

public class BasicVertexShader extends Shader {
	
	@Override
	public ShaderType getShaderType() {
		return ShaderType.VERTEX_SHADER;
	}

	@Override
	public String shaderLocation() {
		return "shaders/BasicShader.vs";
	}

	@Override
	public String shaderName() {
		return "BasicVertexShader";
	}
}
