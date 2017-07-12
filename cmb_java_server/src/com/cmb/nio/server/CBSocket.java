package com.cmb.nio.server;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class CBSocket {

	public void listen() {
		Selector selector = null;
		ServerSocketChannel ssChannel = null;
		try {
			selector = Selector.open();
			ssChannel = ServerSocketChannel.open();
			ssChannel.socket().bind(new InetSocketAddress(12134), 1024);
			ssChannel.configureBlocking(false);
			ssChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			while (true) {
				if (selector.select(300) == 0) {  //三百毫秒轮询一次
					continue;
				}
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					SelectionKey selectionKey = (SelectionKey) iterator.next();
					if (selectionKey.isAcceptable()) {
						// 一个新的注册
						
					}
					if (selectionKey.isReadable()) {
						
					}
					if (selectionKey.isWritable()) {
						
					}
					if (selectionKey.isConnectable()) {
						
					}
					iterator.remove();
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if (selector != null) {
					selector.close();
				}
				if (ssChannel != null) {
					ssChannel.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
	}
	
}
