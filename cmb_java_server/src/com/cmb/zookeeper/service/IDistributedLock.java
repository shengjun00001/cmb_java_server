package com.cmb.zookeeper.service;

public interface IDistributedLock {
	
	/**
	 * 获取分布式锁
	 * */
	public void acquire() throws Exception;
	
	/**
	 * 释放分布式锁
	 * */
	public void release() throws Exception;

}
