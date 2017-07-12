package com.cmb.zk;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.cmb.utils.CBLogUtils;

public class ZKLock implements Watcher{

	private CountDownLatch countDownLatch = new CountDownLatch(0);
	private ZooKeeper zk;
	
	@Override
	public void process(WatchedEvent event) {
		CBLogUtils.Log(event);
	}
	
	public static void main(String[] args) throws Exception{
		ZKLock lock = new ZKLock();
		lock.createConnection("119.23.68.133:2181", 3000);
		lock.doSomeThing();
	}
	
	public void createConnection(String connection,int outTime) throws Exception {
		CBLogUtils.Log("connect...");
		zk = new ZooKeeper(connection, outTime, this);
		countDownLatch.await();
		CBLogUtils.Log("connect success");
	}
	
	private void doSomeThing() {
		BaseDistributedLock lock = new BaseDistributedLock(zk, "/testlock", "lockName");
		try {
			Thread.sleep(1000*10);
			CBLogUtils.Log("begin lock");
			lock.lock2();
			CBLogUtils.Log("get a lock");
			Thread.sleep(1000*30);
			lock.releaseLock();
			CBLogUtils.Log("release lock");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
