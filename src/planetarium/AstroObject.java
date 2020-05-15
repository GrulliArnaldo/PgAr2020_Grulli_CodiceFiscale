package planetarium;

import it.unibs.fp.mylib.InputDati;

	public class AstroObject {

		//ciao catalin 
		// ciao di nuovo
		private static final String INSERT_OBJECT_Y = "Insert %s's Y coordinate: ";
		private static final String INSERT_OBJECT_X = "Insert %s's X coordinate: ";
		private static final String INSERT_OBJECT_MASS = "Insert %s mass: ";
		private static final String INSERT_OBJECT_NAME = "Insert %s name: ";
		private static final String INSERT_OBJECT_ID = "Insert %s ID: ";

		private static final String ERROR_WRONG_INPUT = "Error, wrong input!";
		private static final String MESSAGE_MOON_LINKED_PLANET = "Insert the moon's linked planet ID: ";
		
		
		protected int objectId;
		protected String name;
		protected double mass;
		protected double positionX;
		protected double positionY;
		protected TypeAstroObject type;
		
		public AstroObject(int _objectId, String _name, double _mass, double _positionX, double _positionY, TypeAstroObject _type) {
			this.objectId = _objectId;
			this.name = _name;
			this.mass = _mass;
			this.positionX = _positionX;
			this.positionY = _positionY;
			this.type = _type;
		}
		
		/**
		 * Metodo che permette di chiedere in input i dati e creare un pianeta o una luna
		 * @param scelta 1 per il pianeta 2 per la luna, controllare che sia 1 o 2 per non andare in loop
		 * @param star, ci chiede la stella di riferimento per poter linkare il pianeta o la luna a un riferimento 
		 * @return restituisce un oggetto o di tipo luna o di tipo pianeta
		 */
		public static AstroObject createMoonOrPlanet(int scelta, Star star) {
		    
		    do {
			    switch(scelta) {
			      case 1:
			    	AstroObject planet = AstroObject.createObject(TypeAstroObject.PLANET, star);
			        return new Planet(planet.getObjectId(), planet.getName(), planet.getMass(), planet.getPositionX(), planet.getPositionY(), planet.getType(), star);
	
			        
			      case 2:
			        AstroObject moon = AstroObject.createObject(TypeAstroObject.MOON, star);
			        int _linkedPlanetID;
			        do {
			        	_linkedPlanetID = InputDati.leggiIntero(MESSAGE_MOON_LINKED_PLANET);
			        	if (star.getPlanet(_linkedPlanetID) == null)
			        		System.out.println(ERROR_WRONG_INPUT);
			        }while(star.getPlanet(_linkedPlanetID) == null);
			        return new Moon(moon.getObjectId(), moon.getName(), moon.getMass(), moon.getPositionX(), moon.getPositionY(), moon.getType(), star.getPlanet(_linkedPlanetID));
			        
			    default: 
			    	System.out.println(ERROR_WRONG_INPUT);
			    	break;
			    }
			    
		  }while(scelta!=1 || scelta!=2 );
		    
		  return null;
		}
		
		/**
		 * metodo di generale che chiede in input i dati condivisi dalle le classe figlie di object
		 * @param _type da dare in input per determinare il tipo di oggeto da creare
		 * @param star la stella di riferimento per poter fare il controllo del id
		 * @return resituisce un oggetto con i parametri inseriti
		 */
		public static AstroObject createObject(TypeAstroObject _type, Star star) {
			int objectId;
			
			do {
				objectId = InputDati.leggiInteroNonNegativo(String.format(INSERT_OBJECT_ID, _type.toString()));
				if (AstroObject.controlIfIDTaken(objectId, star))
					System.out.println(ERROR_WRONG_INPUT);
			}while(AstroObject.controlIfIDTaken(objectId, star));
			
			String name = InputDati.leggiStringaNonVuota(String.format(INSERT_OBJECT_NAME, _type.toString()));
			double mass = InputDati.leggiDoubleConMinimo(String.format(INSERT_OBJECT_MASS, _type.toString()), 0);
			double positionX = InputDati.leggiDouble(String.format(INSERT_OBJECT_X, name));
			double positionY = InputDati.leggiDouble(String.format(INSERT_OBJECT_Y, name));
			
			return new AstroObject(objectId, name, mass, positionX, positionY, _type);
		}
		
		/**
		 * controlla se una determinato Id è stato già preso da un'altro AstroObject nel sistema stellare collegato
		 * alla stella data in input
		 * @param id , il valore integer del Id da ricercare
		 * @param star la stella del sistema stellare di riferimento
		 * @return restituisce true se trova, un valore già preso o false in caso contrario
		 */
		public static boolean controlIfIDTaken(int id, Star star) {
		    if(id == star.getObjectId())
		    	return true;
		    for(int i = 0; i<star.getPlanetList().size(); i++) 
		        if(id == star.getPlanetList().get(i).getObjectId())
		          return true;
		    for(int j = 0; j<star.getMoonList().size(); j++) 
		        if(id == star.getMoonList().get(j).getObjectId()) 
		        	return true;
		    return false;
	    }


		//GETTERS AND SETTERS
		public int getObjectId() {
			return objectId;
		}

		public void setObjectId(int objectId) {
			this.objectId = objectId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getMass() {
			return mass;
		}

		public void setMass(double mass) {
			this.mass = mass;
		}

		public double getPositionX() {
			return positionX;
		}

		public void setPositionX(double positionX) {
			this.positionX = positionX;
		}

		public double getPositionY() {
			return positionY;
		}

		public void setPositionY(double positionY) {
			this.positionY = positionY;
		}

		public TypeAstroObject getType() {
			return type;
		}

		public void setType(TypeAstroObject type) {
			this.type = type;
		}

		
		
	}
