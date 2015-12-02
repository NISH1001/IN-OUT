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

    // for circle it is radius, for rectangle it is w,h
    public float[] data;

    // rgba
    public float[] color = {0.0f,0.0f,0.0f, 1.0f};

    public Enum.Shape  shape;

    public AppearanceComponent(){}

    // accept enum and fill type
    public AppearanceComponent(Enum.Shape shp, boolean fill){
        shape = shp;
        filled = fill;
    }

    // accept enum, filltype and color
    public AppearanceComponent(Enum.Shape shp, boolean fill, float[] col){
        shape = shp;
        filled = fill;
        color = Arrays.copyOf(col, col.length);
    }

    // set data, color
    public void setData(float [] data, float[] color){
        this.data = Arrays.copyOf(data, data.length);
        this.color = Arrays.copyOf(color, color.length);
    }

    // set data
    public void setData(float[] data){
        this.data = Arrays.copyOf(data, data.length);
    }
}
