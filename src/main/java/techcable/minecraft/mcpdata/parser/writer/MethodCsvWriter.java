package techcable.minecraft.mcpdata.parser.writer;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import techcable.minecraft.mcpdata.ClassData;
import techcable.minecraft.mcpdata.FieldData;
import techcable.minecraft.mcpdata.MethodData;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class MethodCsvWriter implements MappingWriter {
	private File methodFile;
	@Override
	public void writeClasses(ClassData classData) throws IOException {
	}

	@Override
	public void writeMethods(MethodData methodData) throws IOException {
		CSVWriter writer = new CSVWriter(Files.newWriter(getMethodFile(), Charsets.UTF_8));
		try {
			writer.writeNext(new String[] {"searge", "name"});
			for (Entry<String, String> method : methodData.getSrgToMcp().entrySet()) {
				writer.writeNext(new String[] {method.getKey(), method.getValue()});
			}
			if (writer.checkError()) throw new IOException("Unknown error");
		} finally {
			writer.close();	
		}
	}

	@Override
	public void writeFields(FieldData fieldData) throws IOException {
	}

}
