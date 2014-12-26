package techcable.minecraft.mcpdata;

import java.net.URI;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.management.InstanceAlreadyExistsException;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;

import techcable.minecraft.mcpdata.irc.MCPDataBot;
import techcable.minecraft.mcpdata.terminal.Terminal;

import lombok.Getter;

@Getter
public class MCPData {
	public static final Pattern MINECRAFT_VERSION = Pattern.compile("\\d\\.\\d\\.\\d+");
	public static final Pattern IRC_CHANNEL = Pattern.compile("#\\S+");
	public static final Pattern IRC_USER = Pattern.compile("\\S+");
	public static final EventBus EVENT_BUS = new EventBus();
	public static void main(String[] args) {
		MCPDataOptions options = CliFactory.parseArguments(MCPDataOptions.class, args);
		getInstance().run(options);
	}
	
	public static interface MCPDataOptions {
		@Option(longName="minecraft-version", shortName="v", description="The version of minecraft to retreive mcp mappings for")
		public String getMinecraftVersion();
		@Option(longName="irc-network", shortName="i", description="The irc network to connect to", defaultValue="irc://irc.esper.net/")
		public String getIRCNetwork();
		@Option(shortName="u", description="The username for the irc bot")
		public String getUsername();
		@Option(shortName="c", description="The channel with mcp bot reborn", defaultValue="#mcpbot")
		public String getMcpBotChannel();
		@Option(shortName="n", description="The name of the bot to retreive data from", defaultValue="MCPBot_Reborn")
		public String getMcpBotName();
		@Option(shortName="h", description="Get help", helpRequest=true)
		public boolean getHelp();
	}
	
	private MCPDataBot bot;
	public void run(MCPDataOptions options) {
		//Ensure valid data
		Preconditions.checkArgument(matches(options.getMinecraftVersion(), MINECRAFT_VERSION), "%s is not a valid minecraft version", options.getMinecraftVersion());
		Preconditions.checkArgument(matches(options.getUsername(), IRC_USER), "%s is not a valid username", options.getUsername());
		Preconditions.checkArgument(matches(options.getMcpBotChannel(), IRC_CHANNEL), "%s is not a valid channel", options.getMcpBotChannel());
		Preconditions.checkArgument(matches(options.getMcpBotName(), IRC_USER), "%s is not a valid username", options.getMcpBotName());
		Preconditions.checkArgument(isValidURI(options.getIRCNetwork()), "$s is not a valid url", options.getIRCNetwork());
		URI ircNetwork = URI.create(options.getIRCNetwork());
		Preconditions.checkArgument(ircNetwork.getScheme() == "irc", "%s is not irc network", options.getIRCNetwork());
		
		//Initialize bot
		bot = new MCPDataBot(options.getMcpBotName(), options.getUsername(), options.getMinecraftVersion(), options.getMcpBotChannel());
		String pass = Terminal.TERMINAL.readPassword("Password for %s :", options.getUsername());
		bot.start(ircNetwork, pass);
	}
	private static MCPData instance;
	public static MCPData getInstance() {
		if (instance == null) {
			instance = new MCPData();
		}
		return instance;
	}
	
	public static boolean matches(String value, Pattern pattern) {
		return pattern.matcher(value).matches();
	}
	public static boolean isValidURI(String uri) {
		try {
			URI.create(uri);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	public static void error(String msg, Object... args) {
		Terminal.TERMINAL.printf(msg, args);
		System.exit(1);
	}
	public static void error(String msg, Exception ex) {
		error(msg + ": %s", ex.getMessage());
	}
	public static void error(Exception ex) {
		error(ex.getClass().getSimpleName(), ex);
	}
}
