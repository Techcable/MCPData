package techcable.minecraft.mcpdata.terminal;

import java.io.Console;

import lombok.*;
import lombok.experimental.Delegate;

@Getter
@RequiredArgsConstructor
public class ConsoleTerminal extends Terminal {
	private final Console backing;

	public boolean equals(Object obj) {
		return backing.equals(obj);
	}
	
	public void printf(String format, Object... args) {
		backing.printf(format, args);
	}

	public String readLine(String fmt, Object... args) {
		return backing.readLine(fmt, args);
	}

	public String readLine() {
		return backing.readLine();
	}

	public String readPassword() {
		return new String(backing.readPassword());
	}

	public String readPassword(String format, Object... args) {
		return new String(backing.readPassword(format, args));
	}
	public void nextLine() {
		printf(System.lineSeparator());
	}
}
