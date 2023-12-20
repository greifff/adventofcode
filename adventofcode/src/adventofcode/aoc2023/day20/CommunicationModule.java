package adventofcode.aoc2023.day20;

import java.util.List;

interface CommunicationModule {
	String getName();

	void receive(CommunicationModule from, boolean pulse);

	void process();

	boolean send();

	void addReceiver(CommunicationModule receiver);

	void addSender(CommunicationModule sender);

	long getLowPulsesSend();

	long getHighPulsesSend();

	List<CommunicationModule> getReceivers();

	void resetCounter();
}