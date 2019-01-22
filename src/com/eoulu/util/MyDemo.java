package com.eoulu.util;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;

public class MyDemo {

	
	
	
	public void test(){
		synchronized (this) {
			
		System.out.println("线程开始");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("线程结束");
		}
	}
	
	public static void main(String[] args){

//		for(int i = 0;i < 4;i ++){
//			ThreadDemo demo = new ThreadDemo();
//			demo.start();
//		}
		
		Path path = Paths.get("E:");

	    try {
	      FileStore fs = Files.getFileStore(path);
	      printDetails(fs, AclFileAttributeView.class);
	      printDetails(fs, BasicFileAttributeView.class);
	      printDetails(fs, DosFileAttributeView.class);
	      printDetails(fs, FileOwnerAttributeView.class);
	      printDetails(fs, PosixFileAttributeView.class);
	      printDetails(fs, UserDefinedFileAttributeView.class);
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	}
	
	 public static void printDetails(FileStore fs,
		      Class<? extends FileAttributeView> attribClass) {
		    boolean supported = fs.supportsFileAttributeView(attribClass);
		    System.out.format("%s is  supported: %s%n", attribClass.getSimpleName(),
		        supported);
		  }
	
	
	
		

}

class ThreadDemo extends Thread{
	public void run(){
		
		MyDemo demo = new MyDemo();
		demo.test();
		
	}
}
	




