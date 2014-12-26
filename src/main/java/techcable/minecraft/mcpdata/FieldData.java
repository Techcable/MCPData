package techcable.minecraft.mcpdata;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import lombok.*;

@Getter
@NoArgsConstructor
public class FieldData {
	public FieldData(String[][] mappings) {
		for (String[] mapping : mappings) {
			addMapping(mapping);
		}
	}
	private final BiMap<String, String> mojangToSrg = HashBiMap.create();
	private final BiMap<String, String> srgToMcp = HashBiMap.create();
	
	public void addMapping(String[] mapping) {
		mojangToSrg.put(mapping[0], mapping[2]);
		srgToMcp.put(mapping[2], mapping[1]);
	}
}
