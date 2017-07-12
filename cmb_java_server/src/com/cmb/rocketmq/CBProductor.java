package com.cmb.rocketmq;

import java.util.List;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.cmb.utils.CBLogUtils;

public class CBProductor {

	public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
            DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("119.23.68.133:9876");
        //Launch the instance.
        producer.start();
        CBLogUtils.Log("begin sendMessage");
        
        MessageQueueSelector messageQueue = new MessageQueueSelector() {
			@Override
			public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
				// TODO Auto-generated method stub
		        CBLogUtils.Log("arg: "+arg);
		        CBLogUtils.Log("mqs: "+mqs.size());

				return mqs.get(0);
			}
		};
        for (int i = 0; i < 10; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ " +
                    i).getBytes("utf8") /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            //SendResult sendResult = producer.send(msg);
            producer.send(msg, messageQueue, 1);
            
           // Thread.sleep(1000);
        }
        CBLogUtils.Log("end sendMessage");

        //Shut down once the producer instance is not longer in use.
        Thread.sleep(5000);
        producer.shutdown();
    }
	
}
