package com.cmb.nio.server;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.cmb.utils.CBLogUtils;

public class CBSocket {
	
	private static final int KBUF_SIZE = 1024;

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
						this.handleAccept(selectionKey);
					}
					if (selectionKey.isReadable()) {
						this.handleRead(selectionKey);
					}
					if (selectionKey.isWritable()) {
						this.handleWrite(selectionKey);
					}
					if (selectionKey.isConnectable()) {
						CBLogUtils.Log("socket connected");
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
	
	public void handleAccept(SelectionKey selectionKey) throws Exception{
		ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
		SocketChannel socketChannel = channel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(KBUF_SIZE));
	}
	
	public void handleRead(SelectionKey selectionKey) throws Exception{
		SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
		ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
		int bytesRead = socketChannel.read(byteBuffer);
		while (bytesRead>0) {
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				CBLogUtils.Log(byteBuffer.get());
			}
			System.out.println();
			byteBuffer.clear();
			bytesRead = socketChannel.read(byteBuffer);
		}
		if (bytesRead == -1) {
			socketChannel.close();
		}
	}
	
	public void handleWrite(SelectionKey key) throws Exception{
        ByteBuffer buf = (ByteBuffer)key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while(buf.hasRemaining()){
            sc.write(buf);
        }
        buf.compact();
    }
	
	public void connect() {
		SocketChannel socketChannel = null;
		Selector selector = null;
		try {
			socketChannel = socketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress(12134));
			selector = Selector.open();
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if (selector != null) {
					selector.close();
				}
				if (socketChannel != null) {
					socketChannel.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
}
