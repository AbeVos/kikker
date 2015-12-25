package nl.abevos.kikker.manager;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Holds references to assets and creates them when necessary.
 * @author Abe Vos
 */
public class AssetManager
{
	/** The context of the assets that will be loaded. */
	public static enum AssetContext
	{
		Splash, MainMenu, LevelSelection, Game, Misc;
	}
	
	private static final String[] mainMenuTextureNames = new String[] {
		"kikker_00"
	};
	private static final String[] gameTextureNames = new String[] {
		"kikker_00", "tongueSegment", "food", "platform", "wall"
	};
	
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	/** Load all predefined textures from an AssetContext /context/. */
	public static void loadTexturesInContext (AssetContext context)
	{
		switch (context)
		{
		case Splash:
			break;
			
		case MainMenu:
			
			loadTextures(mainMenuTextureNames);
			
			break;
			
		case LevelSelection:
			
			loadTextures(gameTextureNames);
			
			break;
			
		case Game:
			
			loadTextures(gameTextureNames);
			
			break;
			
		case Misc:
			
			loadTextures(gameTextureNames);
			
			break;
			
		default:
			break;
		}
	}
	
	/** Retrieve a loaded texture or load a new one. */
	public static Texture getTexture(String name)
	{
		Texture texture = textures.get(name);
		if (texture == null)
		{
			texture = loadSingleTexture(name);
			if (texture == null)
			{
				System.out.println("Could not load " + name + ".");
				return null;
			}
			else
			{
				System.out.println("Loaded additional Texture: " + name + ".");
				return texture;
			}
		}
		else
		{
			return texture;
		}
	}
	
	/** Loads textures by an array of names. */
	private static void loadTextures (String[] names)
	{
		for (int i = 0; i < names.length; i++)
		{
			loadSingleTexture(names[i]);
		}
	}
	
	/** Load a singe texture. */
	private static Texture loadSingleTexture (String name)
	{
		return textures.put(name, new Texture(Gdx.files.internal("textures/" + name + ".png")));
	}
}
