import Actors.KPConsumer;
import Actors.KPProducer;
import Actors.MessageGenerator;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;


import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Common.Resources;


public class Driver {

    private final static String TOPIC = "main-topic";
    private final static String BOOTSTRAP_SERVERS =  "localhost:9092,localhost:9093,localhost:9094";
    protected static final Logger logger = Logger.getLogger(Driver.class);

    Resources resources = new Resources();

    public static void main(String[] args) {

        MessageGenerator generator = new MessageGenerator();
        KPProducer producer = new KPProducer(generator);
        KPProducer producer1 = new KPProducer(generator);
        KPConsumer consumer = new KPConsumer();

        try {
            generator.start();
            producer.start();
            producer1.start();
            consumer.start();

            /*
            *  keep main thread asleep; checking if all the threads are alive every 5 seconds; and terminating them all if
                any one of them dies
            * */
            while (generator.isAlive() && producer.isAlive() && producer1.isAlive() && consumer.isAlive()) {
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // if any of them fail close them all out
            generator.interrupt();
            producer.interrupt();
            producer1.interrupt();
            consumer.interrupt();
        }

    }

    //this is working and successfully created maintopic
    //may want to move this to script so that it doesn't run every time
    private static void createTopic() {
        try {
            //move these properties to the properties file
            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            AdminClient kafkaAdminClient = KafkaAdminClient.create(properties);
            CreateTopicsResult result = kafkaAdminClient.createTopics(
                    Stream.of("main-topic").map(
                            name -> new NewTopic(name, 3, (short) 1)
                    ).collect(Collectors.toList())
            );
            result.all().get();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
