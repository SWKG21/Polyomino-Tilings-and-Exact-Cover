import java.util.LinkedList;

public class FreePolyominoes { //for all types of freepolyomino
	
	
	public int none(int p){
		none testnone = new none();
		testnone.Count(p);
		return testnone.count/8;
	}
	
	
	//Np represent Nprime
	public int Np(int p){
		FixedPolyominoes testNp = new FixedPolyominoes();
		testNp.Count(p);
		return testNp.count;
	}
	
	
	
	
	
	
	
	
	
	//axisp=all+2axis2+2axis
	public int axisp(int p){
		return (Hp(p)+Vp(p))/2;
	}
	
	
	//Hp=HXp+HIp=all+2axis2+2axis
	public int Hp(int p){
		return HXp(p)+HIp(p);
	}
	
	public int HXp(int p){
		if(p%2!=0)
			return 0;
		else
			return Np(p/2);
	}
	
	public int HIp(int p){
		HIp testHIp = new HIp();
		testHIp.Count(p);
		return testHIp.count;
	}
	
	//Vp=VXp+VIp=all+2axis2+2axis
	public int Vp(int p){
		return VXp(p)+VIp(p);
	}
	
	public int VXp(int p){
		return HXp(p);
	}
	
	public int VIp(int p){
		return HIp(p);
	}
	
	
	//H=HX+HI=axis
	public int H(int p){
		return HX(p)+HI(p);
	}	
	
	public int HI(int p){
		HI testHI = new HI();
		testHI.Count(p);
		return testHI.count;
	}
	
	public int HX(int p){
		if(p%2!=0)
			return 0;
		else{
			HX testHX = new HX();
			testHX.Count(p/2);
			return testHX.count;
		}
	}
	
	
	//HVR=HVRp-HVADR2=2axis2
	public int HVR(int p){
		return HVRp(p)-HVADR2(p);
	}		
	
	public int HVRp(int p){
		return HVRXp(p)+HVRIp(p);
	}
	
	public int HVRIp(int p){
		HVRIp testHVRIp = new HVRIp();
		testHVRIp.Count(p);
		return testHVRIp.count;
	}
	
	public int HVRXp(int p){
		return HVRXXp(p)+HVRXIp(p);
	}
	
	public int HVRXXp(int p){
		if(p%2!=0)
			return 0;
		else
			return HXp(p/2);
	}
	
	public int HVRXIp(int p){
		if(p%2!=0)
			return 0;
		else
			return HIp(p/2);
	}
	
	//need HVRX to calcule HVR
	public int HVRI(int p){
		HVRI testHVRI = new HVRI();
		testHVRI.Count(p);
		return testHVRI.count;
	}	
	
	
	
	
	
	
	
	
	
	//diagp=all+2diag2+2diag
	public int diagp(int p){
		return (Ap(p)+Dp(p))/2;
	}
	
	//Ap=all+2diag2+2diag
	public int Ap(int p){
		Ap testAp = new Ap();
		testAp.Count(p);
		return testAp.count;
	}
	
	//Dp=all+2diag2+2diag
	public int Dp(int p){
		return Ap(p);
	}
	
	//A=diag
	public int A(int p){
		A testA = new A();
		testA.Count(p);
		return testA.count;
	}
	
	//ADR=diag2
	public int ADR(int p){
		ADR testADR = new ADR();
		testADR.Count(p);
		return testADR.count;
	}
	
	//HVADR2=all
	public int HVADR2(int p){
		HVADR2 testHVADR2 = new HVADR2();
		testHVADR2.Count(p);
		return testHVADR2.count;
	}
	
	
	
	
	
	
	//R
	public int Rp(int p){
		if(p%2!=0){
			RIIp testRp = new RIIp();
			testRp.oddSizeCount(p);
			return testRp.count;
		}
		else{
			return 0;                           //to modify
		}
		
	}
	
	/*//need R
	R2Xp
	R2Ip*/
	
	
	public int HVADR2Xp(int p){
		if(p%4!=0)
			return 0;
		else
			return Ap(p/4);
	}
	
	
	//HVADR2Ip
	
	
	
	
	
	
	//R=rotate
	public int R(int p){
		MonR testMonR = new MonR();
		testMonR.Count(p);
		return testMonR.count;
	}
	
	
	//R2=rotate
	public int R2(int p){
		MonFree testMonFree = new MonFree();
		testMonFree.Count(p);
		return testMonFree.count-HVADR2(p)-HVR(p)/2-H(p)-ADR(p)-A(p)-R(p)-none(p);
	}
	
	
}
