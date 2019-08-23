package com.example.demo1.utils;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;

public class ShaderHelper {
    public static int compileVertexShader(String shaderCode){
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode){
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    /**
     * 编译着色器
     * @param type 着色器类型
     * @param shaderCode 着色器代码
     * @return 返回着色器id
     */
    private static int compileShader(int type, String shaderCode){
        //创建一个shader对象
        //返回值为shader的引用
        int shaderObjectId = glCreateShader(type);
        //返回0表示创建对象失败
        if (shaderObjectId == 0){
            Logger.e_dev("无法创建shader");
            return 0;
        }

        //上传着色器代码
        glShaderSource(shaderObjectId, shaderCode);
        //编译着色器
        glCompileShader(shaderObjectId);

        //取出编译状态
        int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        //取出着色器信息日志
        Logger.w_dev(glGetShaderInfoLog(shaderObjectId));

        //如果状态值返回为0则表示编译失败
        if (compileStatus[0] == 0){
            //删除着色器
            glDeleteShader(shaderObjectId);
            Logger.e_dev("opengl编译失败：" + shaderCode);

            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId){
        //新建opengl程序
        //返回值为程序id
        int programObjectId = glCreateProgram();
        if (programObjectId == 0){
            Logger.e_dev("创建opengl程序失败");
            return 0;
        }

        //attach上顶点着色器和片段着色器
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);

        //链接程序中的着色器
        glLinkProgram(programObjectId);

        //获取链接状态
        int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if (linkStatus[0] == 0){
            //删除opengl程序
            glDeleteProgram(programObjectId);
            Logger.e_dev("初始化链接器失败：" + glGetProgramInfoLog(programObjectId));
        }else {
            Logger.w_dev("打印程序日志:" + glGetProgramInfoLog(programObjectId));
        }

        return programObjectId;
    }

    /**
     * 验证opengl程序的对象
     * @param programObjectId
     * @return
     */
    public static boolean validateProgram(int programObjectId){
        //先验证程序
        glValidateProgram(programObjectId);

        //再获取程序状态
        int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Logger.d_dev("返回opengl程序验证结果：" + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }
}
