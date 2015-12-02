package com.bitsmantra.inout.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;


import com.bitsmantra.inout.components.AppearanceComponent;
import com.bitsmantra.inout.components.MovementComponent;
import com.bitsmantra.inout.components.TransformComponent;

/**
 * Created by paradox on 12/2/15.
 */
public class MovementSystem extends IteratingSystem{
    private ComponentMapper<TransformComponent> mTransforms = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<MovementComponent> mMovements = ComponentMapper.getFor(MovementComponent.class);

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());
    }

    public void update(float deltatime){
        super.update(deltatime);
    }

    @Override
    public void processEntity(Entity entity, float deltatime) {
        /*
        TransformComponent tc = mTransforms.get(entity);
        MovementComponent mc = mMovements.get(entity);
        tc.position.x += mc.velocity.x * deltatime;
        tc.position.y += mc.velocity.y * deltatime;
        */
    }
}
