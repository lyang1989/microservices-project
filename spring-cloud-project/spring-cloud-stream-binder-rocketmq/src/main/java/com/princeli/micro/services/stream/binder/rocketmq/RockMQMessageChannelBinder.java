package com.princeli.micro.services.stream.binder.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-04-02 14:00
 **/
public class RockMQMessageChannelBinder implements Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

    private static final String GROUP = "TEST_GROUP";

    private static final String TOPIC = "TEST_TOPIC";

    private static final String TAG = "TEST_TAG";

    private static final String NAME_ADDRESS = "localhost:9876";

    @Override
    public Binding<MessageChannel> bindConsumer(String name, String group, MessageChannel inputChannel, ConsumerProperties consumerProperties) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP);
        consumer.setNamesrvAddr(NAME_ADDRESS);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        try {
            consumer.subscribe(TOPIC, TAG);


             consumer.registerMessageListener(new MessageListenerConcurrently() {

                AtomicLong consumeTimes = new AtomicLong(0);
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                           ConsumeConcurrentlyContext context) {

                    System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");

                    msgs.forEach(msg ->{
                        byte[] body  = msg.getBody();
                        //发送消息到消息管道
                        inputChannel.send(new GenericMessage<Object>(body));
                    });


                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            consumer.start();

        }catch (Exception e){
            e.getMessage();
        }

        return () ->{
            //todo
            System.out.println("consumer shutdown");
            consumer.shutdown();
        };
    }

    /**
     * 发送消息
     * @param name
     * @param outputChannel
     * @param producerProperties
     * @return
     */
    @Override
    public Binding<MessageChannel> bindProducer(String name, MessageChannel outputChannel, ProducerProperties producerProperties) {

        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(GROUP);
        // Specify name server addresses.
        producer.setNamesrvAddr(NAME_ADDRESS);
        //Launch the instance.
        try {
            producer.start();

            //消息到达回调
            SubscribableChannel subscribableChannel = (SubscribableChannel)outputChannel;
            subscribableChannel.subscribe(message -> {
                //消息主体
                Object messageBody = message.getPayload();
                Message mqMessage = new Message();
                mqMessage.setTopic(TOPIC);
                mqMessage.setTags(TAG);

                try {
                    mqMessage.setBody(serialize(messageBody));
                    SendResult sendResult = producer.send(mqMessage);
                    System.out.printf("消息发送: %s%n", sendResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        return () ->{

        };
    }



    private byte[] serialize(Object serializable) throws IOException {
        if (serializable instanceof byte[]){
            return (byte[])serializable;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        //通过java序列化将Object写入字节流
        objectOutputStream.writeObject(serializable);
        //返回字节数组
        return outputStream.toByteArray();
    }
}
