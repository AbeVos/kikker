package nl.abevos.kikker.state;

import com.badlogic.gdx.utils.Array;

public class GameState
{
	public enum States
	{
		/** Initial state, the GameState will never be set to this. */Null,
		Load,
		Start,
		Play,
		End
	}
	
	private static Array<GameStateListener> listeners = new Array<GameStateListener>();
	
	private static States currentState = States.Null;
	
	public static void addListener (GameStateListener listener)
	{
		listeners.add(listener);
	}
	
	public static void setState (States newState)
	{
		setState(newState, null);
	}
	
	public static void setState (States newState, String debugMessage)
	{
		if (debugMessage == null)
		{
			System.out.println("Set state from " + currentState + " to " + newState + ".");
		}
		else
		{
			System.out.println("Set state from " + currentState + " to " + newState + " --> " + debugMessage);
		}
		
		if (newState == States.Null)
		{
			System.out.println("Game state cannot be set to Null, state was nog changed.");
			return;
		}
		
		States oldState = currentState;
		currentState = newState;
		
		/*for (GameStateListener listener : listeners)
		{
			listener.onStateChanged(newState, oldState);
		}*/
		
		for (int i = 0; i < listeners.size; i++)
		{
			listeners.get(i).onStateChanged(newState, oldState);
		}
	}
	
	//////////////////////
	//	Getter Methods	//
	//////////////////////
	
	public static States getCurrent ()
	{
		return currentState;
	}
}
