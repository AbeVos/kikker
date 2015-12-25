package nl.abevos.kikker;

import com.badlogic.gdx.Gdx;

public class Global
{
	public final static float HEIGHT = 480;
	public final static float WIDTH = (HEIGHT * Gdx.graphics.getWidth()) / Gdx.graphics.getHeight();
	
	public static final float pixelToMeter = 23;
	
	public static final short FROG_LAYER = 2;
	public static final short FROGBASE_LAYER = 4;
	public static final short FLOOR_LAYER = 8;
	public static final short WALL_LAYER = 16;
}
