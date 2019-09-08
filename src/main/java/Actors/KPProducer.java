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


    /*
     *
     * This producer uses a transaction which I may want to toss as I want messages to be sent asynchronously
     *
     * Perhaps the batches will each be individual transactions and transaction ids will be made dynamically? Investage further
     *
     * */

    @Override
    public void run() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("transactional.id", "my-transactional-id");
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());

        producer.initTransactions();

        try {
            int i = 0;
            while(true) {
                producer.beginTransaction();
                producer.send(new ProducerRecord<>("main-topic", Integer.toString(i), Integer.toString(i)));
                producer.commitTransaction();
                if (i % 100 == 1) Thread.sleep(1000);
            }
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        } catch (InterruptedException e) {
            logger.warn("Producer thread interrupted");
        }
        producer.close();
    }

    public void runProducer() throws Exception {
        thread.start();
    }

}
