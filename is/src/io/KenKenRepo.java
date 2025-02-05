package io;

import backtracking.*;

public interface KenKenRepo {
	public KenKen load(String path);
	public void save(String path,KenKen temp);
	public default KenKen load() {
		return load("file/default");
	}
	public default void save(KenKen temp) {
		save("file/default",temp);
	}
}
