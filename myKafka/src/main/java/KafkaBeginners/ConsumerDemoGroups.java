package KafkaBeginners;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoGroups {
    public static void main(String[] args) {
        Logger logger= LoggerFactory.getLogger(ConsumerDemoGroups.class.getName());

        String bootStrapConfig="127.0.0.1:9092";
        String grpid="my_fifth_application";
        String topic="first_topic";
        Properties properties=new Properties();
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapConfig);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,grpid);

        KafkaConsumer<String,String> consumer=new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(topic));

        while(true)
        {
            ConsumerRecords<String,String> records=consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord record :records)
            {
                logger.info("Key "+record.key()+" Value "+record.value()+"\n");
                logger.info("Partition "+record.partition()+" Offset "+record.offset() );
            }
        }
    }
}
