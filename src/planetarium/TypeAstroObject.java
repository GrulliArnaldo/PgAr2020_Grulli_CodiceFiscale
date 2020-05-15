package planetarium;


	public enum TypeAstroObject {
		STAR, PLANET, MOON;
		
		/**
		 * prende in input una string e restituisce se presente l'oggetto corrispondente come enum TypeAstroObject
		 * @param type deve essere una stringa contenente STAR/PLANET/MOON
		 * @return restituisce l'oggetto di tipo TypeAstroObject
		 */
		public static TypeAstroObject getType(String type) {
			for(TypeAstroObject list : values()) 
				if (list.toString().equalsIgnoreCase(type))
					return list;
			return null;
				
		}
	}
