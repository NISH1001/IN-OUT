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


        Entity player = new Entity();
        TransformComponent tc = new TransformComponent();
        tc.position = new Vector3(400,400,0);
        player.add(tc);
        AppearanceComponent ac = new AppearanceComponent("circle");
        ac.setData(new float[]{20}, new float[]{1, 0, 0, 1});
        player.add(ac);

        Entity box1 = new Entity();
        TransformComponent tc1 = new TransformComponent();
        tc1.position = new Vector3(50, 50, 0);
        box1.add(tc1);
        AppearanceComponent ac1 = new AppearanceComponent("rectangle");
        ac1.setData(new float[]{200, 100}, new float[]{0, 1, 0, 1});
        box1.add(ac1);

        Entity box2 = new Entity();
        TransformComponent tc2 = new TransformComponent();
        tc2.position = new Vector3(800, 800, 0);
        box2.add(tc2);
        AppearanceComponent ac2 = new AppearanceComponent("rectangle");
        ac2.setData(new float[]{300, 300}, new float[]{0, 1, 0, 1});
        box2.add(ac2);

        CameraComponent camera = new CameraComponent();
        camera.camera = mEngine.getSystem(RenderSystem.class).getCamera();
        camera.target = player;

        Entity cam = new Entity();
        cam.add(camera);

        mEngine.addEntity(player);
        mEngine.addEntity(cam);
        mEngine.addEntity(box1);
        mEngine.addEntity(box2);
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
}
