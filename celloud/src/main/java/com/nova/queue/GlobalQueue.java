package com.nova.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 全局队列
 * 
 * @author lin
 */
public class GlobalQueue {
	private static Queue<String> queue = new ConcurrentLinkedQueue<String>();

	/**
	 * 将指定元素插入此队列的尾部。
	 * 
	 * @param e
	 * @return
	 */
	public static boolean offer(String e) {
		return queue.offer(e);
	}

	/**
	 * 获取并移除此队列的头，如果此队列为空，则返回 null。
	 * 
	 * @return
	 */
	public static String poll() {
		return queue.poll();
	}

	/**
	 * 获取但不移除此队列的头；如果此队列为空，则返回 null。
	 * 
	 * @return
	 */
	public static String peek() {
		return queue.peek();
	}

	/**
	 * 从队列中移除指定元素的单个实例（如果存在）。
	 * 
	 * @param o
	 * @return
	 */
	public static boolean remove(String o) {
		return queue.remove(o);
	}

	/**
	 * 获取队列长度
	 * 
	 * @return
	 */
	public static int getSize() {
		return queue.size();
	}

	/**
	 * 队列是否为空
	 * 
	 * @return
	 */
	public static boolean isEmpty() {
		return queue.isEmpty();
	}
}