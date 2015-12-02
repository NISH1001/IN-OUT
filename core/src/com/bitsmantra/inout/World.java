package com.bitsmantra.inout;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.bitsmantra.inout.components.AppearanceComponent;
import com.bitsmantra.inout.components.CameraComponent;
import com.bitsmantra.inout.components.MovementComponent;
import com.bitsmantra.inout.components.OrbitComponent;
import com.bitsmantra.inout.components.TransformComponent;
import com.bitsmantra.inout.globals.*;
import com.bitsmantra.inout.globals.Enum;
import com.bitsmantra.inout.systems.RenderSystem;

import java.util.ArrayList;

/**
 * Created by paradox on 12/2/15.
 */
public class World {
    // 0:outer, 1:inner, so on
    public ArrayList<Float> mOrbitRadius = new ArrayList<Float>();
    public ArrayList<Vector2> mOribitCentre = new ArrayList<Vector2>();

    public Engine mEngine;

    public World(Engine engine){
        mEngine = engine;

        /*
        mOrbitRadius.add();
        mOrbitRadius.add((float) (Gdx.graphics.getHeight()/2-100));

        mOribitCentre.add(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        mOribitCentre.add(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        */
    }

    public void createOrbit(TransformComponent tc, AppearanceComponent ac){
        Entity orbit = new Entity();
        orbit.add(tc);
        orbit.add(ac);
        mEngine.addEntity(orbit);

        mOrbitRadius.add(ac.data[0]);
        mOribitCentre.add( new Vector2(tc.position.x, tc.position.y));
    }


    public void createWorld(){
        float radius = Gdx.graphics.getHeight()/2-25;
        float gap = 100;

        AppearanceComponent aco1 = new AppearanceComponent(Enum.Shape.CIRCLE, false);
        aco1.setData(
                    new float[]{radius},
                    new float[]{(float) 1.0, (float) 0.5, 0, 1}
                );

        this.createOrbit(
                    new TransformComponent(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0.1f)),
                    aco1
                );

        AppearanceComponent aco2 = new AppearanceComponent(Enum.Shape.CIRCLE, false);
        aco2.setData( new float[]{radius-gap}, new float[]{(float) 1.0, (float) 0.5, (float) 0.0, 1} );

        this.createOrbit(
                    new TransformComponent(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0.1f)),
                    aco2
                );

        Entity player = new Entity();
        player.add(new TransformComponent(new Vector3(Gdx.graphics.getWidth() / 2, 25, 0)));
        AppearanceComponent ac = new AppearanceComponent(com.bitsmantra.inout.globals.Enum.Shape.CIRCLE, true);
        ac.setData(new float[]{20}, new float[]{1, 0, 0, 1});
        player.add(ac);
        player.add(new MovementComponent(new Vector2(50.0f, 0.0f), new Vector2(0.0f, 0.0f), 1.0f));
        player.add(new OrbitComponent(1));


        CameraComponent camera = new CameraComponent();
        camera.camera = mEngine.getSystem(RenderSystem.class).getCamera();
        camera.target = player;

        Entity cam = new Entity();
        cam.add(camera);

        mEngine.addEntity(cam);
        mEngine.addEntity(player);
    }


}
