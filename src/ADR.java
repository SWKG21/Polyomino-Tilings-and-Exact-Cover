import java.util.*;

public class ADR {       //Ap=A+ADR+HVADR2=2diag+2diag2+all    ADR=diag2
	
	int count;
	LinkedList<Polyomino> polyominoList;
	
	public void Count(Polyomino parent, Stack<Point> untried, int p){
		while(!untried.isEmpty()){
			if(parent == null){
				LinkedList<Point> occupied = Point.pointsOfPolyomino(parent);
				//step 1: remove the newest element of untried set
				Point point = untried.pop();
				Point imagepoint = new Point(point.y, point.x);            //imagepoint.x=point.y, imagepoint.y=point.x
				
				//step 2: place a cell at this point and form a new Polyomino
				Polyomino child = new Polyomino(null, null);
				if(point.x != point.y){
					int[] new_xcoords = new int[2];
					int[] new_ycoords = new int[2];
					new_xcoords[0] = point.x;
					new_ycoords[0] = point.y;
					new_xcoords[1] = imagepoint.x;
					new_ycoords[1] = imagepoint.y;
					child = new Polyomino(new_xcoords, new_ycoords);
					occupied.add(point);
					occupied.add(imagepoint);
				}
				else{
					int[] new_xcoords = new int[1];
					int[] new_ycoords = new int[1];
					new_xcoords[0] = point.x;
					new_ycoords[0] = point.y;
					child = new Polyomino(new_xcoords, new_ycoords);
					occupied.add(point);
				}
				
				
				
				//for modify times of recursion                                  //here the different size
				int m = 0;
				if(point.y == point.x)
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
						if(neighbour.isFreeForAp(parent)){                           //here change the rule
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
				else if(size>p-m)
					;
				else{
					if(child.Dequals(child) && !child.Hequals(child)){                 //contain D, not H
						if(this.polyominoList.isEmpty()){                              //keep one each
							this.polyominoList.add(child);
							this.count++;
						}
						else{
							Iterator<Polyomino> it = this.polyominoList.iterator();
							while(it.hasNext()){
								Polyomino tmp = it.next();
								if(child.Vequals(tmp)){
									it.remove();
									this.count--;
								}
						    }
							this.polyominoList.add(child);
							this.count++;
						}
					}
				}
				
				//step 5: remove newest cell
				if(point.x != point.y)
					occupied.remove(imagepoint);
				occupied.remove(point);
					
			}
			else{
				LinkedList<Point> occupied = Point.pointsOfPolyomino(parent);
				
				//step 1: remove the newest element of untried set
				Point point = untried.pop();
				Point imagepoint = new Point(point.y, point.x);
				
				//step 2: place a cell at this point and form a new Polyomino
				Polyomino child = new Polyomino(null, null);
				int size = parent.ycoords.length;                                      //size different
                if(point.x != point.y){
                	int[] new_xcoords = new int[parent.ycoords.length+2];
    				int[] new_ycoords = new int[parent.ycoords.length+2];
    				for(int i=0; i<new_xcoords.length-2; i++){
    					new_xcoords[i] = parent.xcoords[i];
    					new_ycoords[i] = parent.ycoords[i];
    				}
    				new_xcoords[parent.ycoords.length] = point.x;
    				new_ycoords[parent.ycoords.length] = point.y;
    				new_xcoords[parent.ycoords.length+1] = imagepoint.x;
    				new_ycoords[parent.ycoords.length+1] = imagepoint.y;
    				child = new Polyomino(new_xcoords, new_ycoords);
    				occupied.add(point);
    				occupied.add(imagepoint);
                }
                else{
                	int[] new_xcoords = new int[parent.ycoords.length+1];
    				int[] new_ycoords = new int[parent.ycoords.length+1];
    				for(int i=0; i<new_xcoords.length-1; i++){
    					new_xcoords[i] = parent.xcoords[i];
    					new_ycoords[i] = parent.ycoords[i];
    				}
    				new_xcoords[parent.ycoords.length] = point.x;
    				new_ycoords[parent.ycoords.length] = point.y;
    				child = new Polyomino(new_xcoords, new_ycoords);
    				occupied.add(point);
                }
				
				
				//for modify times of recursion                               //here the different size
				int m = 0;
				if(point.y == point.x)
					m = 1;
				else
					m = 2;
				
				//step 3: count this new Polyomino
				
				//step 4:
				if(size<p-m){
					//step 4a: add new neighbours to the untried set
					int nbrAdd = 0;
					for(Point neighbour : point.neighbours()){
						if(neighbour.isFreeForAp(parent)){                              //here change the rule
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
					if(child.Dequals(child) && !child.Hequals(child)){                         //contai D, not H
						if(this.polyominoList.isEmpty()){                                      //keep one each
							this.polyominoList.add(child);
							this.count++;
						}
						else{
							Iterator<Polyomino> it = this.polyominoList.iterator();
							while(it.hasNext()){
								Polyomino tmp = it.next();
								if(child.Vequals(tmp)){
									it.remove();
									this.count--;
								}
						    }
							this.polyominoList.add(child);
							this.count++;
						}
					}
				}
				
				//step 5: remove newest cell
				if(point.x != point.y)
					occupied.remove(imagepoint);
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
