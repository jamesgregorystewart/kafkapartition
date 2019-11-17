package Actors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KPConsumer extends Thread {

    protected static final Logger logger = Logger.getLogger(KPConsumer.class);


    /*
     *
     * Uses automatic offset -> will need to change that to manual offset to ack messages as consumed ~after~ they are inserted into
     *  the database
     *
     * */

    @Override
    public void run() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "main-group");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("main-topic"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Message Consumed: offset =" + record.offset() + ", key = " + record.key() + ", value = " + record.value());
                logger.info("Message Consumed: offset = "+record.offset()+", key = "+record.key()+", value = "+record.value());
            }
        }
    }

}
