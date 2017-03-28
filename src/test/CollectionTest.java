package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class CollectionTest {
	public static void main(String[] args) {
		Long a=0l,b=0l;
		
		
		LinkedList<String> list = new LinkedList<String>();
		System.out.println("++++");
		a = new Date().getTime();
		for (int i = 0; i < 10000; i++) {
			list.add(i+"");
		}
		b = new Date().getTime();
		System.out.println(b-a);
		
		ArrayList<String> al = new ArrayList<String>();
		System.out.println("++++");
		a = new Date().getTime();
		for (int i = 0; i < 10000; i++) {
			al.add(i+"");
		}
		b = new Date().getTime();
		System.out.println(b-a);
		
		
		
		System.out.println("----");
		a = new Date().getTime();
		for (int i = 0; i < 10000; i++) {
			list.get(i);
		}
		b = new Date().getTime();
		System.out.println(b-a);
		
		System.out.println("----");
		a = new Date().getTime();
		for (int i = 0; i < 10000; i++) {
			al.add(i+"");
		}
		b = new Date().getTime();
		System.out.println(b-a);
		
		

		System.out.println("++++");
		a = new Date().getTime();
		for (int i = 0; i < 100000; i++) {
			list.add(0, i+"");
		}
		b = new Date().getTime();
		System.out.println(b-a);
		
		System.out.println("++++");
		a = new Date().getTime();
		for (int i = 0; i < 100000; i++) {
			al.add(0, i+"");
		}
		b = new Date().getTime();
		System.out.println(b-a);
		
	}
}
