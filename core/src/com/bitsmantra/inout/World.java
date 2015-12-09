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
import com.bitsmantra.inout.components.PlayerControlledComponent;
import com.bitsmantra.inout.components.ScoreComponent;
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

    public ArrayList<Entity> mPlayers = new ArrayList<Entity>();
    public Entity mInOutText;

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
        float radius = RenderSystem.GAME_WORLD_HEIGHT/2-100;
        float gap = 100;

        AppearanceComponent aco1 = new AppearanceComponent(Enum.Shape.CIRCLE, false);
        aco1.setData(
                    new float[]{radius},
                    new float[]{(float) 1.0, (float) 0.5, 0, 1}
                );
        aco1.borderWidth = 0.1f;
        this.createOrbit(
                new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2, RenderSystem.GAME_WORLD_HEIGHT / 2, 0.1f)),
                aco1
        );

        AppearanceComponent aco2 = new AppearanceComponent(Enum.Shape.CIRCLE, false);
        aco2.setData(
                new float[]{radius-gap},
                new float[]{1.0f, 0.5f, 0, 1}
        );
        aco2.borderWidth = 5.0f;
        this.createOrbit(
                new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2, RenderSystem.GAME_WORLD_HEIGHT / 2, 0.1f)),
                aco2
        );

        AppearanceComponent aco3 = new AppearanceComponent(Enum.Shape.CIRCLE, false);
        aco3.setData(
                new float[]{radius-2*gap},
                new float[]{(float) 1.0, (float) 0.5, 0, 1}
        );
        aco3.borderWidth = 0.1f;
        this.createOrbit(
                new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2, RenderSystem.GAME_WORLD_HEIGHT / 2, 0.1f)),
                aco3
        );


        /*
        Entity rect = new Entity();
        AppearanceComponent acr1 = new AppearanceComponent(Enum.Shape.RECTANGLE, true);
        acr1.setData(
                new float[]{25.0f, 50.0f},
                new float[]{0.0f, 1.0f, 0.2f, 1.0f}
            );
        rect.add(acr1);
        rect.add(
                new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2 - acr1.data[0] / 2, RenderSystem.GAME_WORLD_HEIGHT / 2 - acr1.data[1] / 2, 0.1f),
                        20.0f
                )
        );
        */

        // red player
        Entity player1 = new Entity();
        player1.add(new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2, 25, 0), 135.0f));
        AppearanceComponent ac1 = new AppearanceComponent(Enum.Shape.CIRCLE, true);
        ac1.setData(new float[]{50}, new float[]{1, 0, 0, 1});
        player1.add(ac1);
        player1.add(new MovementComponent(new Vector2(50.0f, 0.0f), new Vector2(0.0f, 0.0f), 0.0f));
        player1.add(new OrbitComponent(0));
        player1.add(new ScoreComponent());
        player1.add(new PlayerControlledComponent());
        mEngine.addEntity(player1);

        // green player
        Entity player2 = new Entity();
        player2.add(new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2, 25, 0), 225.0f));
        AppearanceComponent ac2 = new AppearanceComponent(Enum.Shape.CIRCLE, true);
        ac2.setData(new float[]{50}, new float[]{0.0f, 1.0f, 0.0f, 1.0f});
        player2.add(ac2);
        player2.add(new MovementComponent(new Vector2(0.0f, 0.0f), new Vector2(0.0f, 0.0f), 0.0f));
        player2.add(new OrbitComponent(0));
        player2.add(new ScoreComponent());
        player2.add(new PlayerControlledComponent());
        mEngine.addEntity(player2);

        // blue player
        Entity player3 = new Entity();
        player3.add(new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2, 25, 0), -45.0f));
        AppearanceComponent ac3 = new AppearanceComponent(Enum.Shape.CIRCLE, true);
        ac3.setData(new float[]{50}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
        player3.add(ac3);
        player3.add(new MovementComponent(new Vector2(0.0f, 0.0f), new Vector2(0.0f, 0.0f), 0.0f));
        player3.add(new OrbitComponent(0));
        player3.add(new ScoreComponent());
        player3.add(new PlayerControlledComponent());
        mEngine.addEntity(player3);

        // white player
        Entity player4 = new Entity();
        player4.add(new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2, 25, 0), 45.0f));
        AppearanceComponent ac4 = new AppearanceComponent(Enum.Shape.CIRCLE, true);
        ac4.setData(new float[]{50}, new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        player4.add(ac4);
        player4.add(new MovementComponent(new Vector2(0.0f, 0.0f), new Vector2(0.0f, 0.0f), 0.0f));
        player4.add(new OrbitComponent(0));
        player4.add(new ScoreComponent());
        player4.add(new PlayerControlledComponent());
        mEngine.addEntity(player4);


        Entity centerCrcle = new Entity();
        centerCrcle.add(new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2, RenderSystem.GAME_WORLD_HEIGHT / 2, 0)));
        AppearanceComponent acCenterCircle = new AppearanceComponent(Enum.Shape.CIRCLE, false);
        acCenterCircle.setData(new float[]{100}, new float[]{1.0f, 1.0f, 1.0f, 1});
        acCenterCircle.borderWidth = 10.0f;
        centerCrcle.add(acCenterCircle);
        //centerDot.add(new MovementComponent(new Vector2(50.0f, 0.0f), new Vector2(0.0f, 0.0f), 1.0f));
        //centerDot.add(new OrbitComponent(0));
        mEngine.addEntity(centerCrcle);

        Entity inoutText = new Entity();
        TransformComponent tcInOutText = new TransformComponent(
                new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2 - 10, RenderSystem.GAME_WORLD_HEIGHT / 2 - 5, 0)
            );
        tcInOutText.scale = new Vector2(3,3);
        inoutText.add(tcInOutText);
        AppearanceComponent acInOutText = new AppearanceComponent(Enum.Shape.TEXT, true);
        acInOutText.setData(new float[]{0,0}, new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        acInOutText.text = ":D";
        inoutText.add(acInOutText);
        mInOutText = inoutText;
        mEngine.addEntity(mInOutText);

        Entity score1 = new Entity();
        TransformComponent tcScore1 = new TransformComponent(
                new Vector3(50, RenderSystem.GAME_WORLD_HEIGHT- 50, 0)
        );
        tcScore1.scale = new Vector2(5,5);
        score1.add(tcScore1);
        AppearanceComponent acScore1 = new AppearanceComponent(Enum.Shape.TEXT, true);
        acScore1.setData(new float[]{0,0}, new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        acScore1.text = "p1";
        score1.add(acScore1);
        mEngine.addEntity(score1);

        Entity score2 = new Entity();
        TransformComponent tcScore2 = new TransformComponent(
                new Vector3(50, 100, 0)
        );
        tcScore2.scale = new Vector2(5,5);
        score2.add(tcScore2);
        AppearanceComponent acScore2 = new AppearanceComponent(Enum.Shape.TEXT, true);
        acScore2.setData(new float[]{0,0}, new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        acScore2.text = "p2";
        score2.add(acScore2);
        mEngine.addEntity(score2);

        Entity score3 = new Entity();
        TransformComponent tcScore3 = new TransformComponent(
                new Vector3(RenderSystem.GAME_WORLD_WIDTH-100, 100, 0)
        );
        tcScore3.scale = new Vector2(5,5);
        score3.add(tcScore3);
        AppearanceComponent acScore3 = new AppearanceComponent(Enum.Shape.TEXT, true);
        acScore3.setData(new float[]{0,0}, new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        acScore3.text = "p3";
        score3.add(acScore3);
        mEngine.addEntity(score3);

        Entity score4 = new Entity();
        TransformComponent tcScore4 = new TransformComponent(
                new Vector3(RenderSystem.GAME_WORLD_WIDTH-100, RenderSystem.GAME_WORLD_HEIGHT - 100, 0)
        );
        tcScore4.scale = new Vector2(5,5);
        score4.add(tcScore4);
        AppearanceComponent acScore4 = new AppearanceComponent(Enum.Shape.TEXT, true);
        acScore4.setData(new float[]{0,0}, new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        acScore4.text = "p4";
        score4.add(acScore4);
        mEngine.addEntity(score4);


        CameraComponent camera = new CameraComponent();
        camera.camera = mEngine.getSystem(RenderSystem.class).getCamera();
        camera.target = centerCrcle;
        Entity cam = new Entity();
        cam.add(camera);
        mEngine.addEntity(cam);

        /*
        Entity rectangle = new Entity();
        AppearanceComponent acr2 = new AppearanceComponent(Enum.Shape.POLYGON, true);
        acr2.setData(
                new float[] {0,0, 100,0, 100,100, 0,100},
                new float[] {1.0f, 0.0f, 0.0f, 1.0f}
            );
        acr2.setIndices(new short[] {0,1,2, 0,2,3});
        rectangle.add(acr2);
        rectangle.add(
                new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH / 2, RenderSystem.GAME_WORLD_HEIGHT / 2, 0.1f),
                        20.0f
                )
        );
        rectangle.add(new TransformComponent(new Vector3(RenderSystem.GAME_WORLD_WIDTH/2, 25, 0)));
        rectangle.add(new OrbitComponent(0));
        rectangle.add(new MovementComponent(new Vector2(50.0f, 0.0f), new Vector2(0.0f, 0.0f), -2.0f));

        mEngine.addEntity(rectangle);
        */

        mPlayers.add(player1);
        mPlayers.add(player2);
        mPlayers.add(player3);
        mPlayers.add(player4);
    }
}
