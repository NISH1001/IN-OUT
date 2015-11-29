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
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import com.bitsmantra.inout.components.AppearanceComponent;
import com.bitsmantra.inout.components.PositionComponent;
import com.bitsmantra.inout.components.TransformComponent;

/**
 * Created by paradox on 11/29/15.
 */
public class RenderSystem extends IteratingSystem{
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    static final float PIXELS_TO_METRES = 1.0f / 32.0f;


    private SpriteBatch mSpriteBatch;
    private ShapeRenderer mShapeRenderer;
    private OrthographicCamera mCamera;

    private Array<Entity> mRenderQueue;
    //private ImmutableArray<Entity> mEntities;

    private Entity mCurrentEntity = new Entity();

    private ComponentMapper<TransformComponent> mTransforms = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<AppearanceComponent> mAppearances = ComponentMapper.getFor(AppearanceComponent.class);

    public RenderSystem(){
        super(Family.all(TransformComponent.class, AppearanceComponent.class).get());
        mRenderQueue = new Array<Entity>();
        mCamera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        mCamera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
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
        System.out.println("hello");
        mCamera.update();
        mSpriteBatch.setProjectionMatrix(mCamera.combined);

        mSpriteBatch.begin();
        mSpriteBatch.end();

        for(Entity entity : mRenderQueue){
            TransformComponent tc = mTransforms.get(entity);
            AppearanceComponent ac = mAppearances.get(entity);

            if(ac.circle){
                mShapeRenderer.setColor(ac.color[0], ac.color[1], ac.color[2], ac.color[3]);
                mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                mShapeRenderer.circle(tc.position.x, tc.position.y, ac.data[0]);
                mShapeRenderer.end();
            }

            else if(ac.rectangle){
                mShapeRenderer.setColor(ac.color[0], ac.color[1], ac.color[2], ac.color[3]);
                mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
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
