package techcable.minecraft.mcpdata.terminal;

public abstract class Terminal {
	static {
		if (System.console() != null) {
			TERMINAL = new ConsoleTerminal(System.console());
		} else {
			TERMINAL = new InOutTerminal(System.out, System.in);
		}
	}
	public static final Terminal TERMINAL;

	public abstract String readLine();
	public abstract String readLine(String format, Object... args);
	public abstract String readPassword();
	public abstract String readPassword(String format, Object... args);
	
	public abstract void printf(String format, Object... args);
	public abstract void nextLine();
}
