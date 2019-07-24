package com.tkorays.camp;

import android.opengl.GLES20;

import java.nio.ByteBuffer;

public class CampFrameBuffer {
    private int bufferId;
    private int textureId;
    private int texWidth;
    private int texHeight;

    CampFrameBuffer() {
        createFrameBuffer();
    }

    public int getFrameBuffer() { return bufferId; }
    public int getTexture() { return textureId; }

    public void inputRGBATexture(ByteBuffer data) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glTexSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, texWidth, texHeight, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, data);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    private void createFrameBuffer() {
        int[] i = new int[1];
        GLES20.glGenFramebuffers(1, i, 0);
        GLES20.glBindFramebuffer(36160, i[0]);

        // TODO: attach to which index?
        // GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        int[] j = new int[1];
        GLES20.glGenTextures(1, j, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, j[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, texWidth, texHeight, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        textureId = j[0];

        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, textureId, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        bufferId = i[0];
    }
}
