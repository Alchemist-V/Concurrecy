/**
 * 
 */
package com.vraj.playground.threads;

/**
 * @author vrajori
 *
 */
public class MyThreadPoolDriver {

	private static MyThreadPool threadPool;

	public static void main(String[] args) {
		threadPool = new MyThreadPool();
		Runnable runnable = new Runnable() {

			public void run() {
				System.out.println("This is a dummy runnable task");
				try {
					System.out.println("Sleeping for 1 second.");
					Thread.sleep(1000);
					System.out.println("Awake after 1 second.");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		threadPool.submitRunnableTask(runnable);

	}

}
