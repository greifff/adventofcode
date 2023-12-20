package adventofcode.aoc2023.day20;

import java.util.LinkedList;
import java.util.List;

public class Broadcaster implements CommunicationModule {
	List<CommunicationModule> receivers = new LinkedList<>();
	long lowPulses;
	long highPulses;

	boolean lastPulse;

	@Override
	public String getName() {
		return "broadcaster";
	}

	@Override
	public void receive(CommunicationModule from, boolean pulse) {
		lastPulse = pulse;
	}

	@Override
	public boolean send() {
		receivers.forEach(r -> r.receive(this, lastPulse));
		if (lastPulse) {
			highPulses += receivers.size();
		} else {
			lowPulses += receivers.size();
		}
		return true;
	}

	@Override
	public void process() {

	}

	@Override
	public void addReceiver(CommunicationModule receiver) {
		receivers.add(receiver);
	}

	@Override
	public void addSender(CommunicationModule sender) {
		// do nothing
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
		// TODO Auto-generated method stub

	}
}
