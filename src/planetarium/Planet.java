package planetarium;

	import java.util.*;

	public class Planet extends AstroObject{

		private ArrayList<Moon> moonList;
		private Star linkedStar;
		private double radius;
		
		public Planet (int _objectId, String _name, double _mass, double _positionX, double _positionY, TypeAstroObject _type, Star _linkedStar) {
			super(_objectId, _name, _mass, _positionX, _positionY, _type);
			this.moonList = new ArrayList<Moon>();
			this.linkedStar = _linkedStar;
			this.radius = StarSystem.calculateDistanceTwoPositions(this.positionX, this.positionY, this.linkedStar.getPositionX(), this.getLinkedStar().getPositionY());
			System.out.println("");
		}
		
		/**
		 * metodo che stampa a video la lista delle lune di un pianeta
		 */
		public void printMoonsNameList() {
			for(int i = 0; i<this.getMoonList().size();i++)
				System.out.println(this.getMoonList().get(i).getName());
		}
		
		// GETTERS AND SETTERS
		public ArrayList<Moon> getMoonList() {
			return moonList;
		}

		public void setMoonList(ArrayList<Moon> moonList) {
			this.moonList = moonList;
		}
		public Star getLinkedStar() {
			return linkedStar;
		}
		public void setLinkedStar(Star linkedStar) {
			this.linkedStar = linkedStar;
		}
		public double getRadius() {
			return radius;
		}
		public void setRadius(double radius) {
			this.radius = radius;
		}
		
		
		
		
	}

