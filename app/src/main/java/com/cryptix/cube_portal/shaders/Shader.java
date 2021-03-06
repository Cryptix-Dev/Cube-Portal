package com.cryptix.cube_portal.shaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.opengl.GLES20;
import android.util.Log;

import com.cryptix.cube_portal.Main;

public abstract class Shader
{
    public enum ShaderType
    {
        VERTEX_SHADER,
        FRAGMENT_SHADER;
    }

    @SuppressWarnings("rawtypes")
    public static Map<String, Class> shaderNameToClassMap = new HashMap<String, Class>();
    public static Set<Shader> loadedShaders = new HashSet<Shader>();

    static
    {
        /** Add all Shader classes to use. */
        addToClassMapping("BasicVertexShader", BasicVertexShader.class);
        addToClassMapping("BasicFragmentShader", BasicFragmentShader.class);
    }

    private int handle = 0;

    protected int initializeShader(ShaderType shaderType)
    {
        int handle = 0;
        if (shaderType == ShaderType.VERTEX_SHADER)
            handle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        else if (shaderType == ShaderType.FRAGMENT_SHADER) handle = GLES20
            .glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        if (handle != 0)
            return handle;
        else
            throw new RuntimeException("Error Creating Shader.");
    }

    public void loadShader()
    {
        handle = initializeShader(getShaderType());
        if (handle != 0)
        {
            GLES20.glShaderSource(handle, this.getShaderCode(shaderLocation()));
            GLES20.glCompileShader(handle);

            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(
                handle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            if (compileStatus[0] == 0)
            {
                Log.e("CP: Shader ERROR", GLES20.glGetShaderInfoLog(handle));
                deleteShader();
            }
        }

        if (handle == 0) throw new RuntimeException("Error creating Shader.");

        loadedShaders.add(this);
    }

    public void deleteShader()
    {
        GLES20.glDeleteShader(handle);
        handle = 0;
        loadedShaders.remove(this);
    }

    public static void deleteAllShaders()
    {
        for (Shader s : loadedShaders)
            s.deleteShader();
    }

    public int getHandle()
    {
        return handle;
    }

    protected String getShaderCode(String ShaderFileLoc)
    {
        String s = null;
        try
        {
            s = getShaderCode(Main
                .getAppContext().getAssets().open(ShaderFileLoc));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return s;
    }

    protected String getShaderCode(File shaderFile)
    {
        String s = null;
        try
        {
            s = getShaderCode(new FileInputStream(shaderFile));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return s;
    }

    @SuppressWarnings("rawtypes")
    public static void addToClassMapping(String name, Class shaderClass)
    {
        if (shaderNameToClassMap.containsKey(name))
        {
            throw new IllegalArgumentException("Duplicate shader class: "
                + name);
        }
        else
        {
            shaderNameToClassMap.put(name, shaderClass);
        }
    }

    protected String getShaderCode(InputStream shaderStream)
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(shaderStream));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
                sb.append('\n');
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public abstract ShaderType getShaderType();

    public abstract String shaderLocation();

    public abstract String shaderName();
}
