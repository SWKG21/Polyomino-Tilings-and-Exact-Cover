
public class Column extends Data{
	int S; //size
	String N; //name
	Column L;
	Column R;
	
	public Column(Data U, Data D, Column L, Column R, int S, String N){
		super(U,D,L,R,null);
		this.S = S;
		this.N = N;
	}
}
