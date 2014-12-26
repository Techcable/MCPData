package techcable.minecraft.mcpdata;

import com.google.common.eventbus.Subscribe;

import techcable.minecraft.mcpdata.event.ClassDataReceiveEvent;
import techcable.minecraft.mcpdata.event.FieldDataReceiveEvent;
import techcable.minecraft.mcpdata.event.MethodDataReceiveEvent;

import lombok.*;

@Getter
public class DataGatherer {
	public static final long MAX_WAIT_TIME = 1000 * 60; //Max time to wait between gatherings in milliseconds - 1 second
	private FieldData fields = new FieldData();
	private MethodData methods = new MethodData();
	private ClassData classes = new ClassData();
	
	private long lastGather;
	@Subscribe
	public void onMethodReceive(MethodDataReceiveEvent event) {
		lastGather = System.currentTimeMillis();
		methods.addMapping(event.getMapping());
	}
	@Subscribe
	public void onClassReceive(ClassDataReceiveEvent event) {
		lastGather = System.currentTimeMillis();
		classes.addMapping(event.getMapping());
	}
	@Subscribe
	public void onFieldReceive(FieldDataReceiveEvent event) {
		lastGather = System.currentTimeMillis();
		fields.addMapping(event.getMapping());
	}
	
	public boolean isDone() {
		if (lastGather == 0) return false;
		long waitTime = System.currentTimeMillis() - lastGather;
		return waitTime > MAX_WAIT_TIME;
	}
}