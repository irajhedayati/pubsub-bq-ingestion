package ca.dataedu.pubsub;

import ca.dataedu.EventOuterClass;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PublishProto {

    public static void main(String[] args) throws IOException, InterruptedException {
        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(TopicName.of("dataedu-web", "proto_events")).build();

            for (int i = 0; i < 10; i++) {
                EventOuterClass.Event event = EventOuterClass.Event.newBuilder()
                        .setId("New V1 events after schema evolution")
                        .setUrl("http://www.dataedu.ca")
                        .setTimestamp(System.currentTimeMillis())
                        .setUserId(i)
                        .build();
                PubsubMessage message = PubsubMessage.newBuilder().setData(event.toByteString()).build();
                publisher.publish(message);
            }
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }

}
