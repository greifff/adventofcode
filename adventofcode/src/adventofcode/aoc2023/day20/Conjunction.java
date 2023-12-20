package adventofcode.aoc2023.day20;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Conjunction implements CommunicationModule {

	Map<CommunicationModule, Boolean> lastPulses = new HashMap<>();
	String name;
	List<CommunicationModule> receivers = new LinkedList<>();
	long lowPulses;
	long highPulses;
	boolean toTransmit;

	public Conjunction(String realname) {
		name = realname;
	}

	@Override
	public void receive(CommunicationModule from, boolean pulse) {
//		System.out.println("& " + from.getName() + " -" + (pulse ? "high" : "low") + "-> " + name);
		lastPulses.put(from, pulse);
	}

	@Override
	public void process() {
		toTransmit = !lastPulses.values().stream().reduce((a, b) -> a && b).orElse(true);
	}

	@Override
	public boolean send() {
		receivers.forEach(r -> r.receive(this, toTransmit));
		if (toTransmit) {
			highPulses += receivers.size();
		} else {
			lowPulses += receivers.size();
		}
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addReceiver(CommunicationModule receiver) {
		receivers.add(receiver);
	}

	@Override
	public void addSender(CommunicationModule sender) {
		lastPulses.put(sender, false);
	}

	@Override
	public long getLowPulsesSend() {
		return lowPulses;
	}

	@Override
	public long getHighPulsesSend() {
		return highPulses;
	}

	@Override
	public List<CommunicationModule> getReceivers() {
		return receivers;
	}

	@Override
	public void resetCounter() {
		highPulses = 0;
	}
}
