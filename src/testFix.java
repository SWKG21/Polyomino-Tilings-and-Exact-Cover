import java.util.*;

public class testFix { //for improving on the Fix, but it doesn't work better

	int count;
	LinkedList<Polyomino> polyominoList;
	
	public void Count(Polyomino parent, Point untried, int p){                    //untried is different
		
		while(untried != null){
			if(parent == null){
				LinkedList<Point> occupied = Point.pointsOfPolyomino(parent);
				//step 1: remove the newest element of untried set
				Point point = untried;
				untried = untried.next;
				
				//step 2: place a cell at this point and form a new Polyomino
				int[] new_xcoords = new int[1];
				int[] new_ycoords = new int[1];
				new_xcoords[0] = point.x;
				new_ycoords[0] = point.y;
				Polyomino child = new Polyomino(new_xcoords, new_ycoords);
				occupied.add(point);
				
				//step 3: count this new Polyomino
				
				//step 4
				int size = 0;
				if(size<p-1){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFree(parent)){
							neighbour.next = untried;
							untried = neighbour;
							nbrAdd++;
						}
					}
					
					//step 4b: new parent, copy untried set and recursion
					Polyomino new_parent = child;
					Count(new_parent, untried, p);
					//step 4c: remove new neighbours from the untried set
					for(int i=0; i<nbrAdd; i++)
						untried = untried.next;
				}
				else if(size>p-1)
					;
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
				Point point = untried;
				untried = untried.next;
				
				//step 2: place a cell at this point and form a new Polyomino
				int size = parent.xcoords.length;
				int[] new_xcoords = new int[size+1];
				int[] new_ycoords = new int[size+1];
				for(int i=0; i<new_xcoords.length-1; i++){
					new_xcoords[i] = parent.xcoords[i];
					new_ycoords[i] = parent.ycoords[i];
				}
				new_xcoords[size] = point.x;
				new_ycoords[size] = point.y;
				Polyomino child = new Polyomino(new_xcoords, new_ycoords);
				occupied.add(point);
				
				//step 3: count this new Polyomino
				
				//step 4:
				if(size<p-1){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFree(parent)){
							neighbour.next = untried;
							untried = neighbour;
							nbrAdd++;
						}
					}
					
					//step 4b: new parent, copy untried set and recursion
					Polyomino new_parent = child;
					Count(new_parent, untried, p);
					
					//step 4c: remove new neighbours from the untried set
					for(int i=0; i<nbrAdd; i++)
						untried = untried.next;
				}
				else if(size>p-1)
					;
				else{
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
		Point untried = new Point(0, 0, null);
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
