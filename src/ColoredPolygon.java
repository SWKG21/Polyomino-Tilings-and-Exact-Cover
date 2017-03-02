import java.awt.Color;
import java.awt.Polygon;

public class ColoredPolygon {

	int[] xcoords, ycoords;
	Color color;
	Polygon polygon;
	
	public ColoredPolygon(int[] xcoords, int[] ycoords, Color color) {
		this.xcoords = xcoords;
		this.ycoords = ycoords;
		this.color = color;
		polygon = new Polygon(xcoords, ycoords, xcoords.length);
	}

	
}
