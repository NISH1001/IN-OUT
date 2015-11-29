package com.bitsmantra.inout.components;

import com.badlogic.ashley.core.*;

/**
 * Created by paradox on 11/28/15.
 */
public class PositionComponent implements Component{
    public float x;
    public float y;

    public PositionComponent(){
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public PositionComponent(float xx, float yy){
        this.x = xx;
        this.y = yy;
    }
}
