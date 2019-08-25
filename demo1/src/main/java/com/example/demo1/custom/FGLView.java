package com.example.demo1.custom;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class FGLView extends GLSurfaceView {
    public FGLView(Context context) {
        this(context, null);
    }

    public FGLView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init();
    }

    public void init(){
        //设置
        setEGLContextClientVersion(2);
        setRenderer(new FGLRender(getContext()));
    }
}
