package com.cmb.zookeeper.service;

public interface IDistributedLock {
	
	/**
	 * ��ȡ�ֲ�ʽ��
	 * */
	public void acquire() throws Exception;
	
	/**
	 * �ͷŷֲ�ʽ��
	 * */
	public void release() throws Exception;

}
