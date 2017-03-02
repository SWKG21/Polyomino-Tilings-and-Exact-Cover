import java.util.*;

public class HIp {
	//the same as Fixed except size and position of P
	int count;
	LinkedList<Polyomino> polyominoList;
	
	public void Count(Polyomino parent, Stack<Point> untried, int p){
		
		while(!untried.isEmpty()){
			if(parent == null){
				LinkedList<Point> occupied = Point.pointsOfPolyomino(parent);
				//step 1: remove the newest element of untried set
				Point point = untried.pop();
				
				//step 2: place a cell at this point and form a new Polyomino
				int[] new_xcoords = new int[1];
				int[] new_ycoords = new int[1];
				new_xcoords[0] = point.x;
				new_ycoords[0] = point.y;
				Polyomino child = new Polyomino(new_xcoords, new_ycoords);
				occupied.add(point);
				
				//for modify times of recursion                                        //here the different size
				int m = 0;
				if(point.y == 0)
					m = 1;
				else
					m = 2;
				
				//step 3: count this new Polyomino
				
				//step 4
				int size = 0;
				if(size<p-m){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFree(parent)){
							untried.add(neighbour);
							nbrAdd++;
						}
					}
					
					//step 4b: new parent, copy untried set and recursion
					Polyomino new_parent = child;
					Stack<Point> new_untried = new Stack<Point>();
					for(Point e : untried){
						new_untried.add(e);
					}
					Count(new_parent, new_untried, p);
					//step 4c: remove new neighbours from the untried set
					for(int i=0; i<nbrAdd; i++)
						untried.pop();
				}
				else if(size>p-m){
					;
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
				
				//step 1: remove the newest element of untried set
				Point point = untried.pop();
				
				//step 2: place a cell at this point and form a new Polyomino
				
				int size = 0;                                                        //here the different size
				for(int i=0; i<parent.ycoords.length; i++){
					if(parent.ycoords[i] == 0)
						size++;
					else
						size = size+2;
				}

				int[] new_xcoords = new int[parent.ycoords.length+1];
				int[] new_ycoords = new int[parent.ycoords.length+1];
				for(int i=0; i<new_xcoords.length-1; i++){
					new_xcoords[i] = parent.xcoords[i];
					new_ycoords[i] = parent.ycoords[i];
				}
				new_xcoords[parent.ycoords.length] = point.x;
				new_ycoords[parent.ycoords.length] = point.y;
				Polyomino child = new Polyomino(new_xcoords, new_ycoords);
				occupied.add(point);
					
				//for modify times of recursion                                     //here the different size
				int m = 0;
				if(point.y == 0)
					m = 1;
				else
					m = 2;
				//step 3: count this new Polyomino
				
				//step 4:
				if(size<p-m){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFree(parent)){
							untried.add(neighbour);
							nbrAdd++;
						}
					}
					
					//step 4b: new parent, copy untried set and recursion
					Polyomino new_parent = child;
					Stack<Point> new_untried = new Stack<Point>();
					for(Point e : untried){
						new_untried.add(e);
					}
					Count(new_parent, new_untried, p);
					
					//step 4c: remove new neighbours from the untried set
					for(int i=0; i<nbrAdd; i++)
						untried.pop();
				}
				else if(size>p-m){
					;
				}
				else{//only when childsize(size+m)=p it works
					this.polyominoList.add(child);
					this.count++;
				}
				
				//step 5: remove newest cell
				occupied.remove(point);
			}	
		}
	}
	
	
	public void Count(int p){
		this.polyominoList = new LinkedList<Polyomino>();
		Stack<Point> untried = new Stack<Point>();
		untried.add(new Point(0, 0));
		Count(null, untried, p);
		/*//for test
		for(Polyomino l : this.polyominoList){
			for(int i=0; i<l.xcoords.length; i++){
				System.out.print("("+l.xcoords[i]+","+l.ycoords[i]+")");
			}
			System.out.println();
		}*/
	}	

}
