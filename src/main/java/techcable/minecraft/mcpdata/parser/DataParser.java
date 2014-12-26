package techcable.minecraft.mcpdata.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import techcable.minecraft.mcpdata.ClassData;

import lombok.*;

@Getter
public class DataParser {
	private DataParser() {}
	public static final Pattern CLASS_DATA = Pattern.compile("(\\w+) => (\\S+)");
	public static final Pattern METHOD_DATA = Pattern.compile("(\\S+) => ([\\w\\.]+)(\\S+) \\[ (\\S+) \\]");
	public static final Pattern FIELD_DATA = Pattern.compile("(\\S+) => (\\S+) \\[ (\\S+) \\]");


	public static String[] parseClass(String classData) {
		Matcher matcher = CLASS_DATA.matcher(classData);
		String[] mapping = new String[] {matcher.group(1), matcher.group(2)};
		return mapping;
	}
	public static String[] parseMethod(String methodData) {
		Matcher matcher = METHOD_DATA.matcher(methodData);
		String[] mapping = new String[] {matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3)};
		return mapping;
	}
	public static String[] parseField(String fieldData) {
		Matcher matcher = FIELD_DATA.matcher(fieldData);
		String[] mapping = new String[] {matcher.group(1), matcher.group(2), matcher.group(3)};
		return mapping;
	}
}
