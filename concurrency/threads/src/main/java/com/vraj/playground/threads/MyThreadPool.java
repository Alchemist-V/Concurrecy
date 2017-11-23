/**
 * 
 */
package com.vraj.playground.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Its quite easy to get confused when we deal with multithreading priniples in
 * programing. Terms like Threads, thread pools etc kind of makes us nervous.
 * 
 * This is my attempt to understand how the threading ecosystem functions by
 * creating on of my own. <br>
 * <br>
 * 
 * This class represents a <b>thread pool</b>, a thread pool is nothing but a
 * container that contains specific number of threads (per your system
 * capabilities) with some management harness.
 * 
 * <br>
 * <br>
 * The idea is to have a request queue that consumes runnable tasks, and the
 * fixed threads in the threadpool keeps polling for them. This way we can
 * always tune the amount of system resource our app can block upon.
 * 
 * <br>
 * <br>
 * 
 * @author vrajori
 *
 */
public class MyThreadPool {
	private static final int THREAD_COUNT = 2;
	private AtomicInteger threadIndex = new AtomicInteger(0);
	private AtomicBoolean threadExecute;
	private ConcurrentLinkedQueue<Runnable> runnables;

	// list of threads.
	private List<MyThreadPoolThread> threads;

	MyThreadPool() {
		runnables = new ConcurrentLinkedQueue<Runnable>();
		threadExecute = new AtomicBoolean(true);
		threads = new ArrayList<MyThreadPoolThread>(THREAD_COUNT);
		for (int i = 0; i < THREAD_COUNT; i++) {
			MyThreadPoolThread thread = new MyThreadPoolThread(getThreadName(), threadExecute, runnables);
			threads.add(thread);
			thread.run();
		}
	}

	public void submitRunnableTask(Runnable runnable) {
		this.runnables.add(runnable);
	}

	private String getThreadName() {
		return "Thread: " + threadIndex.incrementAndGet();
	}

}
