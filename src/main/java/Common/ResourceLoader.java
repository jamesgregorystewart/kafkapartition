package Common;

/*
* This classes exists to serve resources
* */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class ResourceLoader {

    protected int frequency;
    protected String [] hosts;
    protected String producerAcks;
    protected String producerKeySerializer;
    protected String producerValueSerializer;


    public  ResourceLoader() {
        this.frequency = getProducerFrequencyProp();
        this.hosts = getHostProp();
        this.producerAcks = getProducerAcksProp();
        this.producerKeySerializer = getProducerKeySerializerProp();
        this.producerValueSerializer = getProducerValueSerializerProp();
    }

    private int getProducerFrequencyProp() {
        try {
            InputStream input = ResourceLoader.class.getResourceAsStream("kafkapartition.properties");
            Properties props = new Properties();

            props.load(input);
            return Integer.valueOf(props.getProperty("producer.frequency"))*1000;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 1000; //default
    }

    private String[] getHostProp() {
        try {
            InputStream input = ResourceLoader.class.getResourceAsStream("kafkapartition.properties");
            Properties props = new Properties();

            props.load(input);
            String hosts = props.getProperty("bootstrap.servers");
            String[] splitHosts = hosts.split(",");
            return splitHosts;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getProducerAcksProp() {
        try {
            InputStream input = ResourceLoader.class.getResourceAsStream("kafkapartition.properties");
            Properties props = new Properties();

            props.load(input);
            return String.valueOf(props.getProperty("acks"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getProducerKeySerializerProp() {
        try {
            InputStream input = ResourceLoader.class.getResourceAsStream("kafkapartition.properties");
            Properties props = new Properties();

            props.load(input);
            return String.valueOf(props.getProperty("key.serializer"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getProducerValueSerializerProp() {
        try {
            InputStream input = ResourceLoader.class.getResourceAsStream("kafkapartition.properties");
            Properties props = new Properties();

            props.load(input);
            return String.valueOf(props.getProperty("value.serializer"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
