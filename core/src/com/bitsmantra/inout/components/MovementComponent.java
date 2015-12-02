package com.bitsmantra.inout.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by paradox on 12/2/15.
 */
public class MovementComponent implements Component{
    public Vector2 velocity = new Vector2(0.0f, 0.0f);
    public Vector2 acceleration = new Vector2(0.0f, 0.0f);
    public float angularVelocity = 0.0f;

    public MovementComponent(){
    }

    public MovementComponent(Vector2 vel, Vector2 acc, float omega){
        velocity = vel;
        acceleration = acc;
        angularVelocity = omega;
    }
}
