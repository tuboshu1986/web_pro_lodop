package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
	
	public static void main(String[] args) {
		
	}
	
	public static void fixed(){
		ExecutorService es = Executors.newFixedThreadPool(3);
		final Printer printer = new Printer();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			es.execute(new Runnable() {
				@Override
				public void run() {
					printer.print(Thread.currentThread().getName()+"-"+index);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					printer.print(Thread.currentThread().getName()+"-"+index+"完成");
				}
			});
		}
		System.out.println("完成");
	}
	
	public static void cached(){
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			es.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName()+"-"+index);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"-"+index+"完成");
				}
			});
		}
		System.out.println("完成");
	}	
	
	public static void schedule(){
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
		for (int i = 0; i < 10; i++) {
			final int index = i;
			ses.schedule(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName()+"-"+index);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"-"+index+"完成");
				}
			},2,TimeUnit.SECONDS);
		}
		System.out.println("完成");
	}	
	
	public static void single(){
		ExecutorService es = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 100; i++) {
			final int index = i;
			es.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName()+"-"+index);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"-"+index+"完成");
				}
			});
		}
		System.out.println("完成");
	}
}

class Printer{
	public void print(String param){
		System.out.println(param);
	}
}
