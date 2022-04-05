package BookManage;

public class Books {
	
	String title;
	String author;
	String publisher;
	String borrower;
	String rentalDate;
	String returnDate;
	
	public Books(String title, String author, String publisher, String borrower, String rentalDate, String returnDate) {
		super();
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.borrower = borrower;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
	
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(String rentalDate) {
		this.rentalDate = rentalDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString() {
		return title + "," + author + "," + publisher + "," + borrower
				+ "," + rentalDate + "," + returnDate;
	}
	
	
	
	

}
