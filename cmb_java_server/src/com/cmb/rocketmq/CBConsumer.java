package com.cmb.rocketmq;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.cmb.utils.CBLogUtils;

public class CBConsumer {
	

	public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setConsumeMessageBatchMaxSize(10);
        consumer.setNamesrvAddr("119.23.68.133:9876");

        consumer.subscribe("TopicTest", "TagA || TagC || TagD");

        OrderlyMessageListener listener = new OrderlyMessageListener();
       // ConcurrentlyMessageListener listener = new ConcurrentlyMessageListener();

        consumer.registerMessageListener(listener);

        consumer.start();

        System.out.printf("Consumer Started.%n");
        
    }
}

class OrderlyMessageListener implements MessageListenerOrderly{

	private AtomicInteger atomicInteger = new AtomicInteger(0);
	
	@Override
	public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext arg1) {
				
		CBLogUtils.Log("num: "+atomicInteger.getAndAdd(msgs.size())+ " msgs size: "+msgs.size());
		CBLogUtils.Log(msgs);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return ConsumeOrderlyStatus.SUCCESS;
   }

}

class ConcurrentlyMessageListener implements MessageListenerConcurrently{

	private AtomicInteger atomicInteger = new AtomicInteger(0);

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		CBLogUtils.Log("num: "+atomicInteger.getAndAdd(msgs.size())+ " msgs size: "+msgs.size());
		CBLogUtils.Log(msgs);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
	
}


