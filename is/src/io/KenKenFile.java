package io;

import java.io.*;

import backtracking.*;

public class KenKenFile implements KenKenRepo{
	ObjectInputStream ois;
	ObjectOutputStream oos;
	KenKen buffer;
	private static KenKenFile instance=null;
	private KenKenFile() {}
	public static KenKenFile getKKF() {
		if(instance==null)
			return new KenKenFile();
		return instance;
	}
	
	public KenKen load(String path) {
		try {
			ois=new ObjectInputStream(new FileInputStream(path));
			this.buffer=(KenKen)ois.readObject();
			ois.close();
			return this.buffer;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new RuntimeException("File non trovato");
		} 
	}
	public void save(String path,KenKen temp) {
		try {
			oos=new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(temp);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new RuntimeException("File non trovato");
		}
		
	}
}
