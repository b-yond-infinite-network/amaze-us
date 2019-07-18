package com.kepler.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PIONEER")
@XmlRootElement(name = "pioneer")
public class Pioneer {
   
	public static final DateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.FRENCH);

	/* Definition of the attribute members composing a role
	 * **************************************************** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PIONEER_ID")
	private Long pioneerId;
	
	@Column(name="PIONEER_LOGIN")
	private String pioneerLogin;
	
	@Column(name="PIONEER_PASSWORD")
	private String pioneerPassword;
	
	@Column(name="PIONEER_EMAIL")
	private String pioneerEmail;
	
	@Column(name="PIONEER_FIRST_NAME")
	private String pioneerFirstName;
	
	@Column(name="PIONEER_LAST_NAME")
	private String pioneerLastName;
	
	@Column(name="PIONEER_BIRTHDATE")
	private Date pioneerBirthDate;
	
	@Column(name="PIONEER_SEX")
	private boolean pioneerSex;
	
	@Column(name = "PIONEER_NOTATION")
	private Long pioneerNotation;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "userApplicationId")
	private UserApplication userApplication;

	/* Definition of the constructors 
	 * ****************************** */
	public Pioneer() {
		
		super();
		
	}
	
	public Pioneer(Long pioneerId, String pioneerLogin, String pioneerPassword, String pioneerEmail,
			String pioneerFirstName, String pioneerLastName, Date pioneerBirthDate, boolean pioneerSex,
			Long pioneerNotation, UserApplication userApplication) {
		
		super();
		
		this.pioneerId = pioneerId;
		this.pioneerLogin = pioneerLogin;
		this.pioneerPassword = pioneerPassword;
		this.pioneerEmail = pioneerEmail;
		this.pioneerFirstName = pioneerFirstName;
		this.pioneerLastName = pioneerLastName;
		this.pioneerBirthDate = pioneerBirthDate;
		this.pioneerSex = pioneerSex;
		this.pioneerNotation = pioneerNotation;
		this.userApplication = userApplication;
	}

	/* GETTERS / SETTERS
	 * ***************** */	
	public Long getPioneerId() {
		return pioneerId;
	}
	@XmlElement
	public void setPioneerId(Long pioneerId) {
		this.pioneerId = pioneerId;
	}

	public String getPioneerLogin() {
		return pioneerLogin;
	}
	@XmlElement
	public void setPioneerLogin(String pioneerLogin) {
		this.pioneerLogin = pioneerLogin;
	}

	public String getPioneerPassword() {
		return pioneerPassword;
	}
	@XmlElement
	public void setPioneerPassword(String pioneerPassword) {
		this.pioneerPassword = pioneerPassword;
	}

	public String getPioneerEmail() {
		return pioneerEmail;
	}
	@XmlElement
	public void setPioneerEmail(String pioneerEmail) {
		this.pioneerEmail = pioneerEmail;
	}

	public String getPioneerFirstName() {
		return pioneerFirstName;
	}
	@XmlElement
	public void setPioneerFirstName(String pioneerFirstName) {
		this.pioneerFirstName = pioneerFirstName;
	}

	public String getPioneerLastName() {
		return pioneerLastName;
	}
	@XmlElement
	public void setPioneerLastName(String pioneerLastName) {
		this.pioneerLastName = pioneerLastName;
	}

	public Date getPioneerBirthDate() {
		return pioneerBirthDate;
	}
	@XmlElement
	public void setPioneerBirthDate(Date pioneerBirthDate) {
		this.pioneerBirthDate = pioneerBirthDate;
	}

	public boolean isPioneerSex() {
		return pioneerSex;
	}
	@XmlElement
	public void setPioneerSex(boolean pioneerSex) {
		this.pioneerSex = pioneerSex;
	}

	public Long getPioneerNotation() {
		return pioneerNotation;
	}
	@XmlElement
	public void setPioneerNotation(Long pioneerNotation) {
		this.pioneerNotation = pioneerNotation;
	}

	public UserApplication getUserApplication() {
		return userApplication;
	}
	@XmlElement
	public void setUserApplication(UserApplication userApplication) {
		this.userApplication = userApplication;
	}

	public static DateFormat getDateFormatter() {
		return date_formatter;
	}

	@Override
	public String toString() {
		return "Pioneer [pioneerId=" + pioneerId + ", pioneerLogin=" + pioneerLogin + ", pioneerPassword="
				+ pioneerPassword + ", pioneerEmail=" + pioneerEmail + ", pioneerFirstName=" + pioneerFirstName
				+ ", pioneerLastName=" + pioneerLastName + ", pioneerBirthDate=" + pioneerBirthDate + ", pioneerSex="
				+ pioneerSex + ", pioneerNotation=" + pioneerNotation + ", userApplication=" + userApplication + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pioneerBirthDate == null) ? 0 : pioneerBirthDate.hashCode());
		result = prime * result + ((pioneerEmail == null) ? 0 : pioneerEmail.hashCode());
		result = prime * result + ((pioneerFirstName == null) ? 0 : pioneerFirstName.hashCode());
		result = prime * result + ((pioneerId == null) ? 0 : pioneerId.hashCode());
		result = prime * result + ((pioneerLastName == null) ? 0 : pioneerLastName.hashCode());
		result = prime * result + ((pioneerLogin == null) ? 0 : pioneerLogin.hashCode());
		result = prime * result + ((pioneerNotation == null) ? 0 : pioneerNotation.hashCode());
		result = prime * result + ((pioneerPassword == null) ? 0 : pioneerPassword.hashCode());
		result = prime * result + (pioneerSex ? 1231 : 1237);
		result = prime * result + ((userApplication == null) ? 0 : userApplication.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pioneer other = (Pioneer) obj;
		if (pioneerBirthDate == null) {
			if (other.pioneerBirthDate != null)
				return false;
		} else if (!pioneerBirthDate.equals(other.pioneerBirthDate))
			return false;
		if (pioneerEmail == null) {
			if (other.pioneerEmail != null)
				return false;
		} else if (!pioneerEmail.equals(other.pioneerEmail))
			return false;
		if (pioneerFirstName == null) {
			if (other.pioneerFirstName != null)
				return false;
		} else if (!pioneerFirstName.equals(other.pioneerFirstName))
			return false;
		if (pioneerId == null) {
			if (other.pioneerId != null)
				return false;
		} else if (!pioneerId.equals(other.pioneerId))
			return false;
		if (pioneerLastName == null) {
			if (other.pioneerLastName != null)
				return false;
		} else if (!pioneerLastName.equals(other.pioneerLastName))
			return false;
		if (pioneerLogin == null) {
			if (other.pioneerLogin != null)
				return false;
		} else if (!pioneerLogin.equals(other.pioneerLogin))
			return false;
		if (pioneerNotation == null) {
			if (other.pioneerNotation != null)
				return false;
		} else if (!pioneerNotation.equals(other.pioneerNotation))
			return false;
		if (pioneerPassword == null) {
			if (other.pioneerPassword != null)
				return false;
		} else if (!pioneerPassword.equals(other.pioneerPassword))
			return false;
		if (pioneerSex != other.pioneerSex)
			return false;
		if (userApplication == null) {
			if (other.userApplication != null)
				return false;
		} else if (!userApplication.equals(other.userApplication))
			return false;
		return true;
	}

	public boolean init() {
		
		this.setPioneerLogin(this.getPioneerLogin().toLowerCase());
		this.setPioneerEmail(this.getPioneerEmail().toLowerCase());
		
		this.setPioneerFirstName(this.getPioneerFirstName().toLowerCase());
		this.setPioneerLastName(this.getPioneerLastName().toLowerCase());
		
		return true;
	}
}
