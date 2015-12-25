package nl.abevos.kikker.environment;

import nl.abevos.kikker.Global;
import nl.abevos.kikker.KikkerGame;
import nl.abevos.kikker.level.Level;
import nl.abevos.kikker.manager.AssetManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Platform
{
	private Sprite sprite;
	
	private Body body;
	
	public Platform (float x, float y)
	{
		sprite = new Sprite(AssetManager.getTexture("platform"));
		sprite.setOriginCenter();
		sprite.setCenter(x / Global.pixelToMeter, y / Global.pixelToMeter);
		sprite.setScale(1 / Global.pixelToMeter);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x / Global.pixelToMeter, y / Global.pixelToMeter);
		
		body = Level.getWorld().createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sprite.getWidth() / Global.pixelToMeter, sprite.getHeight() / Global.pixelToMeter);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		fixtureDef.filter.categoryBits = Global.FLOOR_LAYER;
		
		body.createFixture(fixtureDef);
		
		shape.dispose();
	}
	
	public void update (float delta)
	{
		
	}
	
	public void draw ()
	{
		sprite.draw(KikkerGame.batch());
	}
}
