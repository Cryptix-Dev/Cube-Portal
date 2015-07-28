package com.cryptix.cube_portal.programs;

import com.cryptix.cube_portal.programs.ProgramVariable.VariableType;

public class BasicProgram extends Program
{

    public ProgramVariable ModelViewPositionMatrix;
    public ProgramVariable Color;
    public ProgramVariable Position;

    public BasicProgram()
    {
        Color = addVariable(VariableType.TYPE_ATTRIBUTE, "a_Color");
        Position = addVariable(VariableType.TYPE_ATTRIBUTE, "a_Position");
        ModelViewPositionMatrix = addVariable(
            VariableType.TYPE_UNIFORM, "u_MVPMatrix");
    }

    @Override
    public String getVertexShader()
    {
        return "BasicVertexShader";
    }

    @Override
    public String getFragmentShader()
    {
        return "BasicFragmentShader";
    }
}
