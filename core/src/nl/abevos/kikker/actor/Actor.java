package nl.abevos.kikker.actor;

import nl.abevos.kikker.Global;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Actor
{
	protected Sprite sprite;
	
	protected Vector2 position;
	
	public Actor (float x, float y, Texture texture)
	{
		position = new Vector2(x, y);
		
		sprite = new Sprite(texture);
		sprite.setScale(1f / Global.pixelToMeter);
		sprite.setOriginCenter();
	}
	
	public abstract void update (float delta);
	public abstract void draw ();
	
	public Vector2 getPosition ()
	{
		return position;
	}
}
