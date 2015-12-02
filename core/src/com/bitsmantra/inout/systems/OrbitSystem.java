package com.bitsmantra.inout.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.bitsmantra.inout.World;
import com.bitsmantra.inout.components.MovementComponent;
import com.bitsmantra.inout.components.OrbitComponent;
import com.bitsmantra.inout.components.TransformComponent;

/**
 * Created by paradox on 12/2/15.
 */
public class OrbitSystem extends IteratingSystem{
    private ComponentMapper<TransformComponent> mTransforms = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<MovementComponent> mMovements = ComponentMapper.getFor(MovementComponent.class);
    private ComponentMapper<OrbitComponent> mOrbits = ComponentMapper.getFor(OrbitComponent.class);

    private World mWorld;

    public OrbitSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class, OrbitComponent.class).get());
    }

    public void setWorld(World world){
        mWorld = world;
    }

    public void update(float deltatime){
        super.update(deltatime);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent tc = mTransforms.get(entity);
        MovementComponent mc = mMovements.get(entity);
        OrbitComponent oc = mOrbits.get(entity);

        float R = mWorld.mOrbitRadius.get(oc.id);
        float radians = (float) Math.toRadians(tc.rotation);

        tc.position.x = (float) (R * Math.cos(radians)) + mWorld.mOribitCentre.get(oc.id).x;
        tc.position.y = (float) (R * Math.sin(radians)) + mWorld.mOribitCentre.get(oc.id).y;

        tc.rotation += mc.angularVelocity;

        if(tc.rotation > 360) tc.rotation = 0;
    }
}
