package techcable.minecraft.mcpdata.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.pircbotx.Channel;
import org.pircbotx.DccChat;
import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.IncomingChatRequestEvent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;

import techcable.minecraft.mcpdata.DataType;
import techcable.minecraft.mcpdata.MCPData;

import static techcable.minecraft.mcpdata.MCPData.error;

import techcable.minecraft.mcpdata.event.ClassDataReceiveEvent;
import techcable.minecraft.mcpdata.event.FieldDataReceiveEvent;
import techcable.minecraft.mcpdata.event.MethodDataReceiveEvent;
import techcable.minecraft.mcpdata.parser.DataParser;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class MCPDataReader extends ListenerAdapter<MCPDataBot> {
	private final MCPDataBot bot;
	private final Channel mcpChannel;
	private final User mcpBot;
	private DccChat chat;
	private boolean done = false;
	@Override
	public void onIncomingChatRequest(IncomingChatRequestEvent<MCPDataBot> event) throws Exception {
		if (event instanceof IncomingChatRequestEvent) {
			IncomingChatRequestEvent<MCPDataBot> request = (IncomingChatRequestEvent<MCPDataBot>) event;
			setChat(request.getChat());
			MCPDataBot bot = request.getBot();
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					read();
				}
			});
			t.setDaemon(true);
			t.setName("Data reader");
			t.start();
		}
	}
	
	public void read() {
		try {
			chat.sendLine("findall .");
			beginNewType(chat.readLine());
		} catch (IOException ex) {
			error(ex);
		}
	} 
	
	@SuppressWarnings("incomplete-switch")
	public void beginNewType(String line) throws IOException {
		switch (DataType.getDataTypeFromDeterminer(line)) {
		case CLASS : readClasses();
		case METHOD : readMethods();
		case FIELD : readFields();
		}
	}
	
	private void readFields() throws IOException {
		String line;
		while (!DataType.isDataTypeDeterminer((line = chat.readLine()))) {
			String[] mapping = DataParser.parseField(line);
			fire(new FieldDataReceiveEvent(mapping));
		}
		beginNewType(line);
	}

	private void readMethods() throws IOException {
		String line;
		while (!DataType.isDataTypeDeterminer((line = chat.readLine()))) {
			String[] mapping = DataParser.parseField(line);
			fire(new MethodDataReceiveEvent(mapping));
		}
		beginNewType(line);
	}

	public void readClasses() throws IOException {
		String line;
		while (!DataType.isDataTypeDeterminer((line = chat.readLine()))) {
			String[] mapping = DataParser.parseClass(line);
			fire(new ClassDataReceiveEvent(mapping));
		}
		beginNewType(line);
	}
	
	public void fire(Object event) {
		MCPData.EVENT_BUS.post(event);
	}
}
