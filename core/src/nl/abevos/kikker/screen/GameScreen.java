package nl.abevos.kikker.screen;

import nl.abevos.kikker.Global;
import nl.abevos.kikker.level.Level;
import nl.abevos.kikker.manager.AssetManager;
import nl.abevos.kikker.manager.AssetManager.AssetContext;
import nl.abevos.kikker.state.GameState;
import nl.abevos.kikker.state.GameState.States;
import nl.abevos.kikker.state.GameStateListener;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen extends Screen implements GameStateListener
{
	private Level level;
	
	public GameScreen ()
	{
		GameState.addListener(this);
		
		AssetManager.loadTexturesInContext(AssetContext.Game);
		
		camera = new OrthographicCamera(Global.WIDTH / Global.pixelToMeter, Global.HEIGHT / Global.pixelToMeter);
		
		level = new Level(this);
		
		GameState.setState(States.Load);
	}
	
	//////////////////////////
	//	Inherited Methods	//
	//////////////////////////

	@Override
	public void show ()
	{

	}
	
	@Override
	public void cameraUpdate (OrthographicCamera camera)
	{
		if (GameState.getCurrent() == States.Play)
		{
			level.cameraUpdate(camera);
		}
	}

	@Override
	public void update (float delta)
	{
		if (GameState.getCurrent() == States.Play)
		{
			level.update(delta);
		}
	}

	@Override
	public void draw ()
	{
		if (GameState.getCurrent() == States.Play)
		{
			level.draw();
		}
	}
	
	public void postDraw ()
	{
		if (GameState.getCurrent() == States.Play)
		{
			level.postDraw();
		}
	}
	
	public void dispose ()
	{
		super.dispose();
		
		level.dispose();
	}

	//////////////////////////
	//	Implemented Methods	//
	//////////////////////////

	@Override
	public void onStateChanged (States newState, States oldState)
	{
		switch (newState)
		{
		case Load:
			level.load();
			
			GameState.setState(States.Play);
			break;
			
		default:
			break;
		
		}
	}
}
