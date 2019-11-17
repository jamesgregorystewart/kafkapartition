package Actors;

import Models.KafkaMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.Properties;

public class KPProducer extends Thread {

    protected static final Logger logger = Logger.getLogger(KPProducer.class);

    private Producer<String, String> producer;
    private MessageGenerator generator;

    public KPProducer(MessageGenerator generator) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
        this.generator = generator;
    }

    @Override
    public void run() {

        // To start off each producer will iterate 100 times and fetch new messages from the queue
        // Will convert this to loop endlessly until some input is provided

        for (int i = 0; i < 100; i++) {
            KafkaMessage message = generator.getMessage();
            producer.send(new ProducerRecord<Long, KafkaMessage>("main-topic", message));
        }

        producer.flush();
        producer.close();
    }


}
