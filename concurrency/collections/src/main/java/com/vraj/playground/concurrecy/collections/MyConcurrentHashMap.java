package com.vraj.playground.concurrecy.collections;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple Concurrent hash map.
 *
 * @author Vikas Rajoria
 *
 */
public class MyConcurrentHashMap {

	private static final int INITIAL_CAPACITY = 100;

	// private int size;

	private Lock[] locks;

	private MyHashMap myHashMap = new MyHashMap();

	MyConcurrentHashMap() {
		locks = new Lock[INITIAL_CAPACITY];
		for (int i = 0; i < locks.length; i++) {
			locks[i] = new ReentrantLock();
		}
	}

	/**
	 * Inserts/updates new key-value pair in {@link MyConcurrentHashMap}
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean put(Integer key, String value) {
		return myHashMap.put(key, value);
	}

	/**
	 * Fetches value for a key from {@link MyConcurrentHashMap}, returns null if
	 * key is not found.
	 * 
	 * @param key
	 * @return
	 */
	public String get(Integer key) {
		return myHashMap.get(key);
	}

	private class MyHashMap {
		private LinkedList[] list = new LinkedList[INITIAL_CAPACITY];

		private class MapEntry {
			private Integer key;
			private String value;

			MapEntry(int key, String value) {
				this.key = key;
				this.value = value;
			}
		}

		boolean put(Integer key, String value) {
			if (key == null) {
				// Log exception;
				return false;
				// throw new IllegalArgumentException("Key cannot be null");
			}
			int hash = getHash(key);

			LinkedList targetList = list[hash];
			Lock targetLock = locks[hash];

			if (targetList == null) {
				targetList = new LinkedList<>();
				list[hash] = targetList;
			}
			targetLock.lock();
			int i = 0;
			for (; i < targetList.size(); i++) {
				MapEntry entry = (MapEntry) (targetList.get(i));
				if (entry != null && entry.key.equals(key)) {
					entry.value = value;
					System.out.println("updated value: " + value + ", for key: " + key);
					targetLock.unlock();
					return true;
				}
			}

			if (i == targetList.size()) {
				targetList.addLast(new MapEntry(key, value));
				targetLock.unlock();
				return true;
			}
			targetLock.unlock();

			return false;
		}

		String get(Integer key) {
			int hash = getHash(key);
			LinkedList targetList = list[hash];
			String value = null;
			if (targetList != null) {
				for (int i = 0; i < targetList.size(); i++) {
					MapEntry mapEntry = (MapEntry) (targetList.get(i));
					if (mapEntry != null && mapEntry.key.equals(key)) {
						value = mapEntry.value;
						break;
					}
				}
			}
			return value;
		}

		private int getHash(Integer key) {
			return key % INITIAL_CAPACITY;
		}

	}

	private int getHash(Integer key) {
		return key % INITIAL_CAPACITY;
	}

	public static void main(String[] args) {
		MyConcurrentHashMap myCCHashMap = new MyConcurrentHashMap();
		myCCHashMap.put(1, "A");
		myCCHashMap.put(9, "B");
		myCCHashMap.put(17, "C");
		myCCHashMap.put(9, "blah blah");

		ExecutorService e = Executors.newFixedThreadPool(4);

		test(e, myCCHashMap);
		e.shutdown();
		// System.out.println(myCCHashMap.get(20));

	}

	public static void test(ExecutorService e, final MyConcurrentHashMap map) {
		for (int i = 0; i < 120; i++) {
			final int val = i;
			final int testKey = 20;
			e.execute(new Runnable() {
				public void run() {
					map.put(testKey, "entry-" + String.valueOf(val));
					map.get(testKey);
				}
			});
		}
	}
}
