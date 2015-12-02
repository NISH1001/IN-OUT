package com.bitsmantra.inout.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by paradox on 12/2/15.
 */
public class OrbitComponent implements Component {
    public int id = 0;
    public float angularVelocity = 2.0f;

    public OrbitComponent(int i){
        id = i;
    }
}
