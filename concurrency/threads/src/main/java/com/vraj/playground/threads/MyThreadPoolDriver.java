/**
 * 
 */
package com.vraj.playground.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.vraj.playground.threads.task.SampleThreadPoolTask;
import com.vraj.playground.threads.task.SampleThreadPoolTaskFactory;

/**
 * @author vrajori
 *
 */
public class MyThreadPoolDriver {

	private static MyThreadPool threadPool;

	public static void main(String[] args) {

		threadPool = new MyThreadPool();
		for (int i = 0; i < 10; i++) {
			threadPool.submitRunnableTask(SampleThreadPoolTaskFactory.getSampleThreadPoolTask());
		}
	}
}
