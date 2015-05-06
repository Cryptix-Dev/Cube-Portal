package com.cryptix.cube_portal.programs;

public class ProgramVariable {
	public enum VariableType {
		TYPE_ATTRIBUTE,
		TYPE_UNIFORM;
	}
	
	private int handle = 0;
	private String variableName;
	private VariableType type;
	
	public ProgramVariable(VariableType type, String variableName) {
		this.type = type;
		this.variableName = variableName;
	}
	
	public int getHandle() {
		return handle;
	}
	
	public void setHandle(int handle) {
		this.handle = handle;
	}
	
	public String getVariableName() {
		return variableName;
	}
	
	public VariableType getType() {
		return type;
	}
}
