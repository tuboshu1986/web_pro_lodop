package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
	
	public static void main(String[] args) throws FileNotFoundException {
		Reader reader = new Reader("D:/a.txt");
		reader.start();
		System.out.println("main结束");
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


/**
 * 
 * @author Administrator
 *
 */
class Reader extends Thread {
	private BufferedReader reader;
	private int lineCount;
	private String currLine;
	public Reader(String sourcePath) throws FileNotFoundException{
		File file = new File(sourcePath);
		this.reader = new BufferedReader(new FileReader(file));
	}
	
	public String readLine(){
		return this.currLine;
	}
	
	public boolean hashNext() throws IOException{
		this.currLine = this.reader.readLine();
		if(this.currLine==null){
			this.reader.close();
			return false;
		}else{
			this.currLine = String.format("第%d行为::%s",this.lineCount+1,this.currLine);
			this.lineCount++;
			return true;
		}
	}
	
	public int getLineCount(){
		return this.lineCount;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			while(this.hashNext()){
				System.out.println(this.readLine());
				if(this.getLineCount()==10){
					System.out.println("此处应当中断");
					Thread.currentThread().interrupt();
				}
				Thread.currentThread().sleep(1000L);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


/**
 * 
 * @author Administrator
 *
 */
class Printer implements Runnable {
	
	private int printCount;
	private int priority;

	public Printer(){
	}
	
	public Printer(int priority){
		this.priority = priority;
	}
	
	public int getPrintCount(){
		return printCount;
	}
	
	public void print(String param){
		System.out.println(param);
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		Thread.currentThread().setPriority(this.priority);
		for(int i=0;i<5;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
			printCount=printCount+1;
			if(i==3){
				Thread.currentThread().yield();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
