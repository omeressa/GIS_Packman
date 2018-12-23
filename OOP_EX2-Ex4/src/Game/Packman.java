package Game;

import Geom.Point3D;

/**
 * packkman class
 * @author Omar Essa & Warda Essa
 *
 */
public class Packman {
	
	public Point3D Packman;
	double radius;
	double speed;
	Path path;
	
	/**
	 * Constructor
	 * @param packman
	 * @param radius
	 * @param speed
	 * @param orientation
	 */
	public Packman(Point3D packman, double radius, double speed) {
		super();
		Packman = packman;
		this.radius = radius;
		this.speed = speed;
		path = new Path();
	}
	
	/**
	 * Copy Constructor
	 * @param other
	 */
	public Packman(Packman other) {
		this.Packman = other.Packman;
		this.radius = other.radius;
		this.speed = other.speed;
		this.path = new Path();
	}

	/**
	 * @return the packman
	 */
	public Point3D getPackman() {
		return Packman;
	}

	/**
	 * @param packman the packman to set
	 */
	public void setPackman(Point3D packman) {
		Packman = packman;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}


	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}


	/**
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Packman [Packman=" + Packman.toString() + ", radius=" + radius + ", speed=" + speed + ", path=" + path
				+ "]";
	}
	
	


}