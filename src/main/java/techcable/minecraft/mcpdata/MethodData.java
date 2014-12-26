package techcable.minecraft.mcpdata;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import lombok.*;

@Getter
@NoArgsConstructor
public class MethodData {
	public MethodData(String[][] mappings) {
		for (String[] mapping : mappings) {
			addMapping(mapping);
		}
	}
	private final BiMap<String, String> mojangToSrg = HashBiMap.create();
	private final BiMap<String, String> srgToMcp = HashBiMap.create();
	private final Map<String, String> srgToData = new HashMap<>();
	
	public void addMapping(String[] mapping) {
		mojangToSrg.put(mapping[0], mapping[2]);
		srgToMcp.put(mapping[2], mapping[1]);
		srgToData.put(mapping[2], mapping[3]);
	}
}
