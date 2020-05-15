package planetarium;

public class Moon extends AstroObject{
	
	private Planet linkedPlanet;
	private double radius;
	
	public Moon (int _objectId, String _name, double _mass, double _positionX, double _positionY, TypeAstroObject _type, Planet _linkedPlanet) {
		super(_objectId, _name, _mass, _positionX, _positionY, _type);
		this.linkedPlanet = _linkedPlanet;
		this.radius = StarSystem.calculateDistanceTwoPositions(this.positionX, this.positionY, this.linkedPlanet.getPositionX(), this.getLinkedPlanet().getPositionY());
		System.out.println("");
	}

	//GETTERS AND SETTERS
	public Planet getLinkedPlanet() {
		return linkedPlanet;
	}

	public void setLinkedPlanet(Planet linkedPlanet) {
		this.linkedPlanet = linkedPlanet;
	}


	public double getRadius() {
		return radius;
	}


	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	
}

