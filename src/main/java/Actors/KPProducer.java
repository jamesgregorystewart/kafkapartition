package Actors;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.Properties;

public class KPProducer implements Runnable{

    protected static final Logger logger = Logger.getLogger(KPProducer.class);
    Thread thread = new Thread(this::run);

    @Override
    public void run() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());

        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>("main-topic", Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
    }

    public void runProducer() throws Exception {
        thread.start();
    }

}
