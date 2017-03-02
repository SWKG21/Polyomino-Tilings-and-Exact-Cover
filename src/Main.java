import java.awt.Color;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		
		/*//for test Polyomino(String)
		String s = "[(1,27), (30,4), (5,6), (78,8), (99,10)]";
		Polyomino test = new Polyomino(s);
		for(int i=0; i<test.size(); i++){
			System.out.println(test.xcoords[i]+","+test.ycoords[i]);
		}*/
		
		
		
		/*//for test Fixed
		long startTime = System.currentTimeMillis();
		FixedPolyominoes testFixed = new FixedPolyominoes();
		int p = 14;
		testFixed.Count(p);
		System.out.println(testFixed.count);
		long endTime = System.currentTimeMillis();
	    long duration = endTime - startTime;
	    System.out.printf("Elapsed time: %d milliseconds\n", duration);*/
		
		
		
		/*//for test testFix, don't improve much
		long startTime = System.currentTimeMillis();
		testFix testtestFix = new testFix();
		int p=10;
		testtestFix.Count(p);
		System.out.println(testtestFix.count);
		long endTime = System.currentTimeMillis();
	    long duration = endTime - startTime;
	    System.out.printf("Elapsed time: %d milliseconds\n", duration);*/
		
		
		/*//for test MonFree
		MonFree testMonFree = new MonFree();
		int p=10;
		testMonFree.Count(p);
		System.out.println(testMonFree.count);
		Image2d img = new Image2d(1500, 800);
		int i = 0;
		while(!testMonFree.polyominoList.isEmpty()){
			
			LinkedList<Polygon> list = testMonFree.polyominoList.poll().translate(5+i%100,-i/15).toPolygons(10);
			for(Polygon po : list)
				img.addPolygon(po.xpoints, po.ypoints, Color.BLACK);
			i=i+7;	
		}
		Image2dViewer imgV = new Image2dViewer(img);*/
		
		
		
		/*//for test MonOneSided
		MonOneSided testMonOneSided = new MonOneSided();
		int p = 15;
		testMonOneSided.Count(p);
		System.out.println(testMonOneSided.count);*/
		
		
		
		/*//for test Free.none
		FreePolyominoes testFree = new FreePolyominoes();
		int p = 16;
		System.out.println(testFree.none(p));*/
		
		
		
		/*//for test Free.A
		FreePolyominoes testFree = new FreePolyominoes();
		int p = 19;
		System.out.println(testFree.A(p));*/
		
		
		
		/*//for test Free.ADR
		FreePolyominoes testFree = new FreePolyominoes();
		for(int p=1; p<26; p++){
			System.out.println(testFree.ADR(p));
		}*/
		
		
		
		/*//for test Free.HVADR2
		FreePolyominoes testFree = new FreePolyominoes();
		for(int p=1; p<26; p++){
			System.out.println(testFree.HVADR2(p));
		}*/
		
		
		
		/*//for test Free.H
		FreePolyominoes testFree = new FreePolyominoes();
		int p = 19;
		System.out.println(testFree.H(p));*/
		
		
		
		/*//for test Free.HVR
		FreePolyominoes testFree = new FreePolyominoes();
		for(int p=1; p<26; p++){
			System.out.println(testFree.HVR(p));
		}*/
		
		
		
		/*//for test R
		FreePolyominoes testFree = new FreePolyominoes();
		int p = 14;
		System.out.println(testFree.R(p));*/
		
		
		
		/*//for test R2
		FreePolyominoes testFree = new FreePolyominoes();
		int p = 10;
		System.out.println(testFree.R2(p));*/
		
		
		
		/*//for test Free.axisp
		FreePolyominoes testFree = new FreePolyominoes();
		int p = 25;
		System.out.println(testFree.axisp(p));*/
		
		
		
		/*//for test Free.diagp
		FreePolyominoes testFree = new FreePolyominoes();
		int p = 25;
		System.out.println(testFree.diagp(p));*/
		
		
		
		
		
		/*int[][] matrix1 = new int[][] {{0,0,1,0,1,1,0},
			   						   {1,0,0,1,0,0,1},
			   						   {0,1,1,0,0,1,0},	// (1)
			   						   {1,0,0,1,0,0,0},
			   						   {0,1,0,0,0,0,1},
			   						   {0,0,0,1,1,0,1}};*/
		
		//testTask4(matrix1);

		//printMatrix(matrixAllSubsets(3));   
		//testTask6(matrixAllSubsets(3));
		
		//testTask4(matrixAllSubsets(10));
			   						   
		//testTask8();
		testTask8a();
		//testTask8b();
		//testTask8c();
	}
	
	public static void testTask6(int[][] m){
		Column H = dancingLinksStructureOf(m);
		for(Column c = H.R; c != H; c = c.R){
			System.out.println(c.N + " - " + c.S);
		}
		printSolution(exactCover(dancingLinksStructureOf(m)));
	}
	
	public static void coverColumn(Column x){
		x.R.L = x.L;
		x.L.R = x.R;
		for(Data t = x.D; t != x; t = t.D){
			for(Data y = t.R; y != t; y = y.R){
				y.D.U = y.U;
				y.U.D = y.D;
				y.C.S = y.C.S - 1;
			}
		}
	}
	
	public static void uncoverColumn(Column x){
		x.R.L = x;
		x.L.R = x;
		for(Data t = x.U; t != x; t = t.U){
			for(Data y = t.L; y != t; y = y.L){
				y.D.U = y;
				y.U.D = y;
				y.C.S = y.C.S + 1;
			}
		}
	}
	
	//Exact copy of the pseudo-code of Task 6
	//Returns all the solutions of an exactCover problem based on dancing links structure
	//(represented by the first Column H)
	public static LinkedList<LinkedList<LinkedList<Integer>>> exactCover(Column H){
		LinkedList<LinkedList<LinkedList<Integer>>> P = new LinkedList<LinkedList<LinkedList<Integer>>>();
		if(H == null) //No solutions
			return (P);
		if(H.R == H){ //Empty solution
			LinkedList<LinkedList<Integer>> vide = new LinkedList<LinkedList<Integer>>();
			P.add(vide);
			return (P);
		}
		
		int min = H.R.S;
		Column x = H.R;
		
		for(Column c = H.R; c != H; c = c.R){
			if(c.S < min){
				min = c.S;
				x = c;
			}
		}
		//Here, x.S is minimal
		coverColumn(x);
		for(Data t = x.U; t != x; t = t.U){
			for(Data y = t.L; y != t; y = y.L)
				coverColumn(y.C);
			for(LinkedList<LinkedList<Integer>> p : exactCover(H)){
				LinkedList<Integer> s = new LinkedList<Integer>();
				s.add(Integer.parseInt(t.C.N));
				for(Data y = t.L; y != t; y = y.L){
					s.add(Integer.parseInt(y.C.N));
				}
				p.add(s);
				P.add(p);
			}
			for(Data y = t.R; y!=t; y = y.R)
				uncoverColumn(y.C);
		}
		uncoverColumn(x);
		return P;
	}
	
	public static void testTask4(int[][] m){
		printSolution(exactCover(m));
	}
	
	//Nicely prints the solution of the previous functions if we suppose the sets we work
	//with are sets of integers such as the example problem given at 2.1 in the pdf
	public static void printSolution(LinkedList<LinkedList<LinkedList<Integer>>> P){
		System.out.println("--- Beginning ---");
		int m = 1;
		for(LinkedList<LinkedList<Integer>> s : P){
			System.out.println("[Solution "+m+"]");
			int n = 1;
			for(LinkedList<Integer> p : s){
				System.out.println(" Set " + n + ":");
				for(Integer j : p)
					System.out.println("	" + j);
				n++;
			}
			m++;
		}
		System.out.println("--- End ---");
	}
	
	//Generate a matrix to test the algo 'exactCover' with C containing all subsets of n-size X (excluding the empty set)
	//The i-th line of the returned matrix contains the binary conversion of i+1
	public static int[][] matrixAllSubsets(int n){
		int[][] m = new int[((int) Math.pow(2, n)) - 1][n];
		
		for(int i = 0; i < m.length; i++){
			int i2 = i+1;
			for(int j = m[0].length-1; j >= 0; j--){
				m[i][j] = i2%2;
				i2 = (i2-m[i][j])/2;
			}
		}
		
		return m;
	}
	
	//Prints the content of a matrix m
	public static void printMatrix(int[][] m){
		for(int i = 0; i < m.length; i++){
			for(int j = 0; j < m[0].length; j++)
				System.out.print(m[i][j] + " ");
			System.out.println();
		}
	}
	
	//Returns a set of all found exactCovers, this was exactly translated from the pseudo-code of the PDF.
	//A set is represented here by a LinkedList
	public static LinkedList<LinkedList<LinkedList<Integer>>> exactCover(LinkedList<Integer> X, LinkedList<LinkedList<Integer>> C){
		LinkedList<LinkedList<LinkedList<Integer>>> P = new LinkedList<LinkedList<LinkedList<Integer>>>();
		if(X.isEmpty()){
			LinkedList<LinkedList<Integer>> vide = new LinkedList<LinkedList<Integer>>();
			P.add(vide);
			return (P);
		}
		Integer x = X.peek();
		for(LinkedList<Integer> S : C){
			if(S.contains(x)){
				LinkedList<Integer> X2 = new LinkedList<Integer>(X);
				LinkedList<LinkedList<Integer>> C2 = new LinkedList<LinkedList<Integer>>();
				
				for(LinkedList<Integer> t : C){ //C2 <- deep copy of C
					LinkedList<Integer> t2 = new LinkedList<Integer>(t);
					C2.add(t2);
				}
				
				for(Integer y : S){
					X2.remove(y);
					for(LinkedList<Integer> T : C){
						if(T.contains(y)){
							C2.remove(T);
						}
					}
				}
				
				for(LinkedList<LinkedList<Integer>> p : exactCover(X2,C2)){
					p.add(S);
					P.add(p);
				}
			}
		}
		return P;
	}
	
	//Returns a set of all found solutions of the exactCover problem described by the matrix m
	public static LinkedList<LinkedList<LinkedList<Integer>>> exactCover(int[][] m){
		LinkedList<Integer> X = new LinkedList<Integer>();
		if(m.length == 0) //It means C is empty, so there are no solutions (we thus return an empty set)
			return (new LinkedList<LinkedList<LinkedList<Integer>>>());
		for(int j = 1; j <= m[0].length; j++)
			X.add(j);
		
		LinkedList<LinkedList<Integer>> C = new LinkedList<LinkedList<Integer>>();
		
		for(int i = 0; i < m.length; i++){
			LinkedList<Integer> e = new LinkedList<Integer>();
			for(int j = 0; j < m[0].length; j++){
				if(m[i][j] == 1)
					e.add(j+1);
			}
			C.add(e);
		}
		return(exactCover(X,C));
	}
	
	//Task 5 - Returns the 'H' column of the PDF based on the exact cover problem given by the matrix m
	//All columns are named '1', '2', ...
	public static Column dancingLinksStructureOf(int[][] m){
		
		Column H = new Column(null, null, null, null, 0, "H");
		H.R = H;
		H.L = H;
		H.C = H;
		
		if(m.length == 0) //No solution
			return null;
		if(m[0].length == 0){
			return(H);
		}
		
		//We convert m into a matrix m2 of Data
		Data[][] m2 = new Data[m.length][m[0].length];
		for(int i = 0; i < m.length; i++){
			for(int j = 0; j < m[0].length; j++){
				if(m[i][j] == 1){
					m2[i][j] = new Data();
					m2[i][j].L = m2[i][j];
					m2[i][j].R = m2[i][j];
				}
			}
		}
		
		//We create the columns and we update all the U,D,L,R
		Column previousColumn = H;
		
		for(int j = 0; j < m2[0].length; j++){
			Column c = new Column(null,null,null,null,0,(j+1)+"");
			c.C = c;
			c.L = previousColumn;
			c.R = previousColumn.R;
			previousColumn.R = c;
			c.U = c;
			c.D = c;
			
			Data previousData = c;
			for(int i = 0; i < m2.length; i++){
				if(m2[i][j] != null){

					m2[i][j].C = c;
					c.U = m2[i][j];
					m2[i][j].D = c;
					c.S+=1;
					
					m2[i][j].U = previousData;
					previousData.D = m2[i][j];
					
					int k = j+1;
					boolean RFound = false;
					while(k < m2[0].length && !RFound){
						if(m2[i][k] != null){
							m2[i][j].R.L = m2[i][k]; //m2[i][j].R is never null (cf conversion m -> m2)
							m2[i][k].R = m2[i][j].R;
							
							m2[i][j].R = m2[i][k];
							m2[i][k].L = m2[i][j];
							RFound = true;
						}
						k++;
					}
					previousData = m2[i][j];
				}
			}
			previousColumn = c;
		}
		return H;
	}
	
	//EXPLIQUER TASK 7
	
	//Cover P with polyominoes of l
	public static int[][] tilingToExactCover(Polyomino P, Polyomino[] l, boolean useExactlyOnce, boolean useRotations, boolean useReflections, HashMap<Integer,Point> pointId){
		
		int[][] binaryMatrixP = P.toBinaryMatrix();
		int[][] result = new int[l.length*binaryMatrixP.length*binaryMatrixP[0].length*(useRotations && useReflections ? 8 : (useRotations || useReflections ? 4 : 1))][P.size()+(useExactlyOnce ? l.length : 0)];
		if(P.size() == 0)
			return result;
		
		//We attribute a unique id between 0 and P.size()-1 to each point of P based on its (relative) coordinates
		//We store this in pointId to be able to get the point from the id later in the program
		int id = 0;
		int[][] uniqueId = new int[binaryMatrixP.length][binaryMatrixP[0].length];
		for(int x = 0; x < binaryMatrixP.length; x++){
			for(int y = 0; y < binaryMatrixP[0].length; y++){
				if(binaryMatrixP[x][y] == 1){
					uniqueId[x][y] = id;
					pointId.put(id, new Point(x,y));
					id++;
				}
			}
		}
		
		int uniqueIdForqAtXY = 0; //Will contain a unique Id for a polyomino and others for its rotations/symmetries and dependent of its position
		
		//For each polyomino l[p] in l, we check if it fits (including its rotations/symmetries) at the (relative) coordinate x,y in P.
		for(int p = 0; p < l.length; p++){
			LinkedList<int[][]> polyominoAssociated = new LinkedList<int[][]>(); //Will contain DIFFERENT binary matrixes of the polyominoes obtained by rotations/symmetries of l[p]
			PolyominoList potentialPolyominoes = new PolyominoList(); //Will contain all reflections and rotations of l[p] including repetitions
			potentialPolyominoes.add(l[p]);
			if(useRotations){
				for(int i = 0; i < 3; i++)
					potentialPolyominoes.add(l[p].rotate(i+1)); //We add the rotations of l[p]
				if(useReflections){
					Polyomino base = l[p].reflect("V"); //We flip the polyomino and add all its rotations
					for(int i = 0; i < 4; i++)
						potentialPolyominoes.add(base.rotate(i));
				}
			}
			else if(useReflections){ //We flip l[p] in all ways possible
				Polyomino base = l[p].reflect("V");
				potentialPolyominoes.add(base);
				potentialPolyominoes.add(base.reflect("H"));
				potentialPolyominoes.add(l[p].reflect("H"));
			}
			
			//Here we get rid of all doubles that could have appeared
			//(if a rotation doesn't change the polyomino for instance)
			for(Polyomino q : getRidOfFixedDoubles(potentialPolyominoes))
				polyominoAssociated.add(q.toBinaryMatrix());
			
			
			for(int x = 0; x < binaryMatrixP.length; x++){
				for(int y = 0; y < binaryMatrixP[0].length; y++){
					for(int[][] m_q : polyominoAssociated){
						boolean fitsIn = true; //Can be optimized here
						for(int x2 = 0; x2 < m_q.length; x2++){
							for(int y2 = 0; y2 < m_q[0].length; y2++){
								if(m_q[x2][y2] == 1 && (x+x2 >= binaryMatrixP.length || y+y2 >= binaryMatrixP[0].length || binaryMatrixP[x+x2][y+y2] == 0))
									fitsIn = false;
							}
						}
						//If the polyomino does fit, we add it to the matrix result
						if(fitsIn){
							for(int x2 = 0; x2 < m_q.length; x2++){
								for(int y2 = 0; y2 < m_q[0].length; y2++){
									if(m_q[x2][y2] == 1){
										result[uniqueIdForqAtXY][uniqueId[x+x2][y+y2]] = 1;
										if(useExactlyOnce)
											result[uniqueIdForqAtXY][result[0].length-p-1] = 1;
									}
								}
							}
						}
						uniqueIdForqAtXY++;
					}
				}
			}
		}
		
		return(getRidOfEmptySubsets(result));
	}
	
	//Get rid of all the (translated) copies of any polyomino in l
	public static PolyominoList getRidOfFixedDoubles(LinkedList<Polyomino> l){
		PolyominoList result = new PolyominoList();
		for(Polyomino p : l){
			boolean isAlreadyPresent = false;
			for(Polyomino q : result){
				if(q.equals(p))
					isAlreadyPresent = true;
			}
			if(!isAlreadyPresent)
				result.add(p);
		}
		return result;
	}
	
	//Delete all the lines filled with 0 in a matrix m, which represent an empty set in an exact cover problem
	public static int[][] getRidOfEmptySubsets(int[][] m){
		LinkedList<Integer> notZeroLines = new LinkedList<Integer>();
		for(int i = 0; i < m.length; i++){
			boolean lineFilledWithZeroes = true;
			int j = 0;
			while(j < m[0].length && lineFilledWithZeroes){
				if(m[i][j] != 0)
					lineFilledWithZeroes = false;
				j++;
			}
			if(!lineFilledWithZeroes){
				notZeroLines.add(i);
			}
		}
		int[][] result = new int[notZeroLines.size()][m[0].length];
		int i = 0;
		for(Integer line : notZeroLines){
			result[i] = m[line];
			i++;
		}
		
		return result;
	}
	
	//Should be transfered to Polyomino.java
	public static Polyomino polyominoFromPoints(LinkedList<Point> points){
		return (new Polyomino(points));
	}
	
	//Returns a set of all solutions of a polyomino tiling problem.
	//Here, we try to cover P with all polyominoes of l.
	//The polyomino returned are correctly positioned to cover exactly P when drawn.
	public static LinkedList<LinkedList<Polyomino>> polyominoTiling(Polyomino P, Polyomino[] l, boolean useExactlyOnce, boolean useRotations, boolean useReflections){
		HashMap<Integer,Point> pointId = new HashMap<Integer,Point>();
		int[][] m = tilingToExactCover(P, l, useExactlyOnce, useRotations, useReflections, pointId);
		
		LinkedList<LinkedList<LinkedList<Integer>>> covers = exactCover(dancingLinksStructureOf(m));
		LinkedList<LinkedList<Polyomino>> solutions = new LinkedList<LinkedList<Polyomino>>();
		for(LinkedList<LinkedList<Integer>> cover : covers){
			LinkedList<Polyomino> solution = new LinkedList<Polyomino>();
			for(LinkedList<Integer> Elt : cover){
				LinkedList<Point> points = new LinkedList<Point>();
				for(Integer id : Elt){
					Point p = pointId.get(id-1);
					if(p != null)
						points.add(p);
				}
				Point translation = P.bottomLeftCorner();
				solution.add(polyominoFromPoints(points).translate(translation.x, translation.y));
			}
			solutions.add(solution);
		}
		return solutions;
	}
	
	public static void drawPolyomino(Polyomino P, Color c, Image2d i, Point origin, int squareSize){
		for(Polygon p : P.toPolygons(origin, squareSize))
			i.addPolygon(p.xpoints, p.ypoints, c);
	}
	
	public static void testTask8(){
		Polyomino p1 = new Polyomino("(0,0), (1,0), (0,1)");
		Polyomino p2 = new Polyomino("(0,0)");
		Polyomino P = new Polyomino("(0,0), (0,1), (1,1), (1,0), (2,0), (2,1)");
		int[][] matrix = tilingToExactCover(P, new Polyomino[] {p1, p2}, false, true, false, new HashMap<Integer,Point>());
		printMatrix(matrix);
		LinkedList<LinkedList<Polyomino>> solutions = polyominoTiling(P, new Polyomino[] {p1, p2}, false, true, false);
		
		drawSolutions(P, solutions);
	}
	
	public static void drawSolutions(Polyomino P, LinkedList<LinkedList<Polyomino>> solutions){
		drawSolutions(P, solutions, Integer.MAX_VALUE);
	}
	
	//Draws a solutions of a tiling problem of a polyomino P. maxLength is the maximum number of squares
	//maxLength is the maximum number of squares that can be drawn on your screen in a line, squareSize is the size of such a square
	//squareSize = 20 by default if no parameter is given
	public static void drawSolutions(Polyomino P, LinkedList<LinkedList<Polyomino>> solutions, int maxLength, int squareSize){
		int height = P.height();
		int length = P.length();
		int nbHztal = maxLength/(length+1);
		Point returnTranslation = new Point(-nbHztal*(length+1), height+1);
		Point origin = new Point(1,height+1);
		drawSolutions(solutions, origin, new Point(length + 1, 0), nbHztal, returnTranslation, squareSize);
	}
	
	public static void drawSolutions(Polyomino P, LinkedList<LinkedList<Polyomino>> solutions, int maxLength){
		drawSolutions(P, solutions, maxLength, 20);
	}
	
	//If l contains different polyominoes you want to draw but not on each other, you should use this function.
	//maxLength is the maximum number of squares that can be drawn on your screen in a line, squareSize is the size of such a square
	//squareSize = 20 by default if no parameter is given
	public static void drawDifferentPolyominoes(LinkedList<Polyomino> l, int maxLength, int squareSize){
		int height = 0;
		int length = 0;
		for(Polyomino P : l){
			int h_p = P.height();
			int l_p = P.length();
			if(h_p > height)
				height = h_p;
			if(l_p > length)
				length = l_p;
		}

		int nbHztal = maxLength/(length+1);
		Point returnTranslation = new Point(-nbHztal*(length+1), height+1);
		Point origin = new Point(1,height+1);
		LinkedList<LinkedList<Polyomino>> solutions = new LinkedList<LinkedList<Polyomino>>();
		for(Polyomino p : l){
			LinkedList<Polyomino> u = new LinkedList<Polyomino>();
			u.add(p);
			solutions.add(u);
		}
		drawSolutions(solutions, origin, new Point(length + 1, 0), nbHztal, returnTranslation, squareSize);
	}

	public static void drawDifferentPolyominoes(LinkedList<Polyomino> l, int maxLength){
		drawDifferentPolyominoes(l, maxLength, 20);
	}

	public static void drawSolutions(LinkedList<LinkedList<Polyomino>> solutions, Point origin, Point translation, int nbHztal, Point returnTranslation){
		drawSolutions(solutions, origin, translation, nbHztal, returnTranslation, 20);
	}
	
	//Draws a solution at origin than draws the next solution at origin + translation until the end
	public static void drawSolutions(LinkedList<LinkedList<Polyomino>> solutions, Point origin, Point translation, int nbHztal, Point returnTranslation, int squareSize){
		Image2d i = new Image2d(5,5);
		Random r = new Random();
		int n = 0;
		for(LinkedList<Polyomino> solution : solutions){
			if(n == nbHztal){
				origin.x+=returnTranslation.x;
				origin.y+=returnTranslation.y;
				n = 0;
			}
			
			for(Polyomino p : solution)
				drawPolyomino(p, new Color(r.nextFloat(),r.nextFloat(),r.nextFloat()),i,origin, squareSize);
			
			origin.x += translation.x;
			origin.y += translation.y;

			n++;
		}
		Image2dViewer v = new Image2dViewer(i);
	}
	
	public static void drawPolyomino(Polyomino P, Point origin){
		drawPolyomino(P, origin, 20);
	}
	
	//This can be used to draw a single Polyomino at the given origin ( (0,0) is the Up-Left corner of your screen here). squareSize is the size of a square
	//squareSize = 20 by default if no parameter is given.
	public static void drawPolyomino(Polyomino P, Point origin, int squareSize){
		Image2d i = new Image2d(5,5);
		Random r = new Random();
		drawPolyomino(P,new Color(r.nextFloat(),r.nextFloat(),r.nextFloat()),i,origin, squareSize);
		Image2dViewer v = new Image2dViewer(i);
	}
	
	//Task 8c
	//Returns all polyomino P of size n which can cover their own dilate kP
	public static LinkedList<Polyomino> coverOwnDilate(int n, int k){
		MonFree freeP = new MonFree();
		freeP.Count(n);
		LinkedList<Polyomino> polyominoesSizeN = freeP.polyominoList;
		LinkedList<Polyomino> result = new LinkedList<Polyomino>();
		for(Polyomino p : polyominoesSizeN){
			if(polyominoTiling(p.dilate(k), new Polyomino[] {p}, false, true, true).size()>0)
				result.add(p);
		}
		return result;
	}
	
	public static void testTask8a(){
		PolyominoList figure5 = new PolyominoList("polyominoesFigure5.txt");
		Polyomino figure5a = figure5.get(0);
		Polyomino figure5b = figure5.get(1);
		Polyomino figure5c = figure5.get(2);
		MonFree testFree = new MonFree();
		testFree.Count(5);
		Polyomino[] freePentominoes = testFree.polyominoList.toArray(new Polyomino[testFree.polyominoList.size()]);
		long t0 = System.currentTimeMillis();
		drawDifferentPolyominoes(testFree.polyominoList,55);
		System.out.println("Starting..");
		LinkedList<LinkedList<Polyomino>> solutions = polyominoTiling(figure5a, freePentominoes ,true,true,true);
		System.out.println("Number of solutions : " + solutions.size());
		drawSolutions(figure5a, solutions, 340, 4);
		System.out.println("END - " + (System.currentTimeMillis()-t0));
	}
	
	public static void testTask8b(){
		Polyomino rect = Polyomino.rectanglePolyomino(7, 4);
		MonOneSided t = new MonOneSided();
		t.Count(4);
		Polyomino[] oneSidedTetrominoes = t.polyominoList.toArray(new Polyomino[t.polyominoList.size()]);
		drawDifferentPolyominoes(t.polyominoList, 55);
		System.out.println(t.count);
		System.out.println("Beginning task 8b");
		long t0 = System.currentTimeMillis();
		System.out.println("Starting..");
		LinkedList<LinkedList<Polyomino>> solutions = polyominoTiling(rect, oneSidedTetrominoes, false, true, false);
		System.out.println("Nb of solutions : " + solutions.size());
		drawSolutions(rect, solutions, 340, 4);
		System.out.println("END - " + (System.currentTimeMillis()-t0));
		
	}
	
	public static void testTask8c(){
		//System.out.println("Test task8c ->" + coverOwnDilate(8,4));
		drawDifferentPolyominoes(coverOwnDilate(5,4), 50); //We find what is displayed on Figure 1
	}
}
