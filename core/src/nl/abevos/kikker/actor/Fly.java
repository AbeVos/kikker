package nl.abevos.kikker.actor;

import nl.abevos.kikker.level.Level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Fly extends BodyActor
{

	public Fly(float x, float y, Texture texture)
	{
		super(x, y, texture, Level.getWorld(), (short) 0, (short) 0);
	}

	@Override
	public void update (float delta)
	{
		
	}

	@Override
	public void draw ()
	{
		
	}

	@Override
	protected void BeginContact (Fixture self, Fixture other)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void EndContact (Fixture self, Fixture other)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void PreSolve (Fixture self, Fixture other)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void PostSolve (Fixture self, Fixture other)
	{
		// TODO Auto-generated method stub
		
	}

}
