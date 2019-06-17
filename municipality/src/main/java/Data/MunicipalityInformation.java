package Data;

public class MunicipalityInformation {
	String telephone, fax, email, munName, munCity, munZone, munStreet;

	
	public MunicipalityInformation() {
		super();
	}

	public MunicipalityInformation(String telephone, String fax, String email, String munName, String munCity,
			String munZone, String munStreet) {
		super();
		this.telephone = telephone;
		this.fax = fax;
		this.email = email;
		this.munName = munName;
		this.munCity = munCity;
		this.munZone = munZone;
		this.munStreet = munStreet;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMunName() {
		return munName;
	}

	public void setMunName(String munName) {
		this.munName = munName;
	}

	public String getMunCity() {
		return munCity;
	}

	public void setMunCity(String munCity) {
		this.munCity = munCity;
	}

	public String getMunZone() {
		return munZone;
	}

	public void setMunZone(String munZone) {
		this.munZone = munZone;
	}

	public String getMunStreet() {
		return munStreet;
	}

	public void setMunStreet(String munStreet) {
		this.munStreet = munStreet;
	}
	
}
