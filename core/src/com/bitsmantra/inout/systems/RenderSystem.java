package com.bitsmantra.inout.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bitsmantra.inout.components.AppearanceComponent;
import com.bitsmantra.inout.components.TransformComponent;
import com.bitsmantra.inout.globals.Enum;
import com.bitsmantra.inout.globals.Global;

import java.util.Comparator;

/**
 * Created by paradox on 11/29/15.
 */
public class RenderSystem extends IteratingSystem{
    public static final float GAME_WORLD_WIDTH = 1920;
    public static final float GAME_WORLD_HEIGHT = 1080;

    private SpriteBatch mSpriteBatch;
    private ShapeRenderer mShapeRenderer;
    private PolygonSpriteBatch mPolygonSpriteBatch;
    public static OrthographicCamera mCamera;
    public static Viewport mViewport;

    private Array<Entity> mRenderQueue;
    private Comparator<Entity> mComparator;

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

    public void setmPolygonSpriteBatch(PolygonSpriteBatch polyBatch){
        mPolygonSpriteBatch = polyBatch;
    }

    public void update(float deltatime){
        super.update(deltatime);
        mRenderQueue.sort(mComparator);
        mCamera.update();

        mSpriteBatch.setProjectionMatrix(mCamera.combined);
        mShapeRenderer.setProjectionMatrix(mCamera.combined);

        mSpriteBatch.begin();
        mSpriteBatch.end();

        /*
        PolygonRegion polygonRegion = Global.createPolygon(new float[] {1.0f, 0.0f, 0.0f, 1.0f},new float[] {0,0, 0,100, 100,0, 100,100},
                new short[] {0,1,2, 0,2,3});
        PolygonSprite poly = new PolygonSprite(polygonRegion);
        poly.setOrigin(100, 100);
        PolygonSpriteBatch polybatch = new PolygonSpriteBatch();

        polybatch.begin();
        poly.draw(polybatch);
        polybatch.end();
        */

        for(Entity entity : mRenderQueue){
            TransformComponent tc = mTransforms.get(entity);
            AppearanceComponent ac = mAppearances.get(entity);

            boolean fill = ac.filled;
            ShapeRenderer.ShapeType st = (fill==true) ? ShapeRenderer.ShapeType.Filled : ShapeRenderer.ShapeType.Line;
            Gdx.gl.glLineWidth(ac.borderWidth);

            // if shape is circle
            if(ac.shape == Enum.Shape.CIRCLE){
                mShapeRenderer.setColor(ac.color[0], ac.color[1], ac.color[2], ac.color[3]);
                mShapeRenderer.begin(st);
                mShapeRenderer.circle(tc.position.x, tc.position.y, ac.data[0]);
                mShapeRenderer.end();
            }

            // if shape is rectangle
            else if(ac.shape == Enum.Shape.RECTANGLE){
                mShapeRenderer.setColor(ac.color[0], ac.color[1], ac.color[2], ac.color[3]);
                mShapeRenderer.begin(st);
                mShapeRenderer.rect(
                        tc.position.x, tc.position.y,
                        tc.origin.x, tc.origin.y,
                        ac.data[0], ac.data[1],
                        tc.scale.x, tc.scale.y,
                        tc.rotation
                );
                mShapeRenderer.end();
            }

            // if shape is general polygon
            else if(ac.shape == Enum.Shape.POLYGON){
                ac.polygon.setPosition(tc.position.x, tc.position.y);
                ac.polygon.setOrigin(tc.origin.x, tc.origin.y);
                ac.polygon.setRotation(tc.rotation);
                PolygonRegion polygonRegion = Global.createPolygon(ac.color, ac.polygon.getTransformedVertices(),
                        ac.indices);
                PolygonSprite poly = new PolygonSprite(polygonRegion);
                poly.setOrigin(ac.polygon.getOriginX(), ac.polygon.getOriginY());

                mPolygonSpriteBatch.begin();
                poly.draw(mPolygonSpriteBatch);
                mPolygonSpriteBatch.end();
            }

            // f**k this shit
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
