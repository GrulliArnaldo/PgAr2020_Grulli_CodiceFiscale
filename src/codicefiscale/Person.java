package codicefiscale;

public class Person {

	private String name;
	private String surname;
	private char sex;
	private Date birthDate;
	private String birthPlace;
	
	public Person(String name, String surname, char sex, Date birthDate, String birthPlace) {
		this.name = name;
		this.surname = surname;
		this.sex = sex;
		this.birthDate = birthDate;
		this.birthPlace = birthPlace;
	}

	//GETTERS AND SETTERS
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
	
	
	

}
