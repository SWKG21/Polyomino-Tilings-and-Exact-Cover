
public class Data {
	Data U, D, L, R; //up, down, left, right
	Column C; //column
	
	public Data(Data U, Data D, Data L, Data R, Column C){
		this.U = U;
		this.D = D;
		this.L = L;
		this.R = R;
		this.C = C;
	}
	
	public Data(){
	}
}
