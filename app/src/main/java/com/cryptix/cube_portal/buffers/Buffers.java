package com.cryptix.cube_portal.buffers;

import java.util.HashSet;
import java.util.Set;

import android.opengl.GLES20;

public class Buffers
{
    public static Set<BufferObject> bufferObjects = new HashSet<BufferObject>();
    static
    {
        addBufferObject(new Triangle());
    }
    private int[] buffers = new int[2];

    public void createBuffers()
    {
        GLES20.glGenBuffers(2, buffers, 0);
        int vertexSize = 0;
        int indexSize = 0;
        for (BufferObject object : bufferObjects)
        {
            if (object.vertexArrayData() != null) vertexSize += object
                .vertexArrayData().length;
            if (object.indexArrayData() != null) indexSize += object
                .indexArrayData().length;
        }
        // Vertex Buffer
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        GLES20.glBufferData(
            GLES20.GL_ARRAY_BUFFER, vertexSize, null, GLES20.GL_STATIC_DRAW);
        // Index Buffer
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, buffers[1]);
        GLES20.glBufferData(
            GLES20.GL_ELEMENT_ARRAY_BUFFER, indexSize, null,
            GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void addObjectsToBuffer(BufferObject... bufferObjects)
    {
    }

    public void clearObjectsFromBuffer(BufferObject... bufferObjects)
    {
    }

    private static void addBufferObject(BufferObject bufferObject)
    {
        for (BufferObject bo : bufferObjects)
        {
            if (bo.objectName().equalsIgnoreCase(bufferObject.objectName())) { return; }
        }
        bufferObjects.add(bufferObject);
    }

    public int getVertexBuffer()
    {
        return buffers[0];
    }

    public int getIndexBuffer()
    {
        return buffers[1];
    }
}
