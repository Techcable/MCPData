package techcable.minecraft.mcpdata.event;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class MethodDataReceiveEvent {
	private final String[] mapping;
	
	public String getMojang() {
		return mapping[0];
	}
	public String getSrg() {
		return mapping[2];
	}
	public String getMCP() {
		return mapping[1];
	}
	public String getData() {
		return mapping[4];
	}
}
