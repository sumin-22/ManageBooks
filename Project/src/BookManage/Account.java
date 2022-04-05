package BookManage;

 class Account {
	private String name;
	private String birthDate;
	private String cellphone;
	private String id;
	private String password;
	
	//생성자
	public Account(String name, String birthDate, String cellphone, String id, String password) {
		this.name = name;
		this.birthDate = birthDate;
		this.cellphone = cellphone;
		this.id = id;
		this.password = password;
	}



	//getter & setter 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return name + "," + birthDate + "," + cellphone + "," + id
				+ "," + password;
	}
	
	

}
