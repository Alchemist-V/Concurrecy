/**
 * 
 */
package com.vraj.playground.volatility;

/**
 * Small program to under stand volatile keyword.
 * 
 * @author vrajori
 *
 */
public class VolatileDemo {

	/**
	 * Volatile member.
	 */
	private static volatile int THE_INT = 0;

	public static void main(String[] args) {
		new ChangeListener().start();
		new ChangeMaker().start();
	}

	/**
	 * Simple threads that will listen on the value of {@link THE_INT} infinitly
	 * and will exit if it reaches a certain criteria.
	 * 
	 * @author vrajori
	 *
	 */
	private static class ChangeListener extends Thread {
		@Override
		public void run() {
			int localValue = THE_INT;
			while (localValue < 5) {
				if (localValue != THE_INT) {
					System.out.println("THE_INT value: " + THE_INT);
					localValue = THE_INT;
				}
			}
		}
	}

	/**
	 * Simple thread that initiates changes to volatile member.
	 * 
	 * @author vrajori
	 *
	 */
	private static class ChangeMaker extends Thread {
		@Override
		public void run() {
			int localvalue = THE_INT;
			while (localvalue < 5) {
				THE_INT = localvalue + 1;
				System.out.println("Incremented THE_INT to: " + THE_INT);
				localvalue = THE_INT;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
