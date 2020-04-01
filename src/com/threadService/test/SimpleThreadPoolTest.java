package com.threadService.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.threadService.MyThreadPool;

public class SimpleThreadPoolTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThreadPool executor =new MyThreadPool(3);
	        for (int i = 0; i < 5; i++) {
	            Runnable worker = new WorkerThread("" + i);
	            executor.execute(worker);
	          }
	        executor.shutdown();
	        for (int i = 0; i < 5; i++) {
	            Runnable worker = new WorkerThread("" + i);
	            executor.execute(worker);
	          }
	      
	        System.out.println("Finished all threads");
	    }

	}

