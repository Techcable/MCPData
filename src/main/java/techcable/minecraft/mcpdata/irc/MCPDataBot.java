package techcable.minecraft.mcpdata.irc;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;

import static techcable.minecraft.mcpdata.MCPData.error;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class MCPDataBot extends PircBotX {
	private final String mcpBot, botName, minecraftVersion, mcpBotChannel;
	public void start(URI ircNet, String password) {
		setName(botName);
		connect(ircNet, password);
		if (!channelExists(getMcpBotChannel())) {
			error("Mcp Bot Channel %s doesn't exist", getMcpBotChannel());
		}
		joinChannel(getMcpBotChannel());
		Channel channel = getChannel(getMcpBotChannel());
		User mcpBot = getUser(getMcpBot());
		if (!channel.getUsers().contains(mcpBot)) {
			error("User %s doesn't exist");
		}
		mcpBot.sendMessage("dcc");
		MCPDataReader reader = new MCPDataReader(this, channel, mcpBot);
		getListenerManager().addListener(reader);
	}
	
	public void connect(URI ircNet, String password) {
		int port = 6667;
		if (ircNet.getPort() != -1) {
			port = ircNet.getPort();
		}
		if (password != null && password.isEmpty()) {
			password = null;
		}
		try {
			connect(ircNet.getHost(), port, password);
		} catch (NickAlreadyInUseException e) {
			error("Nickname %s is already in use", getBotName());
		} catch (IrcException ex) {
			error("Couldn't join server: %s", ex.getMessage());
		} catch (IOException e) {
			error(e);
		}
	}
}
