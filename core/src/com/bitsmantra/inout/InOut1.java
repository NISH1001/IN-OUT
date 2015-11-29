package com.bitsmantra.inout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.ashley.core.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.bitsmantra.inout.components.AppearanceComponent;
import com.bitsmantra.inout.components.PositionComponent;
import com.bitsmantra.inout.components.TransformComponent;
import com.bitsmantra.inout.systems.RenderSystem;

public class InOut1 extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Texture img;

    Engine engine;
    RenderSystem renderSystem;

    @Override
    public void create () {
        engine = new Engine();

        Entity player = new Entity();
        TransformComponent tc = new TransformComponent();
        tc.position = new Vector3(600,600,0);
        player.add(tc);
        AppearanceComponent ac = new AppearanceComponent("circle");
        ac.setData(new float[]{20}, new float[]{1,0,0,0});
        player.add(ac);

        engine.addEntity(player);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        img = new Texture("badlogic.jpg");

        renderSystem = new RenderSystem();

    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Circle circle = new Circle(400, 400, 20);
        shapeRenderer.circle(circle.x, circle.y, circle.radius);
        shapeRenderer.end();
    }
}
