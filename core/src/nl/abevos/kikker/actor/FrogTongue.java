package nl.abevos.kikker.actor;

import nl.abevos.kikker.Global;
import nl.abevos.kikker.KikkerGame;
import nl.abevos.kikker.level.Level;
import nl.abevos.kikker.manager.AssetManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class FrogTongue
{
	private enum TongueStates
	{
		disabled, enabled, locked
	}
	
	private TongueStates state = TongueStates.enabled;
	
	private Body[] tongueSegments;
	
	private float stateTime = 0f;
	
	private Sprite spriteTongueSegment;
	
	//////////////////
	//	Constructor	//
	//////////////////
	
	public FrogTongue (Vector2 position, Body body)
	{
		tongueSegments = new Body[12];
		
		PolygonShape shape = new PolygonShape();
		
		for (int i = 0; i < tongueSegments.length; i++)
		{
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			if (i == tongueSegments.length - 1)
			{
				bodyDef.position.set(position.x, position.y + i * 4 / Global.pixelToMeter);
			}
			else
			{
				bodyDef.position.set(position.x, position.y + i * 8 / Global.pixelToMeter);
			}
			
			tongueSegments[i] = Level.getWorld().createBody(bodyDef);
			
			shape = new PolygonShape();
			if (i == tongueSegments.length - 1)
			{
				shape.setAsBox(4 / Global.pixelToMeter, 4 / Global.pixelToMeter);
			}
			else
			{
				shape.setAsBox(2 / Global.pixelToMeter, 12 / Global.pixelToMeter);
			}
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.friction = 0f;
			fixtureDef.density = (i == tongueSegments.length - 1) ? 0.5f : 0.1f;
			fixtureDef.restitution = 0.0f;
			fixtureDef.filter.categoryBits = 1;
			fixtureDef.filter.maskBits = Global.FLOOR_LAYER | Global.WALL_LAYER;
			
			tongueSegments[i].createFixture(fixtureDef);
			
			RevoluteJointDef jointDef = new RevoluteJointDef();
			if (i == 0)
			{
				jointDef.bodyA = body;
				jointDef.bodyB = tongueSegments[i];
				
				jointDef.localAnchorA.set(0, + 1 / Global.pixelToMeter);
				jointDef.localAnchorB.set(0, - 8 / Global.pixelToMeter);
			}
			else
			{
				jointDef.bodyA = tongueSegments[i - 1];
				jointDef.bodyB = tongueSegments[i];
				
				jointDef.localAnchorA.set(0, 11 / Global.pixelToMeter);
				
				if (i == tongueSegments.length - 1)
				{
					jointDef.localAnchorB.set(0, - 4 / Global.pixelToMeter);
				}
				else
				{
					jointDef.localAnchorB.set(0, - 11 / Global.pixelToMeter);
				}
			}
			
			jointDef.collideConnected = false;
			
			Level.getWorld().createJoint(jointDef);
			
			//	Dispose of shape after last tongue segment.
			//if (i == tongueSegments.length - 1) shape.dispose();
		}
		
		shape.dispose();
		
		spriteTongueSegment = new Sprite(AssetManager.getTexture("tongueSegment"));
		spriteTongueSegment.setScale(1 / Global.pixelToMeter);
		spriteTongueSegment.setOriginCenter();
		
		disableTongue();
	}
	
	//////////////////////
	//	Public Methods	//
	//////////////////////
	
	public void update (float delta)
	{
		if (state == TongueStates.enabled)
		{
			if (stateTime > 1f)
			{
				disableTongue();
				//lockTongue();
			}
			
			stateTime += delta;
		}
		else if (state == TongueStates.locked)
		{
			//tongueSegments[tongueSegments.length - 1].setTransform(0, 0, 0);
		}
	}
	
	public void draw ()
	{
		if (state == TongueStates.disabled) return;
		
		for (Body segment : tongueSegments)
		{
			spriteTongueSegment.setRotation(segment.getAngle() * MathUtils.radiansToDegrees);
			spriteTongueSegment.setCenter(segment.getPosition().x, segment.getPosition().y);
			spriteTongueSegment.draw(KikkerGame.batch());
		}
	}
	
	public void enableTongue ()
	{
		if (state != TongueStates.disabled) return;
		
		setState(TongueStates.enabled);
		
		for (Body segment : tongueSegments)
		{
			segment.setTransform(Level.getFrog().getPosition(), 0);

			segment.setActive(true);
		}
		
		tongueSegments[tongueSegments.length - 1].applyForceToCenter(-100, 100, true);
	}
	
	public void disableTongue ()
	{
		if (state == TongueStates.disabled) return;
		
		setState(TongueStates.disabled);
		
		for (Body segment : tongueSegments)
		{
			segment.setActive(false);
		}
	}
	
	public void lockTongue ()
	{
		if (state != TongueStates.enabled) return;
		
		setState(TongueStates.locked);
		
		System.out.println("Lock tongue");
	}
	
	//////////////////////
	//	Private Methods	//
	//////////////////////

	private void setState (TongueStates newState)
	{
		stateTime = 0f;
		
		//TongueStates oldState = state;
		state = newState;
		
		switch (newState)
		{
		case disabled:
			
			break;
			
		case enabled:
			
			break;
			
		case locked:
			
			break;
			
		default:
			break;
		
		}
	}
}
