package adventofcode.aoc2023.day20;

import java.util.LinkedList;
import java.util.List;

class FlipFlop implements CommunicationModule {

	boolean state;
	String name;
	long lowPulses;
	long highPulses;

	boolean receivedLow;
	Boolean toSend;

	List<CommunicationModule> receivers = new LinkedList<>();

	public FlipFlop(String realname) {
		name = realname;
	}

	@Override
	public void receive(CommunicationModule from, boolean pulse) {
//		System.out.println("% " + from.getName() + " -" + (pulse ? "high" : "low") + "-> " + name);

		if (pulse) {
			return;
		}
		receivedLow = true;
	}

	@Override
	public void process() {
		if (receivedLow) {
			state = !state;
			toSend = state;
		}
		receivedLow = false;
	}

	@Override
	public boolean send() {
		if (toSend == null) {
			return false;
		}
		receivers.forEach(r -> r.receive(this, toSend));
		if (toSend) {
			highPulses += receivers.size();
		} else {
			lowPulses += receivers.size();
		}
		toSend = null;
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