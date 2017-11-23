/**
 * 
 */
package com.vraj.playground.threads.task;

import java.util.concurrent.atomic.AtomicInteger;

import com.vraj.playground.threads.MyThreadPool;

/**
 * This is a sample thread pool task for feeding {@link MyThreadPool}
 * 
 * @author vrajori
 *
 */
public class SampleThreadPoolTask implements Runnable {

	private static AtomicInteger taskId = new AtomicInteger(0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		String task = "ThreadId#" + String.valueOf(getTaskId());
		System.out.println("Task assigned to my thread pool: " + task);
		// Task completion time 0.5 sec
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getTaskId() {
		return taskId.incrementAndGet();
	}

}
