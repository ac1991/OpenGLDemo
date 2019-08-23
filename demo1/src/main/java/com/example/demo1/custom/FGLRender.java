package com.example.demo1.custom;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.demo1.utils.Logger;
import com.example.demo1.utils.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static javax.microedition.khronos.opengles.GL10.*;
import static android.opengl.GLES20.*;

public class FGLRender implements GLSurfaceView.Renderer {
    //表示是二维坐标的顶点，一个顶点只有两个值
    private static final int POSITION_COMPONENT_COUNT = 2;

    //每个点由横坐标和纵坐标组成，因此，我们每个点为1行
    private float[] tableVertices = {
            0f, 0f, 1f, 1f, 1f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

            0.5f, 0f, 1f, 0f, 0f,
            -0.5f, 0f, 1f, 0f, 0f,

            0f, 0.3f, 0f, 0f, 1f,
            0f, -0.3f, 1f, 0f, 0f

    };

    private String vertexShader = "attribute vec4 a_Position;\n" +
            "attribute vec4 a_Color;\n" +
            "\n" +
            "varying vec4 v_Color;\n" +
            "\n" +
            "void main(){\n" +
            "    v_Color = a_Color;\n" +
            "\n" +
            "    gl_Position = a_Position;\n" +
            "    gl_PointSize = 10.0;\n" +
            "}";

    private String fragmentShader = "precision mediump float;\n" +
            "\n" +
            "uniform vec4 u_Color;\n" +
            "void main(){\n" +
            "    gl_FragColor = u_Color;\n" +
            "}";

    private FloatBuffer vertexData;

    private int programId;
    private int vertexId;
    private int fragmentId;


    private static final String U_COLOR = "u_Color";
    //片段着色器的属性位置
    private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    //顶点着色器属性位置
    private int aPositionLocation;

    public FGLRender() {
        init();
    }

    private void init() {

        vertexData = ByteBuffer.allocateDirect(tableVertices.length * 4)//设置数组的字节数作为分配内存的长度
                .order(ByteOrder.nativeOrder())//按照本地字节序组织内容
                .asFloatBuffer();
        vertexData.put(tableVertices);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Logger.d_dev(this.getClass().getSimpleName() + "  onSurfaceCreated");

        //设置清空屏幕用的颜色
        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        //编译顶点着色器和片段着色器
        vertexId = ShaderHelper.compileVertexShader(vertexShader);
        fragmentId = ShaderHelper.compileFragmentShader(fragmentShader);

        //链接opengl程序
        programId = ShaderHelper.linkProgram(vertexId, fragmentId);

        //获取着色器属性位置
        uColorLocation = glGetUniformLocation(programId, U_COLOR);
        aPositionLocation = glGetAttribLocation(programId, A_POSITION);

        //让缓冲区指针位置回到起始点
        vertexData.position(0);
        //关联属性与顶点数据的数组
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL10.GL_FLOAT, false, 0, vertexData);
        //使顶点属性能够使用到顶点数据数组的
        glEnableVertexAttribArray(aPositionLocation);


    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Logger.d_dev(this.getClass().getSimpleName() + "  onSurfaceChanged");
        //设置视图尺寸
        glViewport(0, 0, height, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        Logger.d_dev(this.getClass().getSimpleName() + "  onDrawFrame");

        glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //将程序加入到OpenGL ES 2.0环境
        glUseProgram(programId);
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GLES20.GL_TRIANGLE_FAN , 0, 6);

        glUniform4f(uColorLocation, 1f, 0f, 0f, 1f);
        glDrawArrays(GLES20.GL_LINES, 6, 2);

        glUniform4f(uColorLocation, 0.25f, 0.25f, 0.25f, 1.0f);
        glDrawArrays(GLES20.GL_POINTS, 8, 2);
    }
}
