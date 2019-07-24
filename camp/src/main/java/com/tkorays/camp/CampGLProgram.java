package com.tkorays.camp;

import android.opengl.GLES20;
import android.util.Log;

public class CampGLProgram {
    private static String TAG = "CampGLProgram";
    private int programId;
    private int vertexShader;
    private int fragmentShader;

    /**
     * Create new GL program.
     * @param vs vertex shader string.
     * @param fs fragment shader string.
     * @return CampGLProgram instance.
     */
    public static CampGLProgram create(String vs, String fs) {
        return new CampGLProgram(vs, fs);
    }

    /**
     * destroy resources.
     */
    public void destroy() {
        if(vertexShader != 0) {
            GLES20.glDeleteShader(vertexShader);
            vertexShader = 0;
        }

        if(fragmentShader != 0) {
            GLES20.glDeleteShader(fragmentShader);
            fragmentShader = 0;
        }
        if(programId > 0) {
            GLES20.glDeleteProgram(programId);
            programId = 0;
        }
    }

    /**
     * GLES20.glGetAttribLocation
     * @param attrib attribution name.
     * @return attribution id.
     */
    public int getAttribLocation(String attrib) {
        return GLES20.glGetAttribLocation(programId, attrib);
    }

    /**
     * GLES20.glGetUniformLocation
     * @param uniform uniform name.
     * @return uniform id.
     */
    public int getUniformLocation(String uniform) {
        return GLES20.glGetUniformLocation(programId, uniform);
    }

    public void use() {
        GLES20.glUseProgram(programId);
    }

    /**
     * Constructor of RaysGLProgram
     * @param vs vertex shader string.
     * @param fs fragment shader string.
     */
    private CampGLProgram(String vs, String fs) {
        vertexShader = loadShader(vs, GLES20.GL_VERTEX_SHADER);
        if(vertexShader == 0) {
            return;
        }

        fragmentShader = loadShader(fs, GLES20.GL_FRAGMENT_SHADER);
        if(fragmentShader == 0) {
            return ;
        }

        programId = GLES20.glCreateProgram();
        GLES20.glAttachShader(programId, vertexShader);
        GLES20.glAttachShader(programId, fragmentShader);
    }

    /**
     * Load shader from source and type.
     * @param shader shader string.
     * @param type shader type.
     * @return shader id.
     */
    private int loadShader(String shader, int type) {
        int[] id = new int[1];
        int i = GLES20.glCreateShader(type);
        GLES20.glShaderSource(i, shader);
        GLES20.glCompileShader(i);
        GLES20.glGetShaderiv(i, GLES20.GL_COMPILE_STATUS, id, 0);
        if(id[0] == 0) {
            Log.e(TAG, "load shader failed: " + GLES20.glGetShaderInfoLog(i));
            return 0;
        }
        return id[0];
    }

    /**
     * link program.
     * @return link result.
     */
    private boolean link() {
        int[] i = new int[1];
        GLES20.glLinkProgram(programId);

        GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, i, 0);
        if (i[0] <= 0) {
            Log.d(TAG, "Linking program Failed");
            return false;
        }
        if(vertexShader != 0) {
            GLES20.glDeleteShader(vertexShader);
            vertexShader = 0;
        }

        if(fragmentShader != 0) {
            GLES20.glDeleteShader(fragmentShader);
            fragmentShader = 0;
        }
        return true;
    }
}
