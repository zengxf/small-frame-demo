package test.thread;

import java.util.Random;

public class RenameThread {

    public static void main(String[] args) {

	new Thread(new MyRun(), "test-aa-0").start(); // OK，Eclipse 要切换视图

    }

    static class MyRun implements Runnable {

	@Override
	public void run() {
	    Random r = new Random();
	    while (true) {
		int i = r.nextInt(10000);
		String name = Thread.currentThread().getName();
		name = name.substring(0, name.lastIndexOf("-"));
		name += "-" + i;
		System.out.println(name);
		Thread.currentThread().setName(name);
		try {
		    Thread.sleep(1000L);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}

    }
}
