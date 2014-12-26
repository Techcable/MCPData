package techcable.minecraft.mcpdata.parser.writer;

import java.io.FileNotFoundException;
import java.io.IOException;

import techcable.minecraft.mcpdata.ClassData;
import techcable.minecraft.mcpdata.FieldData;
import techcable.minecraft.mcpdata.MethodData;

public interface MappingWriter {
	public void writeClasses(ClassData classData) throws IOException;
	public void writeMethods(MethodData methodData) throws IOException;
	public void writeFields(FieldData fieldData) throws IOException;
}
