package techcable.minecraft.mcpdata.parser.writer;

import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import au.com.bytecode.opencsv.CSVWriter;

import techcable.minecraft.mcpdata.ClassData;
import techcable.minecraft.mcpdata.FieldData;
import techcable.minecraft.mcpdata.MethodData;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class FieldCsvWriter implements MappingWriter {
	private final File fieldFile;
	@Override
	public void writeClasses(ClassData classData) {}

	@Override
	public void writeMethods(MethodData methodData) {}

	@Override
	public void writeFields(FieldData fieldData) throws IOException {
		CSVWriter writer = new CSVWriter(Files.newWriter(getFieldFile(), Charsets.UTF_8));
		try {
			writer.writeNext(new String[] {"searge", "name"});
			for (Entry<String, String> field : fieldData.getSrgToMcp().entrySet()) {
				writer.writeNext(new String[] {field.getKey(), field.getValue()});
			}
			if (writer.checkError()) throw new IOException("Unknown error");
		} finally {
			writer.close();	
		}
	}

}
