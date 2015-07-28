package com.cryptix.cube_portal.buffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public abstract class BufferObject
{
    protected int baseOffset = 0;

    public void setBaseOffset(int baseOffset)
    {
        this.baseOffset = baseOffset;
    }

    public FloatBuffer createVertexBuffer()
    {
        FloatBuffer buffer = ByteBuffer
            .allocateDirect(vertexArrayData().length * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(vertexArrayData()).position(0);
        return buffer;
    }

    public ShortBuffer createIndexBuffer()
    {
        ShortBuffer buffer = ByteBuffer
            .allocateDirect(indexArrayData().length * 2)
            .order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(indexArrayData()).position(0);
        return buffer;
    }

    public abstract float[] vertexArrayData();

    public abstract short[] indexArrayData();

    public abstract String objectName();

    public abstract int stride();
}
