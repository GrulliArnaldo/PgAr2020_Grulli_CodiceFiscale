package planetarium;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class MainUtility {
	//PRINTMESSAGES
	private static final String COLLISION_MESSAGE = "%s can collide with %s!!!";
	private static final String DISTANCE_MESSAGE = "Distance: %.3f";
	private static final String MASS_CENTER_MESSAGE = "%s's center of mass is: (%02.3f,%02.3f)";
	private static final String THE_INPUT_OBJECT_BELONGS_TO_STAR = "The input object belongs to %s's system!";
	
	//INSERT MESSAGES
	private static final String MESSAGE_MAIN_MENU = "SELECT ACTION: ";
	private static final String MESSAGE_CHECK_ID = "Enter an id: ";
	private static final String FIRST_OBJECT_ID = "Insert first object id: ";
	private static final String SECOND_OBJECT_ID = "Insert second object id: ";
	private static final String SELECT_ASTRO_OBJECT = "Select the Astronomical Object id you want to remove: ";
	private static final String SELECT_AND_ASTRO_OBJECT_TYPE = "Select and Astro Object type to add: ";
	
	//ERROR MESSAGES
	private static final String CAN_T_FIND_MOON = "Can't find this moon!";
	private static final String CAN_T_FIND_PLANET = "Can't find this planet!";
	private static final String REMOVED_SUCCESSFULLY = "Object was removed successfully!";
	private static final String CAN_T_FIND_ASTRO_OBJECT = "Can't find the Astronomical Object";
	private static final String CAN_T_REMOVE_STAR = "Can't remove the star!";
	private static final String ERROR_NO_PLANETS = "Must enter at least 1 planet!";
	
	//MENU COMANDS 
	public static final String[] INPUT_LIST_TYPE = {"PLANET", "MOON"};
	public static final String[] INPUT_LIST_METHODS = {"ADD PLANET", "ADD MOON", "REMOVE PLANET", "REMOVE MOON", 
			"CHECK AN ASTRONOMICAL OBJECT ID", "GET PLANET MOONS", "GET MOON ROUTE", "CALCULATE STAR SYSTEM CENTER MASS", 
			"PRINT ROUTE OF TWO ASTRONOMICAL OBJECTS AND CALCULATE THEIR DISTANCE",
			"CALCULATE IF TWO OBJECTS CAN COLLIDE"};

	/**
	 * menu inziale che permette di aggiungere pianeti e lune
	 * controlla che esistano dei pianeti prima di inserire una luna
	 * @param star deve essere dato in input la stella di riferimento
	 */
	public static void startingMenu(Star star) {
		MyMenu menuObjects = new MyMenu(SELECT_AND_ASTRO_OBJECT_TYPE, INPUT_LIST_TYPE);
		int objectOption;
		do {
			objectOption = menuObjects.scegli();
			if (objectOption == 2 && star.getPlanetList().isEmpty()) {
				System.out.println(ERROR_NO_PLANETS);
				objectOption = 1;
			}
			if (objectOption == 1)
				star.getPlanetList().add((Planet)AstroObject.createMoonOrPlanet(1, star));
			else if (objectOption == 2)
				star.getMoonList().add((Moon)AstroObject.createMoonOrPlanet(2, star));
			
		}while (objectOption != 0);
	}
	
	/**
	 * il menu principale che richiama una varietà di metodi applicabili al sistema solare
	 * 1. "ADD PLANET" 
	 * 2. "ADD MOON" 
	 * 3. "REMOVE PLANET" 
	 * 4. "REMOVE MOON" 
	 * 5. "CHECK AN ASTRONOMICAL OBJECT ID" 
	 * 6. "GET PLANET MOONS" 
	 * 7. "GET MOON ROUTE" 
	 * 8. "CALCULATE STAR SYSTEM CENTER MASS" 
	 * 9. "PRINT ROUTE OF TWO ASTRONOMICAL OBJECTS AND CALCULATE THEIR DISTANCE"
	 * 10. "CALCULATE IF TWO OBJECTS CAN COLLIDE"
	 * @param star deve essere fornito in input la stella del sistema stellare
	 */
	public static void mainMenu(Star star) {
		MyMenu methodsMenu = new MyMenu(MESSAGE_MAIN_MENU, INPUT_LIST_METHODS);
		int methodSelection = 0;
		int lookedForId;
		int objectAId;
		int objectBId;
		do {
			methodSelection = methodsMenu.scegli();
			switch(methodSelection) {
				case 1:
					star.getPlanetList().add((Planet) AstroObject.createMoonOrPlanet(1, star));
					break;
				case 2:
					star.getMoonList().add((Moon)AstroObject.createMoonOrPlanet(2, star));
					break;
				case 3:
				case 4:
					do {
						lookedForId = InputDati.leggiInteroConMinimo(SELECT_ASTRO_OBJECT, 0);
						if (lookedForId == 0)
							System.out.println(CAN_T_REMOVE_STAR);
						else if (AstroObject.controlIfIDTaken(lookedForId, star) == false)
							System.out.println(CAN_T_FIND_ASTRO_OBJECT);
					}while(lookedForId == 0 || AstroObject.controlIfIDTaken(lookedForId, star) == false);
					star.removeAstroObject(lookedForId);
					System.out.println(REMOVED_SUCCESSFULLY);
					break;
				case 5:
					if (AstroObject.controlIfIDTaken(InputDati.leggiInteroConMinimo(MESSAGE_CHECK_ID, 0), star))
						System.out.println(String.format(THE_INPUT_OBJECT_BELONGS_TO_STAR, star.getName()));
					else
						System.out.println(CAN_T_FIND_ASTRO_OBJECT);
					break;
				case 6:
					do {
						lookedForId = InputDati.leggiInteroConMinimo(MESSAGE_CHECK_ID, 0);
						if (star.getPlanet(lookedForId) == null)
							System.out.println(CAN_T_FIND_PLANET);
					}while(star.getPlanet(lookedForId) == null);
					star.getPlanet(lookedForId).printMoonsNameList();
					break;
				case 7:
					do {
						lookedForId = InputDati.leggiInteroConMinimo(MESSAGE_CHECK_ID, 0);
						if (star.getMoon(lookedForId) == null)
							System.out.println(CAN_T_FIND_MOON);
					}while(star.getMoon(lookedForId) == null);
					StarSystem.printMoonRoute(star.getMoon(lookedForId));
					break;
				case 8:
					System.out.println(String.format(MASS_CENTER_MESSAGE, 
							star.getName(), 
							star.calculateMassCenterX(), 
							star.calculateMassCenterY()));
					break;
				case 9:

					do {
						objectAId = InputDati.leggiInteroConMinimo(FIRST_OBJECT_ID, 0);
						if (AstroObject.controlIfIDTaken(objectAId, star) == false)
							System.out.println(CAN_T_FIND_ASTRO_OBJECT);
					}while(AstroObject.controlIfIDTaken(objectAId, star) == false);
					
					do {
						objectBId = InputDati.leggiInteroConMinimo(SECOND_OBJECT_ID, 0);
						if (AstroObject.controlIfIDTaken(objectAId, star) == false)
							System.out.println(CAN_T_FIND_ASTRO_OBJECT);
					}while(AstroObject.controlIfIDTaken(objectAId, star) == false);
					
					String route = StarSystem.printTravelRoute(StarSystem.getAstroObjectById(objectAId, star), StarSystem.getAstroObjectById(objectBId, star), star);
					System.out.println(route);
					System.out.println(String.format(DISTANCE_MESSAGE, StarSystem.calculateDistanceFromRoute(StarSystem.getStringArrayFromRoute(route, star), star)));
					break;
				case 10:
					do {
						objectAId = InputDati.leggiInteroConMinimo(FIRST_OBJECT_ID, 0);
						if (AstroObject.controlIfIDTaken(objectAId, star) == false)
							System.out.println(CAN_T_FIND_ASTRO_OBJECT);
					}while(AstroObject.controlIfIDTaken(objectAId, star) == false);
					
					do {
						objectBId = InputDati.leggiInteroConMinimo(SECOND_OBJECT_ID, 0);
						if (AstroObject.controlIfIDTaken(objectAId, star) == false)
							System.out.println(CAN_T_FIND_ASTRO_OBJECT);
					}while(AstroObject.controlIfIDTaken(objectAId, star) == false);
					if(StarSystem.collisionTest(StarSystem.getAstroObjectById(objectAId, star), StarSystem.getAstroObjectById(objectBId, star), star))
						System.out.println(String.format(COLLISION_MESSAGE, StarSystem.getAstroObjectById(objectAId, star).getName(),StarSystem.getAstroObjectById(objectBId, star).getName()));
				default:
					break;

			}
			
		}while (methodSelection != 0);
	}
	
}
