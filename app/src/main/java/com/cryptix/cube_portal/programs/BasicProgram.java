package com.cryptix.cube_portal.programs;

import com.cryptix.cube_portal.GLRenderer;
import com.cryptix.cube_portal.programs.ProgramVariable.VariableType;
import com.cryptix.cube_portal.shaders.Shader;

public class BasicProgram extends Program {
	
	public ProgramVariable ModelViewPositionMatrix;
	public ProgramVariable Color;
	public ProgramVariable Position;
	
	public BasicProgram() {
		super();
		
		Color = new ProgramVariable(VariableType.TYPE_ATTRIBUTE, "a_Color");
		Position = new ProgramVariable(VariableType.TYPE_ATTRIBUTE, "a_Position");
		ModelViewPositionMatrix = new ProgramVariable(VariableType.TYPE_UNIFORM, "u_MVPMatrix");
		
		addVariable(Color);
		addVariable(Position);
		addVariable(ModelViewPositionMatrix);
	}
	
	@Override
	public Shader getVertexShader() {
		return GLRenderer.shaders.basicVertexShader;
	}

	@Override
	public Shader getFragmentShader() {
		return GLRenderer.shaders.basicFragmentShader;
	}

}
