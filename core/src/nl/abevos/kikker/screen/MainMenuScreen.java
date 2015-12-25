package nl.abevos.kikker.screen;

import nl.abevos.kikker.KikkerGame;
import nl.abevos.kikker.manager.AssetManager;
import nl.abevos.kikker.manager.AssetManager.AssetContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MainMenuScreen extends Screen
{
	public MainMenuScreen ()
	{
		super();
		
		AssetManager.loadTexturesInContext(AssetContext.MainMenu);
	}
	
	@Override
	public void show ()
	{
		
	}

	@Override
	public void update (float delta)
	{
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			KikkerGame.changeScreen(new GameScreen());
		}
	}

	@Override
	public void draw ()
	{
	}

	@Override
	public void postDraw ()
	{
		// TODO Auto-generated method stub
		
	}

}
