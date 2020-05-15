package planetarium;

import allprojectsutility.MathMethods;
import it.unibs.fp.mylib.InputDati;

public class StarSystem {

		private static final String OBJECTS_NOT_IN_SYSTEM = "One of the objects is not in this star system!";
		private static final String ERROR_STAR = "Error cannot start or finish travel with a star!";
		private static final String REAPEATED_ASTRO_OBJECT = "Same reapeated astro object!";
		private static final String MOON_ROUTE_STRING= "%s/%s/%s";
		private static final String PLANETS_MOONS_PRINT = "%d° moon: %s";
		private static final String INPUT_STAR_MASS = "Insert star mass: ";
		private static final String INPUT_STAR_NAME = "Insert star name: ";

		/**
		 * metodo che crea l'oggetto stella richiedendo i valori in input di nome e massa
		 * il codice univoco viene impostato a 0
		 * e anche le 2 posizioni sono impostate a 0
		 * l'attributo type, viene impostato a STAR
		 * @return restituisce l'oggetto stella
		 */
		public static Star createStar() {
			String name = InputDati.leggiStringaNonVuota(INPUT_STAR_NAME);
			double mass = InputDati.leggiDoubleConMinimo(INPUT_STAR_MASS, 0);
			return new Star(0, name, mass, 0, 0, TypeAstroObject.STAR);
		}
		
		/**
		 * stampa tutte le lune di un pianeta
		 * @param planet bisogna dare un riferimento a un oggetto di tipo Planet
		 */
		public static void printPlanetMoons(Planet planet) {
			for(int i = 0;i<planet.getMoonList().size();i++) {
				System.out.println(String.format(PLANETS_MOONS_PRINT, i+1, planet.getMoonList().get(i).getName()));
			}
		}
		
		/**
		 * stampa la rotta di una luna in formato
		 * Moon/Planet/Star
		 * @param moon bisogna dare il riferimento a un oggetto di tipo Moon
		 */
		public static void printMoonRoute(Moon moon) {
			System.out.println(String.format(MOON_ROUTE_STRING, moon.getName(), moon.getLinkedPlanet().getName(), moon.getLinkedPlanet().getLinkedStar().getName()));
		}
		
		/**
		 * metodo che calcola se c'è la possibilità di una collisione tra 2 oggetti
		 * nel caso i 2 oggeti fossero uguali, verrà stampato un messaggio di errore e restituito false
		 * @param objectA non importa l'ordine
		 * @param objectB non importa l'ordine
		 * @param star deve essere inserito l'oggetto star del sistema di riferimento
		 * @return restituisce true nel caso di collisione o falso in caso contrario
		 */
		public static boolean collisionTest(AstroObject objectA, AstroObject objectB, Star star) {
			
			//controlla che non sia stato inserito lo stesso oggetto 2 volte
			if (objectA.equals(objectB)) {
				System.out.println(REAPEATED_ASTRO_OBJECT);
				return false;
			}
			
			//se uno dei 2 oggetti è la stella, controlliamo solo che l'altro non sia una luna con un raggio 
			//uguale alla distanza del suo pianeta dal sole
			if (objectA.getType().equals(star.getType()) || objectB.getType().equals(star.getType())) 
				if (objectA.getType().equals(TypeAstroObject.MOON)) {
					if (star.getMoon(objectA.getObjectId()).getRadius() == star.getMoon(objectA.getObjectId()).getLinkedPlanet().getRadius())
						return true;
				}
				else if (objectB.getType().equals(TypeAstroObject.MOON)){
					if (star.getMoon(objectB.getObjectId()).getRadius() == star.getMoon(objectB.getObjectId()).getLinkedPlanet().getRadius())
						return true;
				}
			
			
			//nel caso fossero 2 pianeti, dobbiamo solo che non siano sulla stessa orbita, 
			if (objectA.getType() == TypeAstroObject.PLANET && objectB.getType() == TypeAstroObject.PLANET) 
				if (star.getPlanet(objectA.getObjectId()).getRadius() == star.getPlanet(objectB.getObjectId()).getRadius())
					return true;
			
			
			// nel caso fossero 2 lune guardiamo solo che non appartengano allo stesso pianeta
			//e hanno lo stesso raggio di rivoluzione
			if (objectA.getType() == TypeAstroObject.MOON && objectB.getType() == TypeAstroObject.MOON) {
				if(star.getMoon(objectA.getObjectId()).getLinkedPlanet().equals(star.getMoon(objectB.getObjectId()).getLinkedPlanet())) {
					if (star.getMoon(objectA.getObjectId()).getRadius() == star.getMoon(objectB.getObjectId()).getRadius())
						return true;
				}
				else 
					return StarSystem.moonAndMoonCollision(star.getMoon(objectA.getObjectId()), star.getMoon(objectB.getObjectId()));
			}
			
			//controlliamo i casi di pianeta e luna
			//dobbiamo controllare che il pianeta non sia all'interno della corona circolare formata dalla luna
			// 
			if (objectA.getType().equals(TypeAstroObject.MOON) && objectB.getType().equals(TypeAstroObject.PLANET)) {
				return StarSystem.moonCircularCrownContainsPlanet(star.getMoon(objectA.getObjectId()), star.getPlanet(objectB.getObjectId()));
			}
			else if (objectB.getType().equals(TypeAstroObject.MOON) && objectA.getType().equals(TypeAstroObject.PLANET)){
				return StarSystem.moonCircularCrownContainsPlanet(star.getMoon(objectB.getObjectId()), star.getPlanet(objectA.getObjectId()));
			}
			return false;
		}
		
		/**
		 * controlla se le 2 corone circolari delle lune in input si toccano
		 * @param moonA oggetto luna A
		 * @param moonB oggetto luna B
		 * @return restituisce true nel caso di collisione
		 */
		public static boolean moonAndMoonCollision(Moon moonA, Moon moonB) {
			double maxCrownRangeA = MathMethods.calculateCircumferenceLenght(moonA.getLinkedPlanet().getRadius() + moonA.getRadius());
			double minCrownRangeA = MathMethods.calculateCircumferenceLenght(moonA.getLinkedPlanet().getRadius() - moonA.getRadius());
			double maxCrownRangeB = MathMethods.calculateCircumferenceLenght(moonB.getLinkedPlanet().getRadius() + moonB.getRadius());
			double minCrownRangeB = MathMethods.calculateCircumferenceLenght(moonB.getLinkedPlanet().getRadius() - moonB.getRadius());
			
			if (maxCrownRangeA < minCrownRangeB || maxCrownRangeB < minCrownRangeA)
				return false;
			else
				return true;
		}
		
		/**
		 * metodo che calcola la circonferenza della distanza minima della luna dal sole e la massima
		 * controlla che il pianeta in oggetto con il proprio raggio crei una circonferenza
		 * maggiore della minima e minore della massima
		 * @param moon l'oggetto luna che usiamo per determinare la corona circolare
		 * @param planet l'oggeto pianeta da ricercare
		 * @return restituisce true nel caso si compreso e false negli altri casi
		 */
		public static boolean moonCircularCrownContainsPlanet(Moon moon, Planet planet) {
			double maxCrownRange = MathMethods.calculateCircumferenceLenght(moon.getLinkedPlanet().getRadius() + moon.getRadius());
			double minCrownRange = MathMethods.calculateCircumferenceLenght(moon.getLinkedPlanet().getRadius() - moon.getRadius());
			if (MathMethods.calculateCircumferenceLenght(planet.getRadius())>=minCrownRange && MathMethods.calculateCircumferenceLenght(planet.getRadius())<=maxCrownRange)
				return true;
			return false;
		}
	
		/**
		 * crea una stringa con la rotta dal oggetto A al oggeto B mettendo ">" in mezzo
		 * @param objectA partenza
		 * @param objectB arrivo
		 * @param star stella del sistema
		 * @return restituisce una stringa del tipo "luna>pianeta>sole>pianeta>luna"
		 * 0 luna
		 * 1 pianeta
		 * 
		 */
		public static String printTravelRoute(AstroObject objectA, AstroObject objectB, Star star) {
			String route = "";

			if (objectA.getObjectId() == star.getObjectId() || objectB.getObjectId() == star.getObjectId())
				System.out.println(ERROR_STAR);
			else if (star.containsAstroObjectId(objectA.getObjectId()) && star.containsAstroObjectId(objectB.getObjectId())){
				route += objectA.getName() + ">";
				if (objectA.getType() == TypeAstroObject.PLANET && objectB.getType() == TypeAstroObject.PLANET)
					route += star.getName() + ">" + star.getPlanet(objectB.getObjectId()).getName();
				else if (objectA.getType() == TypeAstroObject.PLANET && objectB.getType() == TypeAstroObject.MOON)
					route += String.format("%s>%s>%s", star.getName(), star.getMoon(objectB.getObjectId()).getLinkedPlanet().getName(), star.getMoon(objectB.getObjectId()).getName());
				else if (objectA.getType() == TypeAstroObject.MOON && objectB.getType() == TypeAstroObject.MOON)
					route += String.format("%s>%s>%s", star.getMoon(objectA.getObjectId()).getLinkedPlanet().getName(), star.getName(), star.getPlanet(objectB.getObjectId()).getName());
				else if (objectA.getType() == TypeAstroObject.MOON && objectB.getType() == TypeAstroObject.PLANET)
					route += String.format("%s>%s>%s", star.getMoon(objectA.getObjectId()).getLinkedPlanet().getName(), star.getName(), star.getPlanet(objectB.getObjectId()).getName());
			}
			else
				System.out.println(OBJECTS_NOT_IN_SYSTEM);
			return route;
		}
		
		
		/**
		 * calcola la distanza dato in input un array di stringhe, contenenti i nomi degli oggetti
		 * controllare bene che il nome sia identico
		 * @param objectList l'array dei nomi
		 * @param star la stella del sistema
		 * @return restituisce un double con la distanza
		 */
		public static double calculateDistanceFromRoute(String[] objectList, Star star) {

			double totalDistance = 0;
			for (int k = 0; k+1 < objectList.length && objectList[k+1] != ""; k++) {
				totalDistance += StarSystem.calculateDistanceTwoObjects(StarSystem.getAstroObjectByName(objectList[k],  star), StarSystem.getAstroObjectByName(objectList[k+1],  star));
			}			
			return totalDistance;
		}
		
		/**
		 * calcola la distanza tra 2 coppie di coordinate
		 * @param objectAX coordinata x del primo oggetto
		 * @param objectAY coordinata y del primo oggetto
		 * @param objectBX coordinata x del secondo oggetto
		 * @param objectBY coordinata y del secondo oggetto
		 * @return restituisce un double con la distanza
		 */
		public static double calculateDistanceTwoPositions(double objectAX, double objectAY, double objectBX, double objectBY) {
			return Math.sqrt(Math.pow(objectAX-objectBX, 2) + Math.pow(objectAY-objectBY, 2));
		
		}
		
		/**
		 * prende in input una stringa con il formato del metodo @printTravelRoute e restituisce un array di stringhe
		 * con i valori di ogni "fermata"
		 * @param route la stringa della rotta in ingresso
		 * @param star la stella del sistema
		 * @return restituisce un array di max 5 di lunghezza
		 */
		public static String[] getStringArrayFromRoute(String route, Star star){
			String[] objectList = {"", "", "", "", ""};
			
			for (int i = 0; i<objectList.length && route.length() != 0;i++) {
				for (int j = 0; j<route.length() && route.toCharArray()[j] != '>';j++) {
					objectList[i] += route.toCharArray()[j];
				}
				route = route.replaceFirst(objectList[i], "");
				if (route.length() != 0 && route.toCharArray()[0] == '>')
					route = route.replaceFirst(">", "");
			}
			return objectList;
		}
		
		/**
		 * ricerca un oggetto per id
		 * @param _id valore integer del id cercato
		 * @param star stella del sistema di riferimento
		 * @return restituisce un oggetto oppure null
		 */
		public static AstroObject getAstroObjectById(int _id, Star star) {
			if (_id == star.getObjectId())
					return star;
			for (int i = 0; i<star.getPlanetList().size();i++) {
				if (_id == star.getPlanetList().get(i).getObjectId())
					return star.getPlanetList().get(i);
			}
			for (int i = 0; i<star.getMoonList().size();i++) {
				if (_id ==star.getMoonList().get(i).getObjectId())
					return star.getMoonList().get(i);
			}
			return null;
		}
		
		/**
		 * ricerca un oggetto per id
		 * @param objectName stringa del nome cercato
		 * @param star stella del sistema di riferimento
		 * @return restituisce un oggetto oppure null
		 */
		public static AstroObject getAstroObjectByName(String objectName, Star star) {
			if (objectName.equalsIgnoreCase(star.getName()))
					return star;
			for (int i = 0; i<star.getPlanetList().size();i++) {
				if (objectName.equalsIgnoreCase(star.getPlanetList().get(i).getName()))
					return star.getPlanetList().get(i);
			}
			for (int i = 0; i<star.getMoonList().size();i++) {
				if (objectName.equalsIgnoreCase(star.getMoonList().get(i).getName()))
					return star.getMoonList().get(i);
			}
			return null;
		}
		
		/**
		 * calcola la distanza tra 2 AstroObject
		 * @param objectA AstroObject di partenza
		 * @param objectB AstroObject di arrivo
		 * @return restituisce un double
		 */
		public static double calculateDistanceTwoObjects(AstroObject objectA, AstroObject objectB) {
			return Math.sqrt(Math.pow(objectA.getPositionX()-objectB.getPositionX(), 2) + Math.pow(objectA.getPositionY()-objectB.getPositionY(), 2));
		}
		
	}
	
	
	
	
	
	

