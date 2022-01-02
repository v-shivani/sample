package com.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer {

    public static void main(String args[])
    {

        Logger logger = LoggerFactory.getLogger(Producer.class);

        Properties prop =new Properties();
      prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
      prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
      prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

      KafkaProducer producer = new KafkaProducer(prop);

      ProducerRecord<String,String> record = new ProducerRecord<String,String>("first","true");

      producer.send(record, new Callback() {
          @Override
          public void onCompletion(RecordMetadata recordMetadata, Exception e)
          {

              if(e == null)
              {
                  logger.info("\n Received record metadata. \n" +
                          "Topic: "+recordMetadata.topic() +", partition:"+recordMetadata.partition() +","
                  +"offset:"+recordMetadata.offset()+" "+"timestamp:"+recordMetadata.timestamp()+ "\n");
              }
              else
              {
                  logger.error("error occured",e);
                 // System.out.println("error occured"+e);
              }


          }
      });
      System.out.println(record.topic());
      System.out.println(record.value());
      producer.flush();
      producer.close();
    }
}
