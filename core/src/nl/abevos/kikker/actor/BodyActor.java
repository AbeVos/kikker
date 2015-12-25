package nl.abevos.kikker.actor;

import nl.abevos.kikker.Global;
import nl.abevos.kikker.KikkerGame;
import nl.abevos.kikker.level.Level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/** Actor using a Box2D dynamic Body. 
 * @author Abe Vos */
public abstract class BodyActor extends Actor
{
	protected Body body;
	protected Fixture bodyFixture;
	
	protected ContactHandler contactHandler;
	
	public BodyActor(float x, float y, Texture texture, World world, short categoryBits, short maskBits)
	{
		super(x, y, texture);
		
		createBody(x, y, categoryBits, maskBits);
		
		contactHandler = new ContactHandler()
		{
			@Override
			public void beginContact (Fixture self, Fixture other)
			{
				BeginContact(self, other);
			}

			@Override
			public void endContact (Fixture self, Fixture other)
			{
				EndContact(self, other);
			}

			@Override
			public void preSolve (Fixture self, Fixture other)
			{
				PreSolve(self, other);
			}

			@Override
			public void postSolve (Fixture self, Fixture other)
			{
				PostSolve(self, other);
			}
		};
		
		bodyFixture.setUserData(contactHandler);
	}
	
	@Override
	public void update (float delta)
	{
		//sprite.setCenter(body.getPosition().x * Global.pixelToMeter, body.getPosition().y * Global.pixelToMeter);
	}
	
	public void draw ()
	{
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setCenter(body.getPosition().x, body.getPosition().y);
		
		sprite.draw(KikkerGame.batch());
	}
	
	@Override
	public Vector2 getPosition ()
	{
		return body.getPosition();
	}
	
	public Body getBody ()
	{
		return body;
	}
	
	//////////////////////////
	//	Protected Methods	//
	//////////////////////////
	
	/**
	 * Creates a Box2D Body for this Actor. Is called by the constructor.
	 * @param x	X-position of the Body.
	 * @param y	Y-position of the Body.
	 * @param categoryBits Collision category/categories of this Body.
	 * @param maskBits Collision categories that will be left out of collision checks.
	 */
	protected void createBody (float x, float y, short categoryBits, short maskBits)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x / Global.pixelToMeter, y / Global.pixelToMeter);
		
		body = Level.getWorld().createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sprite.getWidth() / 2 / Global.pixelToMeter, sprite.getHeight() / 2 / Global.pixelToMeter);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 1f;
		fixtureDef.density = 1.5f;
		fixtureDef.restitution = 0.0f;
		fixtureDef.filter.categoryBits = categoryBits;
		fixtureDef.filter.maskBits = maskBits;
		
		bodyFixture = body.createFixture(fixtureDef);
		body.setFixedRotation(true);
		
		shape.dispose();
	}
	
	//////////////////////////
	//	Abstract Methods	//
	//////////////////////////
	
	protected abstract void BeginContact (Fixture self, Fixture other);
	protected abstract void EndContact (Fixture self, Fixture other);
	protected abstract void PreSolve (Fixture self, Fixture other);
	protected abstract void PostSolve (Fixture self, Fixture other);
}
