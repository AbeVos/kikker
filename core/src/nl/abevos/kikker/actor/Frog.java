package nl.abevos.kikker.actor;

import nl.abevos.kikker.Global;
import nl.abevos.kikker.KikkerGame;
import nl.abevos.kikker.level.Level;
import nl.abevos.kikker.manager.AssetManager;
import nl.abevos.kikker.state.GameState;
import nl.abevos.kikker.state.GameState.States;
import nl.abevos.kikker.state.GameStateListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Frog extends BodyActor implements GameStateListener, InputProcessor
{
	private enum FrogState
	{
		Idle,
		Hurt,
		PrepareJump,
		Jump,
		Fall,
		Eat,
	}
	
	private FrogState state;
	private float stateTime;
	
	private Fixture baseFixture;
	
	private FrogTongue tongue;
	
	private boolean onLand;
	
	public Frog (float x, float y, World world)
	{
		super(x, y, AssetManager.getTexture("kikker_00"), world, Global.FROG_LAYER, (short) (Global.FLOOR_LAYER | Global.WALL_LAYER));
		
		Gdx.input.setInputProcessor(this);
		GameState.addListener(this);
		
		//	Instantiate tongue.
		
		tongue = new FrogTongue(getPosition(), body);
	}
	
	//////////////////////////
	//	Inherited Methods	//
	//////////////////////////

	@Override
	public void update (float delta)
	{
		super.update(delta);
		
		switch (state)
		{
		case Idle:
			break;
		case Eat:
			break;
		case Fall:
			if (onLand)
			{
				setState(FrogState.Idle);
			}
			break;
		case Hurt:
			break;
			
		case Jump:
			
			//body.setTransform(position.cpy().lerp(jumpTarget, stateTime), 0);
			//body.setLinearVelocity(jumpTarget.cpy().sub(position));
			

			if (stateTime > 1f || onLand)
			{
				//body.setLinearVelocity(0, 0);
				body.setGravityScale(1f);
				
				setState(FrogState.Fall);
			}
			
			stateTime += delta * 5;
			
			break;
			
		case PrepareJump:
			break;
		default:
			break;
		
		}
		
		tongue.update(delta);
	}
	
	public void draw ()
	{
		if (state == FrogState.Jump || state == FrogState.Fall)
		{
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2 - 10);
			sprite.setRotation(- 90 + MathUtils.radiansToDegrees * MathUtils.atan2(body.getLinearVelocity().y, body.getLinearVelocity().x));
			
			sprite.setCenter(getPosition().x, getPosition().y + 10);
			
			sprite.draw(KikkerGame.batch());
		}
		else
		{
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2 - 10);
			sprite.setRotation(0);
			
			sprite.setCenter(getPosition().x, getPosition().y + 10);
			
			sprite.draw(KikkerGame.batch());
		}
		
		tongue.draw();
	}

	@Override
	protected void BeginContact (Fixture self, Fixture other)
	{
		if (other.getFilterData().categoryBits == Global.FLOOR_LAYER)
		{
			if (state == FrogState.Fall)
				setState(FrogState.Idle);
		}
	}

	@Override
	protected void EndContact (Fixture self, Fixture other)
	{
		onLand = false;
	}

	@Override
	protected void PreSolve (Fixture self, Fixture other)
	{
	}

	@Override
	protected void PostSolve (Fixture self, Fixture other)
	{
	}
	
	@Override
	protected void createBody(float x, float y, short categoryBits, short maskBits)
	{
		//super.createBody(x, y, categoryBits, maskBits);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x / Global.pixelToMeter, y / Global.pixelToMeter);
		
		body = Level.getWorld().createBody(bodyDef);
		
		Shape shape = new CircleShape();
		shape.setRadius(13f / Global.pixelToMeter);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 1f;
		fixtureDef.density = 1.5f;
		fixtureDef.restitution = 0.0f;
		fixtureDef.filter.categoryBits = categoryBits;
		fixtureDef.filter.maskBits = maskBits;
		
		bodyFixture = body.createFixture(fixtureDef);
		body.setFixedRotation(true);
		
		shape = new PolygonShape();
		((PolygonShape) shape).setAsBox(13f / Global.pixelToMeter, 4f / Global.pixelToMeter, new Vector2(0, -13f / Global.pixelToMeter), 0);
		fixtureDef.shape = shape;
		fixtureDef.isSensor = true;
		fixtureDef.density = 0f;
		fixtureDef.filter.categoryBits = Global.FROGBASE_LAYER;
		fixtureDef.filter.maskBits = Global.FLOOR_LAYER;
		baseFixture = body.createFixture(fixtureDef);
		
		shape.dispose();
	}
	
	//////////////////////////
	//	Private Methods.	//
	//////////////////////////
	
	private void setState (FrogState newState)
	{
		state = newState;
		
		stateTime = 0f;
		
		switch (state)
		{
		case Idle:
			onLand = true;
			break;
			
		default:
			break;
		}
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
			setState(FrogState.Idle);
			
			break;
			
		default:
			break;
		}
	}

	@Override
	public boolean keyDown (int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp (int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped (char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button)
	{
		switch (state)
		{
		case Idle:
			//	Transform screen coordinates to world coordinates.
			
			Vector3 touch3 = Level.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			Vector2 touch2 = new Vector2(touch3.x, touch3.y).sub(body.getPosition()).nor().scl(Global.pixelToMeter);
			
			body.setGravityScale(0.1f);
			body.applyLinearImpulse(touch2, body.getPosition(), true);
			
			//	Set state to Jump.
			setState(FrogState.Jump);
			break;
			
		case Eat:
			break;
		case Fall:
			tongue.enableTongue();
			break;
		case Hurt:
			break;
		case Jump:
			tongue.enableTongue();
			break;
		case PrepareJump:
			break;
		default:
			break;
		}
		
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled (int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
