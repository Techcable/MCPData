package techcable.minecraft.mcpdata.event;

import com.google.common.base.Preconditions;

import techcable.minecraft.mcpdata.DataType;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class ClassDataReceiveEvent {
	private final String[] mapping;
	
	public String getMojang() {
		return mapping[0];
	}
	public String getSrg() {
		return mapping[1];
	}
	public String getMcp() {
		return getSrg(); //Identical
	}
}
