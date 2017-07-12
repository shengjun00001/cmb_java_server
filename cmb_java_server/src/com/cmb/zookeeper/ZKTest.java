package com.cmb.zookeeper;

import java.awt.List;
import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZKTest implements Runnable{

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("server start...");
		ZKTest client = new ZKTest();
		Thread thread = new Thread(client);
		thread.start();
		
		while(true) {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(1000*10);
		}
	}

	@Override
	public void run() {
		ZooKeeper zKeeper = null;
		try {
			zKeeper = new ZooKeeper("119.23.68.133:2181", 3000, new Watcher() {

				@Override
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					System.out.println(event.getType());

				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String root = "/testRootPath";
		String child = root+"/child";
		while (true) {
			try {
				Stat stat = zKeeper.exists(root, true);
				if (stat != null) {
					System.out.println("/testRootPath stat exist");
					java.util.List<String> childs = zKeeper.getChildren(root, false);
					for (String string : childs) {
						zKeeper.delete(root+"/"+string, -1);
					}
					zKeeper.delete("/testRootPath", stat.getVersion());
				}
				zKeeper.create(root, "/testRootPath".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				zKeeper.create(child, "childData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
				Thread.sleep(1000*5);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
	}
	
}
