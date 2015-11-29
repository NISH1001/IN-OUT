package com.bitsmantra.inout.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by paradox on 11/29/15.
 */
public class CameraComponent implements Component{
    public Entity target;
    public OrthographicCamera camera;
}
