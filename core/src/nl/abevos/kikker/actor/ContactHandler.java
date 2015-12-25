package nl.abevos.kikker.actor;

import com.badlogic.gdx.physics.box2d.Fixture;

public interface ContactHandler
{
	public void beginContact (Fixture self, Fixture other);
	public void endContact (Fixture self, Fixture other);
	public void preSolve (Fixture self, Fixture other);
	public void postSolve (Fixture self, Fixture other);
}
