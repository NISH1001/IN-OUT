package com.bitsmantra.inout.components;


import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
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

    public Enum.Shape shape;

    public Polygon polygon;
    public short[] indices;

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
    /*
    if circle then data consist of only one element i.e. radius
    if polygon then data consist of sequence of x,y values for polygon i.e {x1,y1, x2,y2, ....}
     */
    public void setData(float [] data, float[] color){
        this.data = Arrays.copyOf(data, data.length);
        this.color = Arrays.copyOf(color, color.length);
        if(data.length >3) polygon = new Polygon(data);
    }

    // set data
    public void setData(float[] data){
        this.data = Arrays.copyOf(data, data.length);
        if(data.length >3) polygon = new Polygon(data);

    }

    // if polygon, set the indices for triangles
    public void setIndices(short[] indices){
        if(this.shape == Enum.Shape.POLYGON)
            this.indices = Arrays.copyOf(indices, indices.length);
    }
}
