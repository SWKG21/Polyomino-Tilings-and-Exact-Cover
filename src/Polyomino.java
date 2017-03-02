import java.awt.*;
import java.util.*;


public class Polyomino {
	int[] xcoords;
	int[] ycoords;
	
	
	//from two groups of coordinates
	public Polyomino(int[] xcoords, int[] ycoords){
		this.xcoords = xcoords;
		this.ycoords = ycoords;
	}
	
	
	//from string in format [(1,2), (3,4), (5,6)]
	public Polyomino(String t){
		String[] tab = t.replace("[","").replace("]","").replace("(","").replace(")","").split(", ");
		xcoords = new int[tab.length];
		ycoords = new int[tab.length];
		for(int i = 0; i < tab.length; i++){
			String[] coords = tab[i].split(",");
			xcoords[i] = Integer.parseInt(coords[0]);
			ycoords[i] = Integer.parseInt(coords[1]);
		}
	}
	
	
	//from Point[]
	public Polyomino(Point[] points){
		xcoords = new int[points.length];
		ycoords = new int[points.length];
		for(int i=0; i<points.length; i++){
			this.xcoords[i] = points[i].x;
			this.ycoords[i] = points[i].y;
		}
	}
	
	public Polyomino(LinkedList<Point> points){
		int n = points.size();
		this.xcoords = new int[n];
		this.ycoords = new int[n];
		int i = 0;
		for(Point p : points){
			if(p != null){
				xcoords[i] = p.x;
				ycoords[i] = p.y;
				i++;
			}
		}
	}
	
	//Returns a rectangle polyomino with given length and height
	public static Polyomino rectanglePolyomino(int length, int height){
		int[] xcoords = new int[length*height];
		int[] ycoords = new int[length*height];
		int i = 0;
		for(int x = 0; x < length; x++){
			for(int y = 0; y < height; y++){
				xcoords[i] = x;
				ycoords[i] = y;
				i++;
			}
		}
		return new Polyomino(xcoords, ycoords);
	}
	
	//Returns the (absolute) coordinates of the bottom left corner of a polyomino P
	public Point bottomLeftCorner(){
		int xmin = this.xcoords[0];
		int ymin = this.ycoords[0];
		for(int i = 0; i < this.xcoords.length; i++){
			if(this.xcoords[i] < xmin)
				xmin = this.xcoords[i];
			if(this.ycoords[i] < ymin)
				ymin = this.ycoords[i];
		}
		return new Point(xmin,ymin);
	}

	//Same with up right corner
	public Point upRightCorner(){
		int xmax = this.xcoords[0];
		int ymax = this.ycoords[0];
		for(int i = 0; i < this.xcoords.length; i++){
			if(this.xcoords[i] > xmax)
				xmax = this.xcoords[i];
			if(this.ycoords[i] > ymax)
				ymax = this.ycoords[i];
		}
		return new Point(xmax,ymax);
	}
	
	public int height(){
		return(this.upRightCorner().y-this.bottomLeftCorner().y+1);
	}
	
	public int length(){
		return(this.upRightCorner().x-this.bottomLeftCorner().x+1);
	}
	
	public LinkedList<Polygon> toPolygons(int height){ //Height of the image to invert the y-axis (in square size)
		return toPolygons(new Point(0, height));
	}
	

	public LinkedList<Polygon> toPolygons(Point origin){
		return toPolygons(origin, 20);
	}
	
	public LinkedList<Polygon> toPolygons(Point origin, int squareSize){
		LinkedList<Polygon> p = new LinkedList<Polygon>();
		int[] p_xcoords = new int[4];
		int[] p_ycoords = new int[4];
		
		for(int i = 0; i < xcoords.length; i++){
			p_xcoords[0] = (origin.x+xcoords[i])*squareSize;
			p_ycoords[0] = (origin.y-ycoords[i])*squareSize;

			p_xcoords[1] = (origin.x+xcoords[i]+1)*squareSize;
			p_ycoords[1] = (origin.y-ycoords[i])*squareSize;
			
			p_xcoords[2] = (origin.x+xcoords[i]+1)*squareSize;
			p_ycoords[2] = (origin.y-(ycoords[i]+1))*squareSize;
			
			p_xcoords[3] = (origin.x+xcoords[i])*squareSize;
			p_ycoords[3] = (origin.y-(ycoords[i]+1))*squareSize;
			
			p.add(new Polygon(p_xcoords,p_ycoords,4));
		}
		return (p);
	}
	
	
	
	
	//translate along with the vector(x,y)
	public Polyomino translate(int vx, int vy) { //translate by vector (vx,vy)
		int[] p_xcoords = new int[xcoords.length];
		int[] p_ycoords = new int[ycoords.length];
		
		for(int i = 0; i < xcoords.length; i++){
			p_xcoords[i] = xcoords[i] + vx;
			p_ycoords[i] = ycoords[i] + vy;
		}
		return (new Polyomino(p_xcoords,p_ycoords));
	}
	
	
	//rotate size quarter-turns clockwise(size>0, after toPolygon size<0) or counterclockwise(size<0, after toPloygon size>0)
	public Polyomino rotate(int size){
		//around the corner(min_x, min_y) and then translate
		if(size>=0){// here size is after toPolygon, size>0, counterclockwise
			int n = this.xcoords.length;
			
			// for finding the vector of translation
			int max_y = Integer.MIN_VALUE;
			int min_y = Integer.MAX_VALUE;
			for(int i=0; i<n; i++){
				if(this.ycoords[i]>max_y)
					max_y = this.ycoords[i];
				if(this.ycoords[i]<min_y)
					min_y = this.ycoords[i];
			}
			
			int max_x = Integer.MIN_VALUE;
			int min_x = Integer.MAX_VALUE;
			for(int i=0; i<n; i++){
				if(this.xcoords[i]>max_x)
					max_x = this.xcoords[i];
				if(this.xcoords[i]<min_x)
					min_x = this.xcoords[i];
			}
			
			int[] new_xcoords = new int[n];
			int[] new_ycoords = new int[n];
			
			//4 types of rotation
			if(size%4 == 0){
				for(int i=0; i<n; i++){
					new_xcoords[i] = this.xcoords[i];
					new_ycoords[i] = this.ycoords[i];
				}
				return new Polyomino(new_xcoords, new_ycoords);
			}
			else if(size%4 == 1){
				for(int i=0; i<n; i++){
					new_xcoords[i] = min_x+min_y-this.ycoords[i];
					new_ycoords[i] = -min_x+min_y+this.xcoords[i];
				}
				return new Polyomino(new_xcoords, new_ycoords).translate(max_y-min_y, 0);
			}
			else if(size%4 == 2){
				for(int i=0; i<n; i++){
					new_xcoords[i] = min_x-this.xcoords[i];
					new_ycoords[i] = min_y-this.ycoords[i];
				}
				return new Polyomino(new_xcoords, new_ycoords).translate(max_x-min_x, max_y-min_y);
			}
			else{
				for(int i=0; i<n; i++){
					new_xcoords[i] = min_x-min_y+this.ycoords[i];
					new_ycoords[i] = min_x+min_y-this.xcoords[i];
				}
				return new Polyomino(new_xcoords, new_ycoords).translate(0, max_x-min_x);
			}
		}
		else{
			return this.rotate(size+4);
		}
	}
	
	//reflect with respect to a coordinate axis
	public Polyomino reflect(String s){
		int n = this.xcoords.length;
		
		// for finding the horizontal or diagonal axis
		int max_y = Integer.MIN_VALUE;
		int min_y = Integer.MAX_VALUE;
		for(int i=0; i<n; i++){
			if(this.ycoords[i]>max_y)
				max_y = this.ycoords[i];
			if(this.ycoords[i]<min_y)
				min_y = this.ycoords[i];
		}
		
		//for finding the vertical or diagonal axis 
		int max_x = Integer.MIN_VALUE;
		int min_x = Integer.MAX_VALUE;
		for(int i=0; i<n; i++){
			if(this.xcoords[i]>max_x)
				max_x = this.xcoords[i];
			if(this.xcoords[i]<min_x)
				min_x = this.xcoords[i];
		}
		
		int[] new_xcoords = new int[n];
		int[] new_ycoords = new int[n];
		
		//around the corner(min_x, min_y) and then translate
		//reflect in horizontal axis
		if(s.equals("H")){
			for(int i=0; i<n; i++){
				new_xcoords[i] = this.xcoords[i];
				new_ycoords[i] = (max_y+min_y)-this.ycoords[i];
			}
			return new Polyomino(new_xcoords, new_ycoords);
		}
		
		//reflect in vertical axis
		if(s.equals("V")){
			for(int i=0; i<n; i++){
				new_xcoords[i] = (max_x+min_x)-this.xcoords[i];
				new_ycoords[i] = this.ycoords[i];
			}
			return new Polyomino(new_xcoords, new_ycoords);
		}
		
		//reflect in ascending diagonal(toPolygon will change the direction of y, so A is D after toPolygon)
		if(s.equals("D")){//here D is after toPloygon
			for(int i=0; i<n; i++){
				new_xcoords[i] = min_x+min_y-this.ycoords[i];
				new_ycoords[i] = min_x+min_y-this.xcoords[i];
			}
			return new Polyomino(new_xcoords, new_ycoords).translate(max_y-min_y, max_x-min_x);
		}
		
		//reflect in descending diagonal(toPolygon will change the direction of y, so D is A after toPolygon)
		if(s.equals("A")){//here A is after toPolygon
			for(int i=0; i<n; i++){
				new_xcoords[i] = min_x-min_y+this.ycoords[i];
				new_ycoords[i] = min_y-min_x+this.xcoords[i];
			}
			return new Polyomino(new_xcoords, new_ycoords);
		}
		
		System.out.println("Error of input");
		return null;
				
				
	}
	
	//dilate a polyomino with n points by size times
	public Polyomino dilate(int size){
		int[] p_xcoords = new int[xcoords.length*size*size];
		int[] p_ycoords = new int[ycoords.length*size*size];
		
		for(int i = 0; i < xcoords.length; i++){
			for(int k = 0; k < size; k++){
				for(int l = 0; l < size; l++){
					p_xcoords[i*size*size+k*size+l] = xcoords[i]*size+k;
					p_ycoords[i*size*size+k*size+l] = ycoords[i]*size+l;
				}
			}
		}
		return (new Polyomino(p_xcoords,p_ycoords).translate(-(size-1)*xcoords[0], -(size-1)*ycoords[0]));
		//We translate the Polyomino so that we place the origin of 
		//the Polyomino back at its previous position
	}
	
	
	//size of polyomino
	public int size(){
		return this.xcoords.length;
	}
	
	
	//points of polyomino
	public Point[] toPoint(){
		int size = this.size();
		Point[] tmp = new Point[size];
		for(int i=0; i<size; i++){
			tmp[i] = new Point(this.xcoords[i], this.ycoords[i]);
		}
		return tmp;
	}
	
	
	//compare with polyomino p
	public boolean equals(Polyomino p){
		if(p == null)
			return this == null;
		if(this.xcoords.length != p.xcoords.length)
			return false;
		else{
			int size = this.size();
			boolean[] b = new boolean[size];
			Point[] pointsOfthis = this.toPoint();
			Point[] pointsOfp = p.toPoint();
			//find point(minx, minyOfminx) of this polyomino and polyomino p for translate 
			int minxOfthis = Integer.MAX_VALUE;
			int minxOfp = Integer.MAX_VALUE;
			for(int i=0; i<size; i++){
				if(pointsOfthis[i].x<minxOfthis)
					minxOfthis = pointsOfthis[i].x;
				if(pointsOfp[i].x<minxOfp)
					minxOfp = pointsOfp[i].x;
			}
			int minyOfminxOfthis = Integer.MAX_VALUE;
			int minyOfminxOfp = Integer.MAX_VALUE;
			for(int i=0; i<size; i++){
				if(pointsOfthis[i].x==minxOfthis && pointsOfthis[i].y<minyOfminxOfthis)
					minyOfminxOfthis = pointsOfthis[i].y;
				if(pointsOfp[i].x==minxOfp && pointsOfp[i].y<minyOfminxOfp)
					minyOfminxOfp = pointsOfp[i].y;		
			}
			//translate p to make the origin point of these two polyominoes together
			Polyomino tmp = p.translate(minxOfthis-minxOfp, minyOfminxOfthis-minyOfminxOfp);
			Point[] pointsOftmp = tmp.toPoint();
			//compare tmp and this
			for(int i=0; i<size; i++){
				for(Point pointOftmp : pointsOftmp)
					if(pointOftmp.equals(pointsOfthis[i]))
						b[i] = true;
			}
			for(int i=0; i<size; i++){
				if(!b[i])
					return false;
			}
			return true;
		}
	}
	
	
	//whether R symmetry 
	public boolean Requals(Polyomino p){
		if(p == null)
			return this == null;
		Polyomino tmp = this.rotate(2);
		return tmp.equals(p);	
	}
	
	
	//whether H symmetry
	public boolean Hequals(Polyomino p){
		if(p == null)
			return this == null;
		Polyomino tmp = this.reflect("H");
		return tmp.equals(p);
	}
	
	
	//whether V symmetry
	public boolean Vequals(Polyomino p){
		if(p == null)
			return this == null;
		Polyomino tmp = this.reflect("V");
		return tmp.equals(p);
	}
	
	//whether A symmetry
	public boolean Aequals(Polyomino p){
		if(p == null)
			return this == null;
		Polyomino tmp = this.reflect("A");
		return tmp.equals(p);
	}
	
	
	//whether D symmetry
	public boolean Dequals(Polyomino p){
		if(p == null)
			return this == null;
		Polyomino tmp = this.reflect("D");
		return tmp.equals(p);
	}
	
	
	//whether R2 symmetry (with both two directions)
	public boolean R2equals(Polyomino p){
		if(p == null)
			return this == null;
		Polyomino tmp = this.rotate(1);//counterclockwise
		Polyomino tmp2 = this.rotate(-1);//clockwise
		return tmp.equals(p) || tmp2.equals(p);
	}
	
	
	
	
	//for Ring
	public boolean isRing(){
		LinkedList<Point> points = Point.pointsOfPolyomino(this);
		LinkedList<Point> occupied = new LinkedList<Point>();
		for(Point point : points){
			occupied.add(point);
		}
		for(Point point : points){
			int cpt=0;
			for(Point neighbour : point.neighbours())
				if(occupied.contains(neighbour))                    //it works??????
					cpt++;
			if(cpt!=2)
				return false;
		}
		return true;
	}
	
	
	
	
	//With the origin of the coordinates the DownLeft corner of a polyomino p, we translate p
	//into a binary matrix m where m[x][y] = 1 if x,y is a point in p and 0 otherwise
	public int[][] toBinaryMatrix(){
		int xmin = this.xcoords[0];
		int ymin = this.ycoords[0];
		int xmax = xmin;
		int ymax = ymin;
		for(int i = 0; i < this.xcoords.length; i++){
			if(this.xcoords[i] < xmin)
				xmin = this.xcoords[i];
			if(this.xcoords[i] > xmax)
				xmax = this.xcoords[i];
			if(this.ycoords[i] < ymin)
				ymin = this.ycoords[i];
			if(this.ycoords[i] > ymax)
				ymax = this.ycoords[i];
		}
		int[][] m = new int[xmax-xmin+1][ymax-ymin+1];
		for(int i = 0; i < this.xcoords.length; i++)
			m[this.xcoords[i]-xmin][this.ycoords[i]-ymin] = 1;
		Main.printMatrix(m);
		System.out.println("---");
		return m;
	}

}
