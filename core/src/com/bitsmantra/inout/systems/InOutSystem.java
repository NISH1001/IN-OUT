package com.bitsmantra.inout.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.bitsmantra.inout.World;
import com.bitsmantra.inout.components.AppearanceComponent;
import com.bitsmantra.inout.components.MovementComponent;
import com.bitsmantra.inout.components.OrbitComponent;
import com.bitsmantra.inout.components.PlayerControlledComponent;
import com.bitsmantra.inout.components.ScoreComponent;
import com.bitsmantra.inout.components.TransformComponent;
import com.bitsmantra.inout.globals.Enum;

import java.util.Random;

/**
 * Created by paradox on 12/9/15.
 */
public class InOutSystem extends IteratingSystem implements InputProcessor{
    private ComponentMapper<TransformComponent> mTransforms = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<MovementComponent> mMovements = ComponentMapper.getFor(MovementComponent.class);
    private ComponentMapper<OrbitComponent> mOrbits = ComponentMapper.getFor(OrbitComponent.class);
    private ComponentMapper<ScoreComponent> mScores = ComponentMapper.getFor(ScoreComponent.class);
    private ComponentMapper<PlayerControlledComponent> mControls = ComponentMapper.getFor(PlayerControlledComponent.class);

    private World mWorld;

    static float timePassed = 0;
    private String mCurrentState = "IN";
    private float mInitTime = 10;
    private float mInOutTIme = 6;
    private float mTimeDecrease = 0.2f;

    public InOutSystem() {
        super(Family.all(
                TransformComponent.class,
                        MovementComponent.class,
                        OrbitComponent.class,
                        ScoreComponent.class).get()
        );

        Gdx.input.setInputProcessor(this);
    }

    public void setWorld(World world){
        mWorld = world;
    }

    public void update(float deltatime){
        super.update(deltatime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        boolean rand = InOutSystem.randomizeInOut();

        InOutSystem.timePassed += deltaTime;
        AppearanceComponent ac = mWorld.mInOutText.getComponent(AppearanceComponent.class);
        ac.text = String.valueOf( (int) timePassed);
        if(InOutSystem.timePassed > mInitTime && InOutSystem.timePassed < (mInitTime + mInOutTIme )){
            Gdx.app.log("time", String.valueOf(InOutSystem.timePassed));
            if(ac.shape == Enum.Shape.TEXT){
                ac.text = mCurrentState;
            }
        }
        if(InOutSystem.timePassed > mInitTime + mInOutTIme){
            String text = (InOutSystem.randomizeInOut() == true) ? "IN" : "OUT";
            mCurrentState = text;
            InOutSystem.timePassed = 0.0f;
            mInitTime -= mTimeDecrease;
        }

        if(mInitTime < 9){
            MovementComponent mc = mMovements.get(entity);
            mc.angularVelocity = 5.0f;
            mc.angularAcceleration = 0.1f;
        }
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){
        Gdx.app.log("x", String.valueOf(x));
        Gdx.app.log("y", String.valueOf(y));
        int id = -1;
        if(x>=0 && x<=300 && y>=0 && y>=0 && y<=RenderSystem.GAME_WORLD_HEIGHT/2){
            id = 0;
        }
        else if(x>=0 && x<=300 && y>RenderSystem.GAME_WORLD_HEIGHT/2 && y<=RenderSystem.GAME_WORLD_HEIGHT){
            id = 1;
        }
        else if(x>= RenderSystem.GAME_WORLD_WIDTH/2 && x<=RenderSystem.GAME_WORLD_WIDTH
                && y>=RenderSystem.GAME_WORLD_HEIGHT/2 && y<=RenderSystem.GAME_WORLD_HEIGHT
                ){
            id = 2;
        }

        else if(x>= RenderSystem.GAME_WORLD_WIDTH/2 && x<=RenderSystem.GAME_WORLD_WIDTH
                && y>=0 && y<RenderSystem.GAME_WORLD_HEIGHT/2
                ){
            id = 3;
        }
        else{
            return true;
        }

        Entity player = mWorld.mPlayers.get(id);
        TransformComponent tc = mTransforms.get(player);
        OrbitComponent oc = mOrbits.get(player);
        if(oc.id == 0){
            oc.id = 2;
        }
        else if(oc.id == 2){
            oc.id = 0;
        }
        else{

        }

        return true;
    }

    public static boolean randomizeInOut(){
        int max = 1, min = 0;
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min) + 1) + min;
        return (randomNum == 0) ? true : false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
