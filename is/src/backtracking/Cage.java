package backtracking;

import java.util.*;
import java.io.*;

public interface Cage extends Serializable{
	public Set<Index> getCage();
	public int size();
	public Operation getOp();
	public int getResult();
}
