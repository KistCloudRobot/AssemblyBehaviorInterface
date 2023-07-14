package kr.ac.uos.ai.behavior.communication.message;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResponseLock {
	private final Lock			lock;
	private final Condition		responseArrived;
	
	public ResponseLock() {
		this.lock = new ReentrantLock();
		responseArrived = lock.newCondition();
	}
	
	public void lock() {
		this.lock.lock();
	}
	public void signal() {
		this.responseArrived.signalAll();
	}
	public void await() throws InterruptedException {
		this.responseArrived.await();
	}
	public void unlock() {
		this.lock.unlock();
	}
		
	
}
