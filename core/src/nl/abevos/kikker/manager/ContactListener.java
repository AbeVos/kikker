package nl.abevos.kikker.manager;

import nl.abevos.kikker.actor.ContactHandler;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener
{

	@Override
	public void beginContact (Contact contact)
	{
		if (contact.getFixtureA().getUserData() != null)
			((ContactHandler) contact.getFixtureA().getUserData()).beginContact(contact.getFixtureA(), contact.getFixtureB());
		
		if (contact.getFixtureB().getUserData() != null)
			((ContactHandler) contact.getFixtureB().getUserData()).beginContact(contact.getFixtureB(), contact.getFixtureA());
	}

	@Override
	public void endContact (Contact contact)
	{
		if (contact.getFixtureA().getUserData() != null)
			((ContactHandler) contact.getFixtureA().getUserData()).endContact(contact.getFixtureA(), contact.getFixtureB());
		
		if (contact.getFixtureB().getUserData() != null)
			((ContactHandler) contact.getFixtureB().getUserData()).endContact(contact.getFixtureB(), contact.getFixtureA());
	}

	@Override
	public void preSolve (Contact contact, Manifold oldManifold)
	{
		if (contact.getFixtureA().getUserData() != null)
			((ContactHandler) contact.getFixtureA().getUserData()).preSolve(contact.getFixtureA(), contact.getFixtureB());
		
		if (contact.getFixtureB().getUserData() != null)
			((ContactHandler) contact.getFixtureB().getUserData()).preSolve(contact.getFixtureB(), contact.getFixtureA());
	}

	@Override
	public void postSolve (Contact contact, ContactImpulse impulse)
	{
		if (contact.getFixtureA().getUserData() != null)
			((ContactHandler) contact.getFixtureA().getUserData()).postSolve(contact.getFixtureA(), contact.getFixtureB());
		
		if (contact.getFixtureB().getUserData() != null)
			((ContactHandler) contact.getFixtureB().getUserData()).postSolve(contact.getFixtureB(), contact.getFixtureA());	}

}
