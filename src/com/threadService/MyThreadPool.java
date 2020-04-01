package com.threadService;

import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {
	

    private final int nThreads;
    private final PoolThread[] threads;
    private  LinkedBlockingQueue<Runnable> queue;
    private boolean isStopped = false;
 
    public MyThreadPool(int nThreads) {
        this.nThreads = nThreads;
        queue = new LinkedBlockingQueue<Runnable>();
        threads = new PoolThread[nThreads];
 
        for (int i = 0; i < nThreads; i++) {
        	if(isStopped)
        		break;
        	else {
            threads[i] = new PoolThread();
            threads[i].start();
        	}
        }
    }
 
    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    	}
    public synchronized void shutdown(){
        this.isStopped = true;
       
    }
   
    public synchronized void shutdownNow()
    {
    	 this.isStopped = true;
    	 for(PoolThread thread : threads){
             thread.doStop();
          }
    }
    private  class PoolThread extends Thread {

        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }
                    task =  queue.poll();
                    
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
        
        public synchronized void doStop(){
            isStopped = true;
            this.interrupt(); //break pool thread out of dequeue() call.
        }

      


    }
    
    }
