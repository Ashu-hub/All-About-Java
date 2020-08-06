package JavaOverview.MultiThreading;

import java.util.LinkedList;
import java.util.List;

class Producer implements Runnable {
	private List<Integer> sharedQueue;
	private int maxSize = 2;

	public Producer(List<Integer> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				produce(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void produce(int i) throws InterruptedException {

		synchronized (sharedQueue) {
			while (sharedQueue.size() == maxSize) {
				System.out.println("Queue is full waiting for Consumer to Consume");
				sharedQueue.wait();
			}
		}

		synchronized (sharedQueue) {
			System.out.println("Produced-" + i);
			sharedQueue.add(i);
			//Thread.sleep(1000);
			// as soon as it produces, it notify
			sharedQueue.notify();
		}
	}

}

class Consumer implements Runnable {
	private List<Integer> sharedQueue;

	public Consumer(List<Integer> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void consume() throws InterruptedException {
		synchronized (sharedQueue) {
			if (sharedQueue.size() == 0) {
				System.out.println("Waiting for Producer to Produce ");
				sharedQueue.wait();
			}
		}

		synchronized (sharedQueue) {
			//Thread.sleep(10000);
			if (sharedQueue.size() >= 0) {
				System.out.println("Consumed-" + sharedQueue.remove(0));
				//
				sharedQueue.notify();
			}
		}

	}

}

public class ProducerConsumerProblem {

	public static void main(String[] args) {
		List<Integer> sharedQueue = new LinkedList<>();
		
		Producer producer = new Producer(sharedQueue);
		Consumer consumer = new Consumer(sharedQueue);
		
		Thread thread1 = new Thread(producer, "ProducerThread");
		Thread thread2 = new Thread(consumer, "ConsumerThread");

		thread1.start();
		thread2.start();
	}

}
