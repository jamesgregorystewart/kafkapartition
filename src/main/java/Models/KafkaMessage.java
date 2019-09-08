package Models;

import java.util.Date;
import java.util.Random;

public class KafkaMessage {

    private byte [] content = new byte[1000];
    private final long sequenceNum;
    private final Date timestamp = new Date();


    public KafkaMessage(long sequenceNum) {
        new Random().nextBytes(content);
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
