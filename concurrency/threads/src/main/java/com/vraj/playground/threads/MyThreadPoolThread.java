package com.vraj.playground.threads;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a single thread in {@link MyThreadPool}
 * 
 * @author vrajori
 *
 */
public class MyThreadPoolThread extends Thread {

	// boolean to keep check on the polling status of the threads.
	private AtomicBoolean execute = new AtomicBoolean();

	// Queue of runnable tasks that gets delegated to this pool.
	private ConcurrentLinkedQueue<Runnable> runnables;

	MyThreadPoolThread(String name, AtomicBoolean execute, ConcurrentLinkedQueue<Runnable> runnables) {
		super(name);
		this.execute = execute;
		this.runnables = runnables;
	}

	@Override
	public void run() {
		while (execute.get() && !runnables.isEmpty()) {
			Runnable runnable;
			while ((runnable = runnables.poll()) != null) {
				System.out.println("Thread: " + this.getName() + ", has picked up a new task");
				runnable.run();
			}
		}
	}

}
