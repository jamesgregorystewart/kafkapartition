package Common;

import Models.KafkaMessage;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaMessageDeserializer implements org.apache.kafka.common.serialization.Deserializer {

    @Override
    public KafkaMessage deserialize(String s, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        KafkaMessage message = null;
        try {
            message = mapper.readValue(data, KafkaMessage.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
