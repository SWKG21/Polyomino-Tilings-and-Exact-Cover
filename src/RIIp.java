import java.util.*;

public class RIIp {
	int count;
	LinkedList<Polyomino> polyominoList;
	
	public void oddSizeCount(Polyomino parent, Stack<Point> untried, int p){
		this.polyominoList = new LinkedList<Polyomino>();
		while(!untried.isEmpty()){
			if(parent == null){
				LinkedList<Point> occupied = Point.pointsOfPolyomino(parent);
				//step 1: remove the newest element of untried set
				Point point = untried.pop();
				Point imagepoint = new Point(-point.x, -point.y);
				/*//for test
				System.out.println(untried.size());*/
				
				//step 2: place a cell at this point and form a new Polyomino
				int[] new_xcoords = new int[2];
				int[] new_ycoords = new int[2];
				new_xcoords[0] = point.x;
				new_ycoords[0] = point.y;
				new_xcoords[1] = imagepoint.x;
				new_ycoords[1] = imagepoint.y;
				Polyomino child = new Polyomino(new_xcoords, new_ycoords);
				occupied.add(point);
				occupied.add(imagepoint);
				
				//step 3: count this new Polyomino
				
				/*//for test
				for(int i=0; i<child.xcoords.length; i++){
					System.out.print("("+child.xcoords[i]+","+child.ycoords[i]+")");
				}
				System.out.println();*/
				
				int size = 0;
				if(size<p-2){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFreeForRp(parent)){                            //change the rule of free
							/*//for test
							System.out.print(neighbour.isFree(parent));*/
							untried.add(neighbour);
							nbrAdd++;
						}
					}
					for(Point imageneighbour : imagepoint.neighbours()){
						if(imageneighbour.isFreeForRp(parent)){                       //change the rule of free
							untried.add(imageneighbour);
							nbrAdd++;
						}
					}
					/*//for test
					System.out.println(nbrAdd);*/
					
					//step 4b: new parent, copy untried set and recursion
					Polyomino new_parent = child;
					Stack<Point> new_untried = new Stack<Point>();
					for(Point e : untried){
						new_untried.add(e);
					}
					oddSizeCount(new_parent, new_untried, p);
					//step 4c: remove new neighbours from the untried set
					/*for(Point neighbour : point.neighbours()){
						if(neighbour.isFree(parent))
						System.out.println(untried.remove(neighbour));
					}*/                                                                  //pourquoi ici remove pas???
					for(int i=0; i<nbrAdd; i++)
						untried.pop();
					/*//for test
					System.out.println(untried.size());*/
				}
				else{
					this.polyominoList.add(child);
					this.count++;
				}
				//step 5: remove newest cell
				occupied.remove(imagepoint);		
				occupied.remove(point);
			}
			else{
				LinkedList<Point> occupied = Point.pointsOfPolyomino(parent);
				/*//for test
				System.out.println("occupied size:"+occupied.size());*/
				
				//step 1: remove the newest element of untried set
				Point point = untried.pop();
				Point imagepoint = new Point(-point.x, -point.y);
				/*//for test
				System.out.println("untried size after pop:"+untried.size());*/
				
				//step 2: place a cell at this point and form a new Polyomino
				int size = parent.xcoords.length;
				int[] new_xcoords = new int[size+2];
				int[] new_ycoords = new int[size+2];
				for(int i=0; i<new_xcoords.length-1; i++){
					new_xcoords[i] = parent.xcoords[i];
					new_ycoords[i] = parent.ycoords[i];
				}
				new_xcoords[size] = point.x;
				new_ycoords[size] = point.y;
				new_xcoords[size+1] = imagepoint.x;
				new_ycoords[size+1] = imagepoint.y;
				Polyomino child = new Polyomino(new_xcoords, new_ycoords);
				occupied.add(point);
				occupied.add(imagepoint);
				
				//step 3: count this new Polyomino
				
				/*//for test
				for(int i=0; i<child.xcoords.length; i++){
					System.out.print("("+child.xcoords[i]+","+child.ycoords[i]+")");
				}
				System.out.println();*/
				
				//step 4:
				if(size<p-2){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFreeForRp(parent)){                              //change the rule of free
							/*//for test
							System.out.print(neighbour.isFree(parent));*/
							untried.add(neighbour);
							/*//for test
							System.out.println("parent"+parent.xcoords[0]+","+parent.ycoords[0]);
							System.out.println(neighbour.isOccupiedBy(parent));
							System.out.println("("+neighbour.x+","+neighbour.y+")");*/
							nbrAdd++;
						}
					}
					for(Point imageneighbour : imagepoint.neighbours()){
						if(imageneighbour.isFreeForRp(parent)){                        //change the rule of free
							untried.add(imageneighbour);
							nbrAdd++;
						}
					}
					/*//for test
					System.out.println(nbrAdd);*/
					
					//step 4b: new parent, copy untried set and recursion
					Polyomino new_parent = child;
					Stack<Point> new_untried = new Stack<Point>();
					for(Point e : untried){
						new_untried.add(e);
					}
					oddSizeCount(new_parent, new_untried, p);
					
					//step 4c: remove new neighbours from the untried set
					/*for(Point neighbour : point.neighbours()){
						if(neighbour.isFree(parent))
							untried.remove(neighbour);//????????? here the problem
					}*/                                                                  //pourquoi ici marche pas
					for(int i=0; i<nbrAdd; i++)
						untried.pop();
				}
				else{
					this.polyominoList.add(child);
					this.count++;
				}
				
				//step 5: remove newest cell
				occupied.remove(imagepoint);
				occupied.remove(point);
			}	
		}
		/*//for test
		for(Polyomino l : this.polyominoList)
			for(int i=0; i<l.xcoords.length; i++){
				System.out.print("("+l.xcoords[i]+","+l.ycoords[i]+")");
			}
			System.out.println();*/
	}
	
	
	public void oddSizeCount(int p){
		Stack<Point> untried = new Stack<Point>();
		untried.add(new Point(0, 0));
		oddSizeCount(null, untried, p);
	}
	
	
	
	
	
	
	
	public void evenSizeCount(int p){
		this.polyominoList = new LinkedList<Polyomino>();
		//untried is not true. to modify
		Stack<Point> untried = new Stack<Point>(); 
		untried.add(new Point(0, 0));
		for(int i=0; i<p; i++){
			Ring R = new Ring(i);
			for(Polyomino parent : R.ring)
				evenSizeCount(parent, untried, p);
		}
	}
	
	
	public void evenSizeCount(Polyomino parent, Stack<Point> untried, int p){
		while(!untried.isEmpty()){
			if(parent == null || (parent.xcoords[0]!=0 && parent.ycoords[0]!=0)){
				LinkedList<Point> occupied = Point.pointsOfPolyomino(parent);
				//step 1: remove the newest element of untried set
				Point point = untried.pop();
				/*//for test
				System.out.println(untried.size());*/
				
				//step 2: place a cell at this point and form a new Polyomino
				int[] new_xcoords = new int[1];
				int[] new_ycoords = new int[1];
				new_xcoords[0] = point.x;
				new_ycoords[0] = point.y;
				Polyomino child = new Polyomino(new_xcoords, new_ycoords);
				occupied.add(point);
				
				//step 3: count this new Polyomino
				
				/*//for test
				for(int i=0; i<child.xcoords.length; i++){
					System.out.print("("+child.xcoords[i]+","+child.ycoords[i]+")");
				}
				System.out.println();*/
				
				int size = 0;
				if(size<p-2){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFreeForRp(parent)){                          //change the rule of free
							/*//for test
							System.out.print(neighbour.isFree(parent));*/
							untried.add(neighbour);
							nbrAdd++;
						}
					}
					/*//for test
					System.out.println(nbrAdd);*/
					
					//step 4b: new parent, copy untried set and recursion
					Polyomino new_parent = child;
					Stack<Point> new_untried = new Stack<Point>();
					for(Point e : untried){
						new_untried.add(e);
					}
					evenSizeCount(new_parent, new_untried, p);
					//step 4c: remove new neighbours from the untried set
					/*for(Point neighbour : point.neighbours()){
						if(neighbour.isFree(parent))
						System.out.println(untried.remove(neighbour));
					}*/                                                             //pourquoi ici remove pas???
					for(int i=0; i<nbrAdd; i++)
						untried.pop();
					/*//for test
					System.out.println(untried.size());*/
				}
				else{
					this.polyominoList.add(child);
					this.count++;
				}
				//step 5: remove newest cell		
				occupied.remove(point);
			}
			else{
				LinkedList<Point> occupied = Point.pointsOfPolyomino(parent);
				/*//for test
				System.out.println("occupied size:"+occupied.size());*/
				
				//step 1: remove the newest element of untried set
				Point point = untried.pop();
				/*//for test
				System.out.println("untried size after pop:"+untried.size());*/
				
				//step 2: place a cell at this point and form a new Polyomino
				int size = 2*parent.xcoords.length;                             //don't count (0,0) in size nor in new Polyomino
				int[] new_xcoords = new int[size/2+1];
				int[] new_ycoords = new int[size/2+1];
				for(int i=0; i<new_xcoords.length-1; i++){
					if(parent.xcoords[i]!=0 && parent.ycoords[i]!=0){
						new_xcoords[i] = parent.xcoords[i];
						new_ycoords[i] = parent.ycoords[i];
					}
					else{
						new_xcoords[size] = point.x;
						new_ycoords[size] = point.y;
					}
				}
				Polyomino child = new Polyomino(new_xcoords, new_ycoords);
				occupied.add(point);
				
				//step 3: count this new Polyomino
				
				/*//for test
				for(int i=0; i<child.xcoords.length; i++){
					System.out.print("("+child.xcoords[i]+","+child.ycoords[i]+")");
				}
				System.out.println();*/
				
				//step 4:
				if(size<p-2){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFreeForRp(parent)){                              //change the rule of free
							/*//for test
							System.out.print(neighbour.isFree(parent));*/
							untried.add(neighbour);
							/*//for test
							System.out.println("parent"+parent.xcoords[0]+","+parent.ycoords[0]);
							System.out.println(neighbour.isOccupiedBy(parent));
							System.out.println("("+neighbour.x+","+neighbour.y+")");*/
							nbrAdd++;
						}
					}
					/*//for test
					System.out.println(nbrAdd);*/
					
					//step 4b: new parent, copy untried set and recursion
					Polyomino new_parent = child;
					Stack<Point> new_untried = new Stack<Point>();
					for(Point e : untried){
						new_untried.add(e);
					}
					evenSizeCount(new_parent, new_untried, p);
					
					//step 4c: remove new neighbours from the untried set
					/*for(Point neighbour : point.neighbours()){
						if(neighbour.isFree(parent))
							untried.remove(neighbour);//????????? here the problem
					}*/                                                                  //pourquoi ici marche pas
					for(int i=0; i<nbrAdd; i++)
						untried.pop();
				}
				else{
					this.polyominoList.add(child);
					this.count++;
				}
				
				//step 5: remove newest cell
				occupied.remove(point);
			}
		}
	}

}
