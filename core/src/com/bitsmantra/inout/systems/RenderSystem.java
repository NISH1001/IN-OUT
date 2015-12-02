package com.bitsmantra.inout.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bitsmantra.inout.components.AppearanceComponent;
import com.bitsmantra.inout.components.TransformComponent;
import com.bitsmantra.inout.globals.Enum;

import java.util.Comparator;

/**
 * Created by paradox on 11/29/15.
 */
public class RenderSystem extends IteratingSystem{
    public static final float GAME_WORLD_WIDTH = 1920;
    public static final float GAME_WORLD_HEIGHT = 1080;
    public static final float PIXELS_TO_METRES = 1.0f / 32.0f;


    private SpriteBatch mSpriteBatch;
    private ShapeRenderer mShapeRenderer;
    public static OrthographicCamera mCamera;
    public static Viewport mViewport;

    private Array<Entity> mRenderQueue;
    private Comparator<Entity> mComparator;
    //private ImmutableArray<Entity> mEntities;

    private ComponentMapper<TransformComponent> mTransforms = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<AppearanceComponent> mAppearances = ComponentMapper.getFor(AppearanceComponent.class);

    public RenderSystem(){
        super(Family.all(TransformComponent.class, AppearanceComponent.class).get());

        mRenderQueue = new Array<Entity>();

        mComparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int)Math.signum(mTransforms.get(entityB).position.z -
                        mTransforms.get(entityA).position.z);
            }
        };

        //float aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();

        mCamera = new OrthographicCamera();
        mViewport = new FitViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, mCamera);
        mViewport.apply();
        mCamera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
    }

    public void setSpriteBatch(SpriteBatch batch){
        mSpriteBatch = batch;
    }

    public void setShapeRenderer(ShapeRenderer sr){
        mShapeRenderer = sr;
    }

    /*
    public void addedToEngine(Engine engine){
        //mEntities = engine.getEntitiesFor(Family.all(TransformComponent.class, AppearanceComponent.class).get());
    }
    */

    public void update(float deltatime){
        super.update(deltatime);
        mRenderQueue.sort(mComparator);
        mCamera.update();

        mSpriteBatch.setProjectionMatrix(mCamera.combined);
        mShapeRenderer.setProjectionMatrix(mCamera.combined);

        mSpriteBatch.begin();
        mSpriteBatch.end();

        for(Entity entity : mRenderQueue){
            TransformComponent tc = mTransforms.get(entity);
            AppearanceComponent ac = mAppearances.get(entity);

            boolean fill = ac.filled;
            ShapeRenderer.ShapeType st = (fill==true) ? ShapeRenderer.ShapeType.Filled : ShapeRenderer.ShapeType.Line;
            Gdx.gl.glLineWidth(ac.borderWidth);

            if(ac.shape == Enum.Shape.CIRCLE){
                mShapeRenderer.setColor(ac.color[0], ac.color[1], ac.color[2], ac.color[3]);
                mShapeRenderer.begin(st);
                mShapeRenderer.circle(tc.position.x, tc.position.y, ac.data[0]);
                mShapeRenderer.end();
            }

            else if(ac.shape == Enum.Shape.RECTANGLE){
                mShapeRenderer.setColor(ac.color[0], ac.color[1], ac.color[2], ac.color[3]);
                mShapeRenderer.begin(st);
                mShapeRenderer.rect(tc.position.x, tc.position.y, ac.data[0], ac.data[1]);
                mShapeRenderer.end();
            }
            else{
                continue;
            }
        }


        mRenderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltatime){
        mRenderQueue.add(entity);
    }

    public OrthographicCamera getCamera(){
        return mCamera;
    }
}
