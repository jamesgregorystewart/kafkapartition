package Models;

import java.util.Date;
import java.util.Random;

public class KafkaMessage {

    private byte [] content = new byte[100];
    private final long sequenceNum;
    private final Date timestamp = new Date(); // initialized to the millisecond it was allocated at

    public KafkaMessage(long sequenceNum) {
        new Random().nextBytes(content); //Generates random bytes and places them into a user-supplied byte array
        this.sequenceNum = sequenceNum;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public long getSequenceNum() {
        return sequenceNum;
    }

    public byte[] getContent() {
        return content;
    }
}
