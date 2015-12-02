package com.bitsmantra.inout;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bitsmantra.inout.components.AppearanceComponent;
import com.bitsmantra.inout.components.CameraComponent;
import com.bitsmantra.inout.components.MovementComponent;
import com.bitsmantra.inout.components.OrbitComponent;
import com.bitsmantra.inout.components.TransformComponent;
import com.bitsmantra.inout.systems.MovementSystem;
import com.bitsmantra.inout.systems.OrbitSystem;
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
    public World mWorld;

    private int mState;

    public GameScreen(InOut game){
        mGame = game;
        mState = GAME_RUNNING;

        mEngine = new Engine();
        mWorld = new World(mEngine);

        RenderSystem renderer = new RenderSystem();
        renderer.setSpriteBatch(game.mSpriteBatch);
        renderer.setShapeRenderer(game.mShapeRenderer);
        mEngine.addSystem(renderer);
        mEngine.addSystem(new MovementSystem());

        OrbitSystem orbiter = new OrbitSystem();
        orbiter.setWorld(mWorld);
        mEngine.addSystem(orbiter);

        mWorld.createWorld();
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
        mEngine.getSystem(MovementSystem.class).setProcessing(true);
        mEngine.getSystem(OrbitSystem.class).setProcessing(true);
    }

    @Override
    public void render(float deltatime){
        update(deltatime);
    }
}
