package com.bitsmantra.inout.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by paradox on 11/29/15.
 */
public class TransformComponent implements Component{
    public Vector3 position = new Vector3();
    public Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;
}
