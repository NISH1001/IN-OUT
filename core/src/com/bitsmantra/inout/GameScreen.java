package com.bitsmantra.inout;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector3;
import com.bitsmantra.inout.components.AppearanceComponent;
import com.bitsmantra.inout.components.CameraComponent;
import com.bitsmantra.inout.components.TransformComponent;
import com.bitsmantra.inout.systems.RenderSystem;
import com.bitsmantra.inout.globals.Enum;

import sun.rmi.runtime.Log;

/**
 * Created by paradox on 11/29/15.
 */
public class GameScreen extends ScreenAdapter{
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;

    InOut mGame;
    Engine mEngine;

    private int mState;

    public GameScreen(InOut game){
        mGame = game;
        mState = GAME_RUNNING;

        mEngine = new Engine();

        RenderSystem renderer = new RenderSystem();
        renderer.setSpriteBatch(game.mSpriteBatch);
        renderer.setShapeRenderer(game.mShapeRenderer);
        mEngine.addSystem(renderer);

        createWorld();
    }

    public void update(float deltatime){
        if(deltatime > 0.1f) deltatime = 0.1f;

        mEngine.update(deltatime);

        if(mState == GAME_RUNNING){
            updateRunning(deltatime);
        }
    }

    public void updateRunning(float deltatime){
        mEngine.getSystem(RenderSystem.class).setProcessing(true);
    }

    @Override
    public void render(float deltatime){
        update(deltatime);
    }

    private void createWorld(){
        Entity player = new Entity();
        TransformComponent tc = new TransformComponent();
        tc.position = new Vector3(100, 100, 0);
        player.add(tc);
        AppearanceComponent ac = new AppearanceComponent(Enum.Shape.CIRCLE, true);
        ac.setData(new float[]{10}, new float[]{1, 0, 0, 1});
        player.add(ac);

        float fullRadius = Gdx.graphics.getHeight()/2;

        Entity orbit1 = new Entity();
        TransformComponent tco1 = new TransformComponent();
        tco1.position = new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        orbit1.add(tco1);
        AppearanceComponent aco1 = new AppearanceComponent(Enum.Shape.CIRCLE, false);
        aco1.setData(new float[]{fullRadius}, new float[]{(float) 0.5, (float) 0.5, 0, 1});
        orbit1.add(aco1);

        Entity orbit2 = new Entity();
        TransformComponent tco2 = new TransformComponent();
        tco2.position = new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        orbit2.add(tco2);
        AppearanceComponent aco2 = new AppearanceComponent(Enum.Shape.CIRCLE, false);
        aco2.setData(new float[]{fullRadius-150}, new float[]{(float) 0.5, (float) 0.5, (float) 0.5, 1});
        orbit2.add(aco2);

        Entity orbit3 = new Entity();
        TransformComponent tco3 = new TransformComponent();
        tco3.position = new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        orbit3.add(tco3);
        AppearanceComponent aco3 = new AppearanceComponent(Enum.Shape.CIRCLE, false);
        aco3.setData(new float[]{fullRadius-250}, new float[]{(float) 1.0, (float) 0.5, (float) 0.5, 1});
        orbit3.add(aco3);


        CameraComponent camera = new CameraComponent();
        camera.camera = mEngine.getSystem(RenderSystem.class).getCamera();
        camera.target = player;

        Entity cam = new Entity();
        cam.add(camera);

        mEngine.addEntity(cam);
        mEngine.addEntity(orbit1);
        mEngine.addEntity(orbit2);
        mEngine.addEntity(orbit3);
        mEngine.addEntity(player);
    }
}
