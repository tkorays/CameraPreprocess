package com.tkorays.camp;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class CampFilter {
    public static final String CAMP_VERTEX_SHADER = "attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;void main(){gl_Position = position;textureCoordinate = inputTextureCoordinate.xy;}";
    public static final String CAMP_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;uniform sampler2D inputImageTexture;void main(){gl_FragColor = texture2D(inputImageTexture, textureCoordinate);}";
    public static final float[] CAMP_VERTICES = { -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    public static final float CAMP_TEXTURE_NO_ROTATION[] = { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    public static final float CAMP_TEXTURE_ROTATED_90[] = {1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
    public static final float CAMP_TEXTURE_ROTATED_180[] = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    public static final float CAMP_TEXTURE_ROTATED_270[] = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};

    private final FloatBuffer fbVertices = createFloatBuffer(CAMP_VERTICES);
    private final FloatBuffer fbCoord = createFloatBuffer(CAMP_TEXTURE_NO_ROTATION);

    protected CampGLProgram program;
    protected int locOfAttrPosition;
    protected int locOfAttrTexCoord;
    protected int locOfUnifInputTex;

    public CampFilter() {
        this(CAMP_VERTEX_SHADER, CAMP_FRAGMENT_SHADER);
    }

    public CampFilter(String vs, String fs) {
        program = CampGLProgram.create(vs, fs);

        locOfAttrPosition = program.getAttribLocation("position");
        locOfAttrTexCoord = program.getAttribLocation("inputTextureCoordinate");
        locOfUnifInputTex = program.getUniformLocation("inputImageTexture");

        program.use();

        GLES20.glEnableVertexAttribArray(locOfAttrPosition);
        GLES20.glEnableVertexAttribArray(locOfAttrTexCoord);
    }

    public void renderToTexture() {
        program.use();

    }

    private FloatBuffer createFloatBuffer(final float[] data) {
        FloatBuffer fb = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        fb.put(data).position(0);
        return fb;
    }
}
