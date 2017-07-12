package com.cmb.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;


public class ZKClient implements Runnable{
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("server start...");
		ZKClient client = new ZKClient();
		Thread thread = new Thread(client);
		thread.start();
		while(true) {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(1000*10);
		}
		
		
	}

	@Override
	public void run() {
		
		try {
			ZooKeeper zKeeper = new ZooKeeper("119.23.68.133:2181", 3000, new Watcher() {

				@Override
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					System.out.println(event.getType());
				}
			});
			zKeeper.exists("/testRootPath", true);
			zKeeper.getChildren("/testRootPath", true);
			
			System.out.println(Thread.currentThread().getName()); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
