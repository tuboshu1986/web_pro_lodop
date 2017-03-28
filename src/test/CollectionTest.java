package test;

import java.util.concurrent.*;

public class CollectionTest {
	public static LinkedBlockingQueue<String> deque = new LinkedBlockingQueue<String>();
	private static int index = 0;
	
	public static void main(String[] args) {
		for(int i=0;i<50;i++){
			deque.add(i+"");
		}
		
		final ExecutorService es = Executors.newFixedThreadPool(3);
		for(int i=0;i<3;i++){
			es.execute(new Runnable() {
				@Override
				public void run() {
					while(true){
						String name = Thread.currentThread().getName();
						String val = deque.poll();
						if(val==null){
							es.shutdown();
							break;
						}
						System.out.println(index+":::"+name+":::"+val);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}
}



