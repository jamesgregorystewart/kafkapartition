package Actors;

import Models.KafkaMessage;
import kafka.Kafka;
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
     * Uses automatic offset -> will need to change that to manual offset to ack messages as consumed ~after~ they are inserted into
     *  the database
     * */

    @Override
    public void run() {
        //TODO: move these properties to the resources and config file
        //TODO: put in the default key.deserializer and value.deserializer
        //TODO: the consumer isn't receiving records
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "main-group");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("key.deserializer", "");
        props.setProperty("value.deserializer", "");
        props.setProperty("auto.commit.interval.ms", "1000");
        KafkaConsumer<Long, KafkaMessage> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("main-topic"));
        while (true) {
            ConsumerRecords<Long, KafkaMessage> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<Long, KafkaMessage> record : records) {
                System.out.println("Message Consumed: offset =" + record.offset() + ", key = " + record.key() + ", value = " + record.value());
            }
        }
    }

}
