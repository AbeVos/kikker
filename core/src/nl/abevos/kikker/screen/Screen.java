package nl.abevos.kikker.screen;

import nl.abevos.kikker.Global;
import nl.abevos.kikker.KikkerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public abstract class Screen implements com.badlogic.gdx.Screen
{
	protected OrthographicCamera camera;
	
	public Screen ()
	{
		camera = new OrthographicCamera(Global.WIDTH, Global.HEIGHT);
		//camera.position.set(Global.WIDTH / 2, Global.HEIGHT / 2, 0);
		camera.position.set(Vector3.Zero);
	}
	
	//////////////////////////
	//	Abstract Methods	//
	//////////////////////////
	
	public abstract void update (float delta);
	public abstract void draw ();
	public abstract void postDraw ();
	
	//////////////////////
	//	Public Methods	//
	//////////////////////
	
	public void cameraUpdate (OrthographicCamera camera)
	{
		
	}
	
	//////////////////////////
	//	Inherited Methods	//
	//////////////////////////

	@Override
	public void render (float delta)
	{
		cameraUpdate(camera);
		camera.update();
		
		update (delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		KikkerGame.batch().setProjectionMatrix(camera.combined);
		KikkerGame.batch().begin();

		draw ();
		
		KikkerGame.batch().end();
		
		postDraw();
	}

	@Override
	public void resize (int width, int height)
	{
		//camera.viewportWidth = width;
		//camera.viewportHeight = height;
	}

	@Override
	public void pause ()
	{}

	@Override
	public void resume ()
	{}

	@Override
	public void hide ()
	{}

	@Override
	public void dispose ()
	{}
	
	public Camera getCamera ()
	{
		return camera;
	}
}
