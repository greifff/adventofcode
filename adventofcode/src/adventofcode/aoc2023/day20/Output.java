package adventofcode.aoc2023.day20;

import java.util.Collections;
import java.util.List;

public class Output implements CommunicationModule {

	int lowPulses;

	@Override
	public String getName() {
		return "output";
	}

	@Override
	public void receive(CommunicationModule from, boolean pulse) {
//		System.out.println("o " + from.getName() + " -" + (pulse ? "high" : "low") + "-> output");
		if (!pulse)
			lowPulses++;

	}

	@Override
	public void process() {

	}

	@Override
	public boolean send() {
		return false;
	}

	@Override
	public void addReceiver(CommunicationModule receiver) {
		// do nothing

	}

	@Override
	public void addSender(CommunicationModule sender) {
		// do nothing

	}

	@Override
	public long getLowPulsesSend() {
		return 0;
	}

	@Override
	public long getHighPulsesSend() {
		return 0;
	}

	@Override
	public List<CommunicationModule> getReceivers() {
		return Collections.emptyList();
	}

	@Override
	public void resetCounter() {
		// TODO Auto-generated method stub

	}
}
