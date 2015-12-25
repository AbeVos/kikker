package nl.abevos.kikker.state;

public interface GameStateListener
{
	public void onStateChanged (GameState.States newState, GameState.States oldState);
}
