package com.cryptix.cube_portal.programs;

public class Programs {
	
	public final BasicProgram basicProgram;
	
	public Programs() {
		basicProgram = new BasicProgram();
	}
	
	public void createPrograms() {
		basicProgram.createProgram();
	}
	
	public void deletePrograms() {
		basicProgram.deleteProgam();
	}
}
