package nl.abevos.kikker.level;

import nl.abevos.kikker.KikkerGame;
import nl.abevos.kikker.actor.Frog;
import nl.abevos.kikker.environment.Platform;
import nl.abevos.kikker.environment.Wall;
import nl.abevos.kikker.manager.AssetManager;
import nl.abevos.kikker.manager.ContactListener;
import nl.abevos.kikker.screen.GameScreen;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Level
{
	private static World staticWorld;
	private static Camera staticCamera;
	private static Frog staticFrog;
	
	private GameScreen screen;
	
	private World world;
	
	private Box2DDebugRenderer debugRenderer;
	
	private Frog frog;
	private Texture texture;

	private Platform floor;
	private Platform platform1;
	private Platform ceiling;
	private Wall leftWall;
	private Wall rightWall;
	
	public Level (GameScreen screen)
	{
		this.screen = screen;
	}
	
	public void load ()
	{
		world = new World(new Vector2(0, -9.8f), true);
		world.setContactListener(new ContactListener());
		
		Level.staticWorld = world;
		Level.staticCamera = screen.getCamera();
		
		frog = new Frog(0, 0, world);
		
		Level.staticFrog = frog;
		
		texture = AssetManager.getTexture("food");
		
		floor = new Platform(0, - 256);
		platform1 = new Platform(160, -170);
		ceiling = new Platform(0, 256);
		leftWall = new Wall(- 256, 0);
		rightWall = new Wall(256, 0);
		
		debugRenderer = new Box2DDebugRenderer();
	}
	
	public void cameraUpdate (OrthographicCamera camera)
	{
		camera.position.lerp(new Vector3(frog.getPosition().x, frog.getPosition().y, 0), camera.position.dst(frog.getPosition().x, frog.getPosition().y, 0) / 100f);
	}
	
	public void update (float delta)
	{
		world.step(1f / 30, 6, 4);
		
		frog.update(delta);
	}
	
	public void draw ()
	{		
		KikkerGame.batch().draw(texture, 0, 0);
		
		floor.draw();
		platform1.draw();
		ceiling.draw();
		leftWall.draw();
		rightWall.draw();
		
		frog.draw();
	}
	
	public void postDraw ()
	{
		debugRenderer.render(world, getCamera().combined);
	}
	
	public void dispose ()
	{
		world.dispose();
	}
	
	public static World getWorld ()
	{
		return Level.staticWorld;
	}
	
	public static Camera getCamera ()
	{
		return Level.staticCamera;
	}
	
	public static Frog getFrog ()
	{
		return Level.staticFrog;
	}
}
