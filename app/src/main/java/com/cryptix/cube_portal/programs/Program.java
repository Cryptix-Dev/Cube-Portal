package com.cryptix.cube_portal.programs;

import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES20;

import com.cryptix.cube_portal.programs.ProgramVariable.VariableType;
import com.cryptix.cube_portal.shaders.Shader;

public abstract class Program
{

    private int handle = 0;
    private List<ProgramVariable> variables = new ArrayList<ProgramVariable>();
    private Shader vertexShader = null;
    private Shader fragmentShader = null;

    public int getHandle()
    {
        return handle;
    }

    public void addVariable(ProgramVariable variable)
    {
        variables.add(variable);
    }

    public ProgramVariable addVariable(VariableType type, String name)
    {
        ProgramVariable temp = new ProgramVariable(type, name);
        variables.add(temp);
        return temp;
    }

    public void createProgram()
    {
        handle = GLES20.glCreateProgram();

        if (handle != 0)
        {

            for (Shader s : Shader.loadedShaders)
            {
                if (s.shaderName().equalsIgnoreCase(getVertexShader())) vertexShader = s;
                if (s.shaderName().equalsIgnoreCase(getFragmentShader())) fragmentShader = s;
            }

            if (vertexShader == null)
            {
                try
                {
                    vertexShader = (Shader) Shader.shaderNameToClassMap.get(
                        getVertexShader()).newInstance();
                }
                catch (Exception e)
                {
                    throw new RuntimeException("Failed to find VertexShader: "
                        + getVertexShader());
                }
                vertexShader.loadShader();
            }
            if (fragmentShader == null)
            {
                try
                {
                    fragmentShader = (Shader) Shader.shaderNameToClassMap.get(
                        getFragmentShader()).newInstance();
                }
                catch (Exception e)
                {
                    throw new RuntimeException(
                        "Failed to find FragmentShader: " + getFragmentShader());
                }
                fragmentShader.loadShader();
            }

            if (vertexShader.getHandle() != 0) GLES20.glAttachShader(
                handle, vertexShader.getHandle());
            if (fragmentShader.getHandle() != 0) GLES20.glAttachShader(
                handle, fragmentShader.getHandle());

            int i = 0;
            for (ProgramVariable variable : variables)
            {
                if (variable.getType() == VariableType.TYPE_ATTRIBUTE)
                {
                    GLES20.glBindAttribLocation(
                        getHandle(), i, variable.getVariableName());
                    i++;
                }
            }

            GLES20.glLinkProgram(handle);

            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(handle, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] == 0)
            {
                deleteProgam();
            }

            for (ProgramVariable variable : variables)
            {
                if (variable.getType() == VariableType.TYPE_ATTRIBUTE)
                    variable.setHandle(GLES20.glGetAttribLocation(
                        getHandle(), variable.getVariableName()));
                else if (variable.getType() == VariableType.TYPE_UNIFORM)
                    variable.setHandle(GLES20.glGetUniformLocation(
                        getHandle(), variable.getVariableName()));
                else
                    throw new RuntimeException(
                        "Unkown type for ProgramVariable.");
            }
        }

        if (handle == 0) throw new RuntimeException("Error Creating Program");
    }

    public void deleteProgam()
    {
        GLES20.glDeleteProgram(handle);
        handle = 0;
    }

    public abstract String getVertexShader();

    public abstract String getFragmentShader();
}
