import java.util.LinkedList;

public class Point {
	int x;
	int y;
	Point next;
	
	//for testFix(modified)
	public Point(int x, int y, Point next){
		this.x = x;
		this.y = y;
		this.next = next;
	}
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
		this.next = null;//for testFix(modified)
	}
	
	
	
	public Point[] neighbours(){
		Point[] neighbours = {new Point(this.x-1, this.y),new Point(this.x+1, this.y),
                              new Point(this.x, this.y-1),new Point(this.x, this.y+1)};
		return neighbours;
	}
	
	
	
	public static LinkedList<Point> pointsOfPolyomino(Polyomino p){
		LinkedList<Point> points = new LinkedList<Point>();
		if(p != null){
			for(int i=0; i<p.xcoords.length; i++)
				points.add(new Point(p.xcoords[i], p.ycoords[i]));
		}
		return points;
	}
	
	
	
	public boolean isBorder(){
		if(this.y<0 || (this.y==0 && this.x<0))
			return true;
		return false;
	}
	
	
	
	public boolean isOccupiedBy(Polyomino p){
		LinkedList<Point> occupied = pointsOfPolyomino(p);
		for(Point e : occupied)
			if(e.x==this.x && e.y==this.y)
				return true;
		return false;
	}
	
	
	
	public boolean isReachableOf(Polyomino p){
		LinkedList<Point> reachable = new LinkedList<Point>();
		for(Point point : pointsOfPolyomino(p)){
			for(Point neighbour : point.neighbours()){
				if(!neighbour.isBorder() && !neighbour.isOccupiedBy(p))
					reachable.add(neighbour);
			}	
		}
		for(Point e : reachable)
			if(e.x==this.x && e.y==this.y)
				return true;
		return false;
	}
	
	
	
	public boolean isFree(Polyomino p){
		if(!this.isBorder() && !this.isOccupiedBy(p) && !this.isReachableOf(p))
			return true;
		return false;
	}
	
	
	
	public boolean equals(Point p){
		return this.x==p.x && this.y==p.y;
	}
	
	
	
	//for Ap
	public boolean isBorderForAp(){
		if(this.y<this.x || (this.y==this.x && this.x<0))
			return true;
		return false;
	}
	
	
	//for Ap
	public boolean isReachableForApOf(Polyomino p){
		LinkedList<Point> reachable = new LinkedList<Point>();
		for(Point point : pointsOfPolyomino(p)){
			for(Point neighbour : point.neighbours()){
				if(!neighbour.isBorderForAp() && !neighbour.isOccupiedBy(p))
					reachable.add(neighbour);
			}	
		}
		for(Point e : reachable)
			if(e.x==this.x && e.y==this.y)
				return true;
		return false;
	}
	
	
	
	//for Ap
	public boolean isFreeForAp(Polyomino p){
		if(!this.isBorderForAp() && !this.isOccupiedBy(p) && !this.isReachableForApOf(p))
			return true;
		return false;
	}
	
	
	
	//for Rp
	public boolean isFreeForRp(Polyomino p){
		if(!this.isOccupiedBy(p) && !this.isReachableOf(p))
			return true;
		return false;
	}
	
	
	
	//for Ring
	public boolean isBorderForRing(){
		if(this.y<0 || (this.y==0 && this.x<=0))
			return true;
		return false;
	}
	
	
	
	//for Ring
	public boolean isFreeForRing(Polyomino p){
		if(!this.isBorderForRing() && !this.isOccupiedBy(p) && !this.isReachableOf(p))
			return true;
		return false;
	}

}
