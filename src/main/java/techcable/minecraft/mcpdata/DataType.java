package techcable.minecraft.mcpdata;

import lombok.*;

public enum DataType {
	CLASS,
	FIELD,
	METHOD,
	PARAMETER;

	public static boolean isDataTypeDeterminer(String s) {
		return getDataTypeFromDeterminer(s) != null;
	}
	
	public static DataType getDataTypeFromDeterminer(String determiner) {
		if (!determiner.matches("+++*+++")) return null;
		if (isClass(determiner)) {
			return CLASS;
		} else if (isField(determiner)) {
			return FIELD;
		} else if (isMethod(determiner)) {
			return METHOD;
		} else if (isParamater(determiner)) {
			return PARAMETER;
		} else {
			return null;
		}
	}

	private static boolean isField(String s) {
		return s.equals("FIELDS");
	}
	
	private static boolean isClass(String s) {
		return s.contains("CLASSES");
	}
	
	public static boolean isParamater(String s) {
		return s.contains("PARAMS");
	}
	
	public static boolean isMethod(String s) {
		return s.contains("METHODS");
	}
}