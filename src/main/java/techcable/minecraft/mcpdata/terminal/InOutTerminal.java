package techcable.minecraft.mcpdata.terminal;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class InOutTerminal extends Terminal {
	public InOutTerminal(PrintStream out, InputStream in) {
		this(out, new Scanner(in));
	}
	private final PrintStream out;
	private final Scanner in;
	@Override
	public String readLine() {
		return in.nextLine();
	}

	@Override
	public String readLine(String format, Object... args) {
		printf(format, args);
		return readLine();
	}

	@Override
	public String readPassword() {
		return readLine();
	}

	@Override
	public String readPassword(String format, Object... args) {
		return readLine(format, args);
	}

	@Override
	public void printf(String format, Object... args) {
		out.printf(format, args);
	}

	@Override
	public void nextLine() {
		out.println();
	}
	
}
