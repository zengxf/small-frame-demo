package test.algorithm.similar;

public class PositionTest {

    public static void main( String[] args ) {
	Point a = new Point( 10, 10, 15 );
	Point b = new Point( 20, 20, 14 );
	Point c = new Point( 10, 16, 14 );
	a.compute( b );
	a.compute( c );
    }

    static class Point {
	int x, y, z;

	public Point( int x, int y, int z ) {
	    super();
	    this.x = x;
	    this.y = y;
	    this.z = z;
	}

	public void compute( Point p ) {
	    int sum = x * p.x + y * p.y ;//+ z * p.z;
	    int quadratic1 = x * x + y * y ;//+ z * z;
	    int quadratic2 = p.x * p.x + p.y * p.y ;//+ p.z * p.z;
	    double pro = Math.sqrt( quadratic1 ) * Math.sqrt( quadratic2 );
	    System.out.println( sum / pro );
	}

    }

}
