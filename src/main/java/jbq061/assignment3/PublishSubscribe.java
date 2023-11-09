package jbq061.assignment3;

import java.util.*;

public class PublishSubscribe {
	Map<String, List<Subscriber>> channels = new HashMap<>();

	public void createChannel(String channelKey) {
		channels.put(channelKey, new ArrayList<>());
	}

	public void subscribe(String channelKey, Subscriber subscriber) {
		if (channels.containsKey(channelKey)) {
			channels.get(channelKey).add(subscriber);
			return;
		}
		createChannel(channelKey);
		subscribe(channelKey, subscriber);
	}

	public void publish(String channelKey, Object changedState) {
		if (channels.containsKey(channelKey)) {
			channels.get(channelKey).forEach(subscriber -> subscriber.receiveNotification(channelKey, changedState));
		}
	}
}
