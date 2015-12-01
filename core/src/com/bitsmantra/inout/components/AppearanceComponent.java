package com.bitsmantra.inout.components;


import com.badlogic.ashley.core.*;
import com.bitsmantra.inout.globals.*;
import com.bitsmantra.inout.globals.Enum;

import java.util.Arrays;

/**
 * Created by paradox on 11/29/15.
 */
public class AppearanceComponent implements Component{
    public boolean filled = true;
    public float borderWidth = 5;

    public float[] data;
    public float[] color = {0.0f,0.0f,0.0f, 1.0f};

    public Enum.Shape  shape;

    public AppearanceComponent(Enum.Shape shp, boolean fill){
        shape = shp;
        filled = fill;
    }

    public void setData(float [] data, float[] color){
        this.data = Arrays.copyOf(data, data.length);
        this.color = Arrays.copyOf(color, color.length);
    }
}
