package com.bitsmantra.inout;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bitsmantra.inout.GameScreen;
import com.bitsmantra.inout.systems.RenderSystem;

public class InOut extends Game{
	public SpriteBatch mSpriteBatch;
	public ShapeRenderer mShapeRenderer;

	@Override
	public void create(){
		mSpriteBatch = new SpriteBatch();
		mShapeRenderer = new ShapeRenderer();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render(){
		//black clear screen
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}

	@Override
	public void resize(int width, int height){
		RenderSystem.mViewport.update(width, height);
		RenderSystem.mCamera.position.set(RenderSystem.GAME_WORLD_WIDTH / 2, RenderSystem.GAME_WORLD_HEIGHT / 2, 0);
	}
}