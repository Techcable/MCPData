package techcable.minecraft.mcpdata;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import lombok.*;

@Getter
@NoArgsConstructor
public class ClassData {
	public ClassData(String[][] mappings) {
		for (String[] mapping : mappings) {
			addMapping(mapping);
		}
	}
	private final BiMap<String, String> mojangToSrg = HashBiMap.create();
	private Cache<String, String> qualifyCache = CacheBuilder.newBuilder().expireAfterAccess(12, TimeUnit.MINUTES).maximumSize(1000).build();
	
	public void addMapping(String[] mapping) {
		mojangToSrg.put(mapping[0], mapping[1]);
	}
	
	public String fullyQualify(String className) {
		if (qualifyCache.getIfPresent(className) != null) {
			return qualifyCache.getIfPresent(className);
		}
		String pack = null;
		for (String s : mojangToSrg.values()) {
			if (s.endsWith(className)) pack = s;
		}
		Preconditions.checkNotNull(pack, "Couldn't find package for srg class %s", className);
		String fullyQualified = pack + "/" + className;
		qualifyCache.put(className, fullyQualified);
		return fullyQualified;
	}
}