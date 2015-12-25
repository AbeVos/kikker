package nl.abevos.kikker;

import nl.abevos.kikker.screen.GameScreen;
import nl.abevos.kikker.screen.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class KikkerGame extends Game
{
	private static KikkerGame game;
	
	private static SpriteBatch batch;
	
	@Override
	public void create ()
	{
		game = this;
		
		batch = new SpriteBatch();
		
		changeScreen(new GameScreen());
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
	}
	
	public static SpriteBatch batch ()
	{
		return batch;
	}
	
	public static void changeScreen (Screen screen)
	{
		game.setScreen(screen);
	}
}
