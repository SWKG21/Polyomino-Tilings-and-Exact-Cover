import java.util.*;

public class Ring{
	
	LinkedList<Polyomino> ring;
	int count;
	
	public Ring(int m){
		Stack<Point> untried = new Stack<Point>();
		untried.add(new Point(0, 0));    //start from (0,0) but (0,0) is vide
		Ring(null, untried, m);
	}
	
	
	public void Ring(Polyomino parent, Stack<Point> untried, int m){
		LinkedList<Polyomino> ring = new LinkedList<Polyomino>();
		while(!untried.isEmpty()){
			if(parent == null){
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
				if(size<m-2){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFreeForRing(parent)){                          //change the rule of free
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
					Ring(new_parent, new_untried, m);
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
					System.out.println("No ring for m="+m);
				}
				//step 5: remove newest cell		
				occupied.remove(point);
			}
			else if(parent.xcoords[0]!=0 && parent.ycoords[0]!=0){
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
				new_xcoords[1] = -point.x;
				new_ycoords[1] = -point.y;
				Polyomino child = new Polyomino(new_xcoords, new_ycoords);
				occupied.add(point);
				occupied.add(imagepoint);
				
				//step 3: count this new Polyomino
				
				/*//for test
				for(int i=0; i<child.xcoords.length; i++){
					System.out.print("("+child.xcoords[i]+","+child.ycoords[i]+")");
				}
				System.out.println();*/
				
				int size = 0;                                                       //don't count (0,0)
				if(size<m-2){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFreeForRing(parent)){                          //change the rule of free and only point no imagepoint
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
					Ring(new_parent, new_untried, m);
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
					System.out.println("No ring for m="+m);
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
				for(int i=0; i<new_xcoords.length-2; i++){
					new_xcoords[i] = parent.xcoords[i];
					new_ycoords[i] = parent.ycoords[i];
				}
				new_xcoords[size] = point.x;
				new_ycoords[size] = point.y;
				new_xcoords[size+1] = -point.x;
				new_ycoords[size+1] = -point.y;
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
				if(size<m-2){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFreeForRing(parent)){                              //change the rule of free
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
					Ring(new_parent, new_untried, m);
					
					//step 4c: remove new neighbours from the untried set
					/*for(Point neighbour : point.neighbours()){
						if(neighbour.isFree(parent))
							untried.remove(neighbour);//????????? here the problem
					}*/                                                                  //pourquoi ici marche pas
					for(int i=0; i<nbrAdd; i++)
						untried.pop();
				}
				else{
					if(child.isRing()){
						this.ring.add(child);
						this.count++;
					}		
				}
				
				//step 5: remove newest cell
				occupied.remove(imagepoint);
				occupied.remove(point);
			}
	}
	}
}
