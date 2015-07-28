package com.cryptix.cube_portal.buffers;

public class Triangle extends BufferObject
{
    private int positionOffset = 0;
    private int colorOffset = 3;

    public int getPositionOffset()
    {
        return positionOffset + baseOffset;
    }

    public int getColorOffset()
    {
        return colorOffset + baseOffset;
    }

    @Override
    public float[] vertexArrayData()
    {
        return new float[]
        {
            // Vertex 1
            -0.5f, -0.25f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            // Vertex 2
            0.0f, -0.25f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
            // Vertex 3
            0.0f, 0.559016994f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f
        };
    }

    @Override
    public short[] indexArrayData()
    {
        return new short[]
        {
            1, 2, 3
        };
    }

    @Override
    public String objectName()
    {
        return "Triangle";
    }

    @Override
    public int stride()
    {
        return 7;
    }
}
