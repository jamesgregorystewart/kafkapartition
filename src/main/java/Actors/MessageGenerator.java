package Actors;

/*
* This class will create Kafka Messages that will then be picked up by the producers and written out
*
 *
 * Approach:
 * - Will fill a blocking queue of kafka messages that need to be sent
 * - Producers will pick messages from this blocking queue to send to consumer group
* */

import Models.KafkaMessage;

import java.util.concurrent.ArrayBlockingQueue;

public class MessageGenerator extends Thread {

    private volatile int sequenceNumber = 0;
    private ArrayBlockingQueue<KafkaMessage> queue = new ArrayBlockingQueue<>(1000); //limited to 1000 messages
    private final Object lock = new Object();

    @Override
    public void run() {
        while (true) {
            while (queue.size() == 1000) {
                try {
                    Thread.sleep(100);
                } catch(InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            synchronized (lock) {
                queue.add(new KafkaMessage(sequenceNumber++));
            }
        }
    }

    //both of the below methods share the same intrinsic class lock like they are supposed to

    // I believe this needs to be synchronized but I would like to test without it
    public synchronized KafkaMessage getMessage() {
        return queue.poll();
    }

    // I believe this needs to be synchronized but look for an async way to do this so as to not impact performance as much
    public synchronized boolean hasMessages() {
        return !queue.isEmpty();
    }
}
