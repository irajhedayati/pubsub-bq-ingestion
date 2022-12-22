package ca.dataedu.pubsub;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PublishJson {

    public static void main(String[] args) throws IOException, InterruptedException {

        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(TopicName.of("dataedu-web", "json_events")).build();

            for (int i = 0; i < 10; i++) {
                String message = "{\"key1\":\"value" + i + "\"}";
                PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(message)).build();
                publisher.publish(pubsubMessage);
            }

        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }
}
