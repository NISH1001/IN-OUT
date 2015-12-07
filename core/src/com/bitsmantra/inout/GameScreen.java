package com.bitsmantra.inout;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;
import com.bitsmantra.inout.systems.MovementSystem;
import com.bitsmantra.inout.systems.OrbitSystem;
import com.bitsmantra.inout.systems.RenderSystem;
import com.bitsmantra.inout.globals.Enum;

import sun.rmi.runtime.Log;

/**
 * Created by paradox on 11/29/15.
 */
public class GameScreen extends ScreenAdapter{
    InOut mGame;
    Engine mEngine;
    public World mWorld;

    private Enum.GameState mState;

    public GameScreen(InOut game){
        mGame = game;
        mState = Enum.GameState.RUNNING;

        mEngine = new Engine();
        mWorld = new World(mEngine);

        RenderSystem renderer = new RenderSystem();
        renderer.setSpriteBatch(game.mSpriteBatch);
        renderer.setShapeRenderer(game.mShapeRenderer);
        renderer.setmPolygonSpriteBatch(game.mPolygonSpriteBatch);
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

        if(mState == Enum.GameState.RUNNING){
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
