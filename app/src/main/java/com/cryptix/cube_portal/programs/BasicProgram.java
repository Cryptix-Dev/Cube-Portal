package com.cryptix.cube_portal.programs;

import com.cryptix.cube_portal.programs.ProgramVariable.VariableType;

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
	public String getVertexShader() {
		return "BasicVertexShader";
	}

	@Override
	public String getFragmentShader() {
		return "BasicFragmentShader";
	}

}
