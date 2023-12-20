package adventofcode.aoc2023.day20;

import java.util.Collections;
import java.util.List;

public class Button implements CommunicationModule {

	@Override
	public String getName() {
		return "button";
	}

	@Override
	public void receive(CommunicationModule from, boolean pulse) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void addSender(CommunicationModule sender) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getLowPulsesSend() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getHighPulsesSend() {
		// TODO Auto-generated method stub
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
