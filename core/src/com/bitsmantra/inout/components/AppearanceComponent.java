package com.bitsmantra.inout.components;

import com.badlogic.ashley.core.*;

import java.util.Arrays;

/**
 * Created by paradox on 11/29/15.
 */
public class AppearanceComponent implements Component{
    public boolean circle = true;
    public boolean rectangle = false;
    public boolean filled = true;

    public float[] data;
    public float[] color = {0.0f,0.0f,0.0f, 1.0f};

    public AppearanceComponent(String type){
        if(type=="circle"){
            circle = true;
            rectangle = false;
        }
        if(type=="rectangle"){
            rectangle = true;
            circle = false;
        }
    }

    public void setData(float [] data, float[] color){
        this.data = Arrays.copyOf(data, data.length);
        this.color = Arrays.copyOf(color, color.length);
    }
}
