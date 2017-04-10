package math.battle.cheat.output;

public interface Output {
	
	public void output(boolean correct);
	public void output(String notification);

	public void stopThreads();

}
