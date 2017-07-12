package com.cmb.zk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Server {

	static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:Z");
	
	private ZooKeeper zooKeeper;

	public static void main(String[] args) throws Exception {
		
		Server server = new Server();
		try {
			server.zooKeeper = new ZooKeeper("119.23.68.133:2181", 3000, null);
			ZookeeperWatch watcher = new ZookeeperWatch();
			server.zooKeeper.register(watcher);
			//server.deleteAllNode("/");
			//server.deleteNode("/dubbo");
			server.deleteAllNode("/dubbo");
			Thread.sleep(1000*10);
			server.createNode("/dubbo", "dubboData".getBytes(), CreateMode.PERSISTENT);
			server.createNode("/dubbo/child1", "dubbo_child1_Data".getBytes(), CreateMode.PERSISTENT);
			Thread.sleep(1000*5);
			server.createNode("/dubbo/child2", "dubbo_child2_Data".getBytes(), CreateMode.PERSISTENT);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while (true) {
			Thread.sleep(1000*10);
		}
		
	}
	
	public static void Log(Object message) {
		System.out.println("["+format.format(new Date())+"] "+message);
	}
	
	public void createNode(String path,byte[] data,CreateMode createMode) throws Exception {
		Stat stat = zooKeeper.exists(path, false);
		if (stat == null) {
			zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, createMode);
			Server.Log("create node ["+path+"]");
		}
	}
	
	/**
	 * 删除所有子节点，包括本身节点
	 * */
	public void deleteAllNode(String path) throws Exception{
		Stat stat = zooKeeper.exists(path, true);
		if (stat != null) {
			List<String> nodes = listAllChilds(path);
			for(int i=nodes.size()-1;i>=0;i--) {
				Server.Log("delete "+nodes.get(i));
				zooKeeper.delete(nodes.get(i), -1);
			}
		}
	}
	
	/**
	 * 删除节点
	 * */
	public void deleteNode(String path) throws Exception{
		Stat stat = zooKeeper.exists(path, false);
		if (stat != null) {
			zooKeeper.delete(path, -1);
		}
	}
	
	public List<String> listAllChilds(String path) throws Exception{
		List<String> cList = zooKeeper.getChildren(path, false);
		List<String> listNodes = new ArrayList<>();
		if (cList != null) {
			for (String string : cList) {
				String path1 = path+"/"+string;
				if (path == "/") { //如果是根节点,不用加上 / 
					path1 = path+string;
				}
				listNodes.add(path1);
				List<String> list = listAllChilds(path1);
				if (list != null) {
					listNodes.addAll(list);
				}
			}
		}
		return listNodes;
	}
	
	public List<String> listChilds(String path) throws Exception {
		return zooKeeper.getChildren(path, false);
	}
	
	
	
}

class ZookeeperWatch implements Watcher{

	@Override
	public void process(WatchedEvent event) {
		
		Server.Log("eventType: "+event.getType());
	}
	
}
