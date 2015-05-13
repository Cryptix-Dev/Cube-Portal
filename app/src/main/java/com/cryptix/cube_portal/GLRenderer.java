package com.cryptix.cube_portal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.cryptix.cube_portal.programs.BasicProgram;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class GLRenderer implements GLSurfaceView.Renderer {

    // TODO: Store programs globally.
    private BasicProgram basicProgram;

    private FloatBuffer  triangle1Vertices;
    float[]              triangle1VerticesData = { -0.5f, -0.25f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,

                                               0.5f, -0.25f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,

                                               0.0f, 0.559016994f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f };

    // TODO: Create camera class.
    private float[]      viewMatrix            = new float[16];
    private float[]      projectionMatrix      = new float[16];
    private float[]      modelMatrix           = new float[16];

    private float[]      modelViewProjMatrix   = new float[16];
    private final int    strideBytes           = 7 * 4;
    private final int    positionOffset        = 0;
    private final int    positionDataSize      = 3;
    private final int    colorOffset           = 3;
    private final int    colorDataSize         = 4;

    private GameTime     drawTime;
    private float        interpolation;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Initialize Method
        // Set the ClearColor for rendering
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // TODO: Cleaner program creation.
        basicProgram = new BasicProgram();
        basicProgram.createProgram();

        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 1.5f;

        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        Matrix.setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        triangle1Vertices = ByteBuffer.allocateDirect(triangle1VerticesData.length * 4).order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        triangle1Vertices.put(triangle1VerticesData).position(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Post Initialize / phone orientation change.
        GLES20.glViewport(0, 0, width, height);

        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(projectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public void update(GameTime gameTime) {
        // TODO: Add frame counter.
        // TODO: Migrate triangle movement
    }

    public void prepareDraw(GameTime gameTime, float interpolation) {
        this.interpolation = interpolation;
        drawTime = gameTime;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Draw Method
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUseProgram(basicProgram.getHandle());

        long time = drawTime.TotalGameTime().getMilliseconds() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);

        triangle1Vertices.position(positionOffset);
        GLES20.glVertexAttribPointer(basicProgram.Position.getHandle(),
                                     positionDataSize,
                                     GLES20.GL_FLOAT,
                                     false,
                                     strideBytes,
                                     triangle1Vertices);

        GLES20.glEnableVertexAttribArray(basicProgram.Position.getHandle());

        triangle1Vertices.position(colorOffset);
        GLES20.glVertexAttribPointer(basicProgram.Color.getHandle(),
                                     colorDataSize,
                                     GLES20.GL_FLOAT,
                                     false,
                                     strideBytes,
                                     triangle1Vertices);

        GLES20.glEnableVertexAttribArray(basicProgram.Color.getHandle());

        Matrix.multiplyMM(modelViewProjMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(modelViewProjMatrix, 0, projectionMatrix, 0, modelViewProjMatrix, 0);

        GLES20.glUniformMatrix4fv(basicProgram.ModelViewPositionMatrix.getHandle(), 1, false, modelViewProjMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
}
