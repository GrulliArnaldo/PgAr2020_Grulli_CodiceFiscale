package planetarium;

	import java.util.*;
	
	public class Star extends AstroObject{

		private static final String IMPOSSIBILE_TO_FIND_THE_ASTRO_OBJECT = "Impossibile to find the AstroObject you insert!";
		private static final String IMPOSSIBLE_TO_REMOVE_A_STAR = "Impossible to remove a star!";
		private ArrayList<Planet> planetList;
		
		public Star (int _objectId, String _name, double _mass, double _positionX, double _positionY, TypeAstroObject _type) {
			super(_objectId, _name, _mass, _positionX, _positionY, _type);
			this.planetList = new ArrayList<Planet>();
			System.out.println("");
		}
		
		/**
		 * metodo che restituisce la lista di tutti le lune nel sistema
		 * @return restituisce un ArrayList di tipo Moon
		 */
		public ArrayList<Moon> getMoonList(){
			ArrayList <Moon> list = new ArrayList<Moon>();
			for(int i = 0; i<this.getPlanetList().size(); i++)
				for (int j = 0; j<this.getPlanetList().get(i).getMoonList().size(); j++)
					list.add(this.getPlanetList().get(i).getMoonList().get(j));
			return list;
		}
		/**
		 * metodo che calcola la massa totale sommata dei tutti i corpi nel sistema della stella
		 * @return restituisce un double
		 */
		public double calculateTotalMass() {
			double mass = this.getMass();
			
			for(int i = 0;i<this.getPlanetList().size();i++) {
				mass += this.getPlanetList().get(i).getMass();
				for(int j = 0;j<this.getPlanetList().get(i).getMoonList().size();j++)
					mass += this.getPlanetList().get(i).getMoonList().get(j).getMass();
			}
			return mass;
		}
		
		/**
		 * resistituisce la coordinata X del centro di massa del sistema della stella
		 * @return restituisce un double
		 */
		public double calculateMassCenterX() {
			double massCenterX = this.getMass() * this.getPositionX();
			
			for(int i = 0;i<this.getPlanetList().size();i++) {
				massCenterX += this.getPlanetList().get(i).getMass() * this.getPlanetList().get(i).getPositionX();
				for(int j = 0;j<this.getPlanetList().get(i).getMoonList().size();j++)
					massCenterX += this.getPlanetList().get(i).getMoonList().get(j).getMass() * this.getPlanetList().get(i).getMoonList().get(j).getPositionX();
			}
			
			return massCenterX/this.calculateTotalMass();
		}
		
		/**
		 * resistituisce la coordinata Y del centro di massa del sistema della stella
		 * @return restituisce un double
		 */
		public double calculateMassCenterY() {
			double massCenterY = this.getMass() * this.getPositionY();
			
			for(int i = 0;i<this.getPlanetList().size();i++) {
				massCenterY += this.getPlanetList().get(i).getMass() * this.getPlanetList().get(i).getPositionY();
				for(int j = 0;j<this.getPlanetList().get(i).getMoonList().size();j++)
					massCenterY += this.getPlanetList().get(i).getMoonList().get(j).getMass() * this.getPlanetList().get(i).getMoonList().get(j).getPositionY();
			}
			
			return massCenterY/this.calculateTotalMass();
		}
		
		/**
		 * aggiunge un pianeta alla lista dei pianeti della stella
		 * @param _star deve essere fornito in input per poter linkare il pianeta alla stella
		 */
		public void addPlanet(Star _star) {
			AstroObject object = createObject(TypeAstroObject.STAR, _star);
			Planet planet = new Planet(object.getObjectId(), object.getName(), object.getMass(), object.getPositionX(), object.getPositionY(), object.getType(), _star);
			this.getPlanetList().add(planet);
		}
		
		/**
		 * il metodo controlla se nella lista dei pianeti della stella (e anche nella lista delle lune di ogni singolo pianeta),
		 * è presente l'id richiesto in input
		 * @param lookedForAstroObjectId è il valore richiesto in input da ricercare
		 * @return restituisce true nel caso corrisponda a una stella/pianeta/luna oppure false se non trova una corrispondenza
		 */
		public boolean containsAstroObjectId(int lookedForAstroObjectId) {
			
			if (this.getObjectId() == lookedForAstroObjectId)
				return true;
			else {
				for(int i = 0;i<this.getPlanetList().size();i++) {
					if (this.getPlanetList().get(i).getObjectId() == lookedForAstroObjectId)
						return true;
					else {
						for(int j = 0;j<this.getPlanetList().get(i).getMoonList().size();j++)
							if (this.getPlanetList().get(i).getMoonList().get(j).getObjectId() == lookedForAstroObjectId)
								return true;
					}
				}
			}
			return false;
		}
		
		
		/**
		 * metodo che restituisce true se l'oggetto è presente nei pianeti o nelle lune di questa stella
		 * non tiene conto della stella stessa
		 * @param object ci deve essere dato l'AstroObject cercato
		 * @return restituisce true se lo trova o false se non lo trova
		 */
		public boolean containsAstroObject(AstroObject object) {
			if (this.getPlanetList().contains(object) || this.getMoonList().contains(object))
				return true;
			else 
				return false;
		}
		
		/**
		 * rimuove dalla lista dei pianeti o dalla lista delle lune l'oggetto con l'id corrispondente al parametro in input
		 * se il parametro corrisponde all'id della stella, stampa un messaggio di errore
		 * se non trova una corrispondenza stampa un messaggio di errore
		 * @param lookedForAstroObjectId l'id da ricercare nelle liste dei pianeti/lune
		 */
		public void removeAstroObject(int lookedForAstroObjectId) {

			if (this.getObjectId() == lookedForAstroObjectId) 
					System.out.println(IMPOSSIBLE_TO_REMOVE_A_STAR);
			else if (this.containsAstroObjectId(lookedForAstroObjectId) == true)
				for(int i = 0;i<this.getPlanetList().size();i++) {
					if (this.getPlanetList().get(i).getObjectId() == lookedForAstroObjectId)
						this.getPlanetList().remove(i);
					else
						for(int j = 0;i<this.getPlanetList().get(i).getMoonList().size();i++)
							if (this.getPlanetList().get(i).getMoonList().get(j).getObjectId() == lookedForAstroObjectId)
								this.getPlanetList().get(i).getMoonList().remove(j);
				}
			else
				System.out.println(IMPOSSIBILE_TO_FIND_THE_ASTRO_OBJECT);
			
		}
		
		/**
		 * stampa la lista dei pianeti della stella
		 */
		public void printPlanetNameList() {
			for(int i = 0; i<this.getPlanetList().size();i++)
				System.out.println("P" + i + ": " + this.getPlanetList().get(i).getName());
		}
		
		/**
		 * cerca un pianeta dato il suo id
		 * @param id il valore interger del id da ricercare
		 * @return restituisce il pianeta se lo trova, altrimenti null
		 */
		public Planet getPlanet(int id) {
			for (int i = 0; i<this.getPlanetList().size();i++)
				if (id == this.getPlanetList().get(i).getObjectId())
						return this.getPlanetList().get(i);
			
			return null;
		}
		
		/**
		 * cerca una luna dato il suo id
		 * @param id il valore interger del id da ricercare
		 * @return restituisce la luna se la trova, altrimenti null
		 */
		public Moon getMoon(int id) {
			for (int i = 0; i<this.getPlanetList().size();i++)
				for(int j = 0; j<this.getPlanetList().get(i).getMoonList().size();j++)
					if (id == this.getPlanetList().get(i).getMoonList().get(j).getObjectId())
						return this.getPlanetList().get(i).getMoonList().get(j);
			
			return null;
		}

		/**
		 * @return restituisce la lista dei pianeti corrispondenti alla stella
		 */
		public ArrayList<Planet> getPlanetList() {
			return planetList;
		}

		/**
		 * 
		 * @param planetList sostituisce la lista dei pianeti con il parametro in input
		 */
		public void setPlanetList(ArrayList<Planet> planetList) {
			this.planetList = planetList;
		}
		
		
	}

