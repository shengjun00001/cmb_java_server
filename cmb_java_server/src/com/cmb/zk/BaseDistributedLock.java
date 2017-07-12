package com.cmb.zk;

import org.apache.zookeeper.ZooDefs.Ids;

import com.cmb.utils.CBLogUtils;

import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class BaseDistributedLock {
	
	private final ZooKeeper zooKeeper;
	private final String lockBasePath;
	private final String lockName;

	private String lockPath;

	public BaseDistributedLock(ZooKeeper zooKeeper,String lockBasePath,String lockName) {
		this.zooKeeper = zooKeeper;
		this.lockName = lockName;
		this.lockBasePath = lockBasePath;
	}
	
	public void lock() throws Exception{
		if (zooKeeper.exists(lockBasePath, true) == null) {
			zooKeeper.create(lockBasePath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		lockPath =  zooKeeper.create(lockBasePath+"/"+lockName, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		CBLogUtils.Log("lock path:"+lockPath);
		final Object lock = new Object();
		synchronized (lock) {
			while (true) {
				List<String> nodes = zooKeeper.getChildren(lockBasePath, new Watcher() {
					
					@Override
					public void process(WatchedEvent event) {
						synchronized (lock) {
							lock.notifyAll();
						}
					}
				});
				Collections.sort(nodes);
				CBLogUtils.Log(nodes);
				if (lockPath.endsWith(nodes.get(0))) {
					return;
				}else {
					lock.wait();
				}
			}
		}
	}
	
	/**
	 * 避免惊群效应锁
	 * */
	public void lock2() throws Exception{
		if (zooKeeper.exists(lockBasePath, true) == null) {
			zooKeeper.create(lockBasePath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		lockPath =  zooKeeper.create(lockBasePath+"/"+lockName, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		CBLogUtils.Log("lock path:"+lockPath);
		final Object lock = new Object();
		synchronized (lock) {
			while (true) {
				List<String> nodes = zooKeeper.getChildren(lockBasePath,false);
				Collections.sort(nodes);
				CBLogUtils.Log(nodes);
				String basePath = lockBasePath+"/";
				String tmp = lockPath.substring(basePath.length(), lockPath.length());
				
				Integer index = nodes.indexOf(tmp);  //获取当前节点所在位置
				if (index == 0) { //若第一个则成功获取到锁
					return;
				}
				String preNode = basePath+nodes.get(index-1);  //否则监听前一个节点
				zooKeeper.exists(preNode, new Watcher() {
					@Override
					public void process(WatchedEvent event) {
						synchronized (lock) {
							lock.notifyAll();
						}
					}
				});
				lock.wait();
				
			}
		}
	}
	
	public void releaseLock() throws Exception{
		zooKeeper.delete(lockPath, -1);
		lockPath = null;
	}
	
	
	

}
