package backtracking;

import java.util.*;

public interface Cage {
	public Set<Index> getCage();
	public int size();
	public Operation getOp();
	public int getResult();
}
