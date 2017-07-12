package com.cmb.zk;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

public class Client implements Runnable{

	private ZooKeeper zooKeeper;
	
	public static void main(String[] args) throws Exception {
		Client server = new Client();
		try {
			server.zooKeeper = new ZooKeeper("119.23.68.133:2181", 3000, null);
			ZookeeperWatch watcher = new ZookeeperWatch();
			server.zooKeeper.register(watcher);
			server.zooKeeper.getChildren("/dubbo", true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
			Thread.sleep(1000*10);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
