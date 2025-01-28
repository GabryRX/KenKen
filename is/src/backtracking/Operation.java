package backtracking;

public enum Operation {
	SUM("+"),
	SUB("-"),
	MUL("*"),
	DIV("/");
	
	private String op;
	
	Operation(String op){
		this.op=op;
	}
	public String toString() {
		return this.op;
	}
}
