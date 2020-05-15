package planetarium;

public class PlanetariumMain {

		private static final String FIRST_MESSAGE = "First start by creating the star";
		private static final String WELCOME = "Welcome to the planetarium project";

		public static void main(String[] args) {

		System.out.println(WELCOME);
		System.out.println(FIRST_MESSAGE);
		
		Star star = StarSystem.createStar();
//		Star star = new Star(0, "Sole", 30, 0, 0, TypeAstroObject.STAR);
		
		MainUtility.startingMenu(star);	
		//Dei valori preimpostati utili per le prove:
//		star.getPlanetList().add(new Planet(1, "Planet 1", 5, 0, -3, TypeAstroObject.PLANET, star));
//		star.getPlanetList().add(new Planet(2, "Planet 2", 7, 3, 3, TypeAstroObject.PLANET, star));
//		star.getPlanetList().add(new Planet(3, "Planet 3", 8, -3, 0, TypeAstroObject.PLANET, star));
//		star.getPlanetList().get(0).getMoonList().add(new Moon(4, "Moon 1", 1, -1, -4, TypeAstroObject.MOON, star.getPlanetList().get(0)));
//		star.getPlanetList().get(1).getMoonList().add(new Moon(5, "Moon 2", 2, 2, 3, TypeAstroObject.MOON, star.getPlanetList().get(1)));
//		star.getPlanetList().get(1).getMoonList().add(new Moon(6, "Moon 3", 1, 4, 4, TypeAstroObject.MOON, star.getPlanetList().get(1)));
//		star.getPlanetList().get(2).getMoonList().add(new Moon(7, "Moon 4", 2, -6, 0, TypeAstroObject.MOON, star.getPlanetList().get(2)));
		
		MainUtility.mainMenu(star);		
				
	}
}
