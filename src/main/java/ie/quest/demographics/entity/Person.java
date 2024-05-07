package ie.quest.demographics.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
public class Person {

	/*
	 * Let's use the @NotNull annotation for required fields over 
	 * the @Column(nullable = false) annotation. This way, we make sure 
	 * the validation takes place before Hibernate sends any insert or 
	 * update SQL queries to the database.
	 */

	@Id
	@NotNull
	@Column(length = 25)
	private String ppsn;

	@NotNull
	@Column(length = 25)
	private String name;

	/*
	 * Let's use LocalDate/LocalDateTime over Date as it is easier 
	 * format/parsing with its own format/parse methods and it also
	 * includes addition/subtraction operations (if needed in the future)
	 */
	@NotNull
	private LocalDate dob;

	@Column(name="mobile_phone")
	private String mobilePhone;

	@Column(name="created_dtm")
	private LocalDateTime createdDtm;

	@Column(name="modified_dtm")
	private LocalDateTime modifiedDtm;


	//*As this is a demo, instead of using lombok/records for getters and setters, let's write them along with 
	//*overridden equals and hashcode methods 
	
	public String getPpsn() {
		return ppsn;
	}

	public void setPpsn(String ppsn) {
		this.ppsn = ppsn;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public LocalDateTime getCreatedDtm() {
		return createdDtm;
	}

	public void setCreatedDtm(LocalDateTime createdDtm) {
		this.createdDtm = createdDtm;
	}

	public LocalDateTime getModifiedDtm() {
		return modifiedDtm;
	}

	public void setModifiedDtm(LocalDateTime modifiedDtm) {
		this.modifiedDtm = modifiedDtm;
	}
	
	/**
	 * Let's override the equals method here which will be crucial when we are thinking about caching using one of
	 * the hash classes. In our case, the Person object is equal to another Person object if their PPSN is identical.
	 */

	@Override
	public boolean equals(Object obj) {

		if (this == obj)  //compare to itself
			return true;
		if (!(obj instanceof Person)) //check if obj is instance of Person
			return false;
		Person person = (Person) obj;
		return Objects.equals(this.ppsn, person.ppsn);
	}

	/**
	 * Since we have overridden equals, it is also recommended to override hashCode so we can find our stored object
	 * It is also important to have efficient hashCode so our data structure can find the object in search more efficiently    
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.ppsn);
	}

	@Override
	public String toString() {
		return "Person {" + "ppsn=" + this.ppsn + ", name='" + this.name + '\'' + 
				", DOB='" + this.dob + '\'' + ", mobile='" + this.mobilePhone + '\'' + 
				", CreatedDate='" + this.createdDtm + '\'' + ", ModifiedDate='" + this.modifiedDtm + '\'' + "}";
	}


}
