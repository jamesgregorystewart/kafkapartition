package Common;

public class Resources extends ResourceLoader {

    public Resources(){
        super();
    }

    public int getFrequency() {return frequency;}

    public String [] getHost() {return hosts;}

    public String getProducerAcks() {return producerAcks;}

    public String getProducerKeySerializer() {return producerKeySerializer;}

    public String getProducerValueSerializer() {return producerValueSerializer;}

}
