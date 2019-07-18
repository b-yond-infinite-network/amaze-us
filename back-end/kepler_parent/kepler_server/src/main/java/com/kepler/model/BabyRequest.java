package com.kepler.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "BABY_REQUEST")
@XmlRootElement(name = "baby_request")
public class BabyRequest {
   
	public static final DateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.FRENCH);

	/* Definition of the attribute members composing a role
	 * **************************************************** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REQUEST_ID")
	private Long requestId;
	
	@Column(name="REQUEST_CREATE_DATE")
	private Date requestCreateDate;
	
	@Column(name="REQUEST_SUBMIT_DATE")
	private Date requestSubmitDate;
	
	@Column(name="REQUEST_ACCEPT_DATE_1")
	private Date requestAcceptDate1;
	
	@Column(name="REQUEST_ACCEPT_DATE_2")
	private Date requestAcceptDate2;
	
	@Column(name="REQUEST_REFUSE_DATE_1")
	private Date requestRefuseDate1;
	
	@Column(name="REQUEST_REFUSE_DATE_2")
	private Date requestRefuseDate2;
	
	@Column(name="REQUEST_SCHEDULE_DATE")
	private Date requestScheduleDate;
	
	@Column(name="REQUEST_CLOSE_DATE")
	private Date requestCloseDate;
	
	@Column(name="BABY_LOGIN")
	private String babyLogin;
	
	@Column(name="BABY_PASSWORD")
	private String babyPassword;
	
	@Column(name="BABY_EMAIL")
	private String babyEmail;
	
	@Column(name="BABY_FIRST_NAME")
	private String babyFirstName;
	
	@Column(name="BABY_LAST_NAME")
	private String babyLastName;
	
	@Column(name="BABY_SEX")
	private boolean babySex;
	
	// TO DO : add the mapping with parents and checkers
	// -> update the rest of the code

	/* Definition of the constructors 
	 * ****************************** */
	public BabyRequest() {
		
	}
	
	public BabyRequest(Long requestId, Date requestCreateDate, Date requestSubmitDate, Date requestAcceptDate1,
			Date requestAcceptDate2, Date requestRefuseDate1, Date requestRefuseDate2, Date requestScheduleDate,
			Date requestCloseDate, String babyLogin, String babyPassword, String babyEmail, String babyFirstName,
			String babyLastName, boolean babySex) {
		
		super();
		
		this.requestId = requestId;
		this.requestCreateDate = requestCreateDate;
		this.requestSubmitDate = requestSubmitDate;
		this.requestAcceptDate1 = requestAcceptDate1;
		this.requestAcceptDate2 = requestAcceptDate2;
		this.requestRefuseDate1 = requestRefuseDate1;
		this.requestRefuseDate2 = requestRefuseDate2;
		this.requestScheduleDate = requestScheduleDate;
		this.requestCloseDate = requestCloseDate;
		this.babyLogin = babyLogin;
		this.babyPassword = babyPassword;
		this.babyEmail = babyEmail;
		this.babyFirstName = babyFirstName;
		this.babyLastName = babyLastName;
		this.babySex = babySex;
	}

	public Long getRequestId() {
		return requestId;
	}
	@XmlElement
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Date getRequestCreateDate() {
		return requestCreateDate;
	}
	@XmlElement
	public void setRequestCreateDate(Date requestCreateDate) {
		this.requestCreateDate = requestCreateDate;
	}

	public Date getRequestSubmitDate() {
		return requestSubmitDate;
	}
	@XmlElement
	public void setRequestSubmitDate(Date requestSubmitDate) {
		this.requestSubmitDate = requestSubmitDate;
	}

	public Date getRequestAcceptDate1() {
		return requestAcceptDate1;
	}
	@XmlElement
	public void setRequestAcceptDate1(Date requestAcceptDate1) {
		this.requestAcceptDate1 = requestAcceptDate1;
	}

	public Date getRequestAcceptDate2() {
		return requestAcceptDate2;
	}
	@XmlElement
	public void setRequestAcceptDate2(Date requestAcceptDate2) {
		this.requestAcceptDate2 = requestAcceptDate2;
	}

	public Date getRequestRefuseDate1() {
		return requestRefuseDate1;
	}
	@XmlElement
	public void setRequestRefuseDate1(Date requestRefuseDate1) {
		this.requestRefuseDate1 = requestRefuseDate1;
	}

	public Date getRequestRefuseDate2() {
		return requestRefuseDate2;
	}
	@XmlElement
	public void setRequestRefuseDate2(Date requestRefuseDate2) {
		this.requestRefuseDate2 = requestRefuseDate2;
	}

	public Date getRequestScheduleDate() {
		return requestScheduleDate;
	}
	@XmlElement
	public void setRequestScheduleDate(Date requestScheduleDate) {
		this.requestScheduleDate = requestScheduleDate;
	}

	public Date getRequestCloseDate() {
		return requestCloseDate;
	}
	@XmlElement
	public void setRequestCloseDate(Date requestCloseDate) {
		this.requestCloseDate = requestCloseDate;
	}

	public String getBabyLogin() {
		return babyLogin;
	}
	@XmlElement
	public void setBabyLogin(String babyLogin) {
		this.babyLogin = babyLogin;
	}

	public String getBabyPassword() {
		return babyPassword;
	}
	@XmlElement
	public void setBabyPassword(String babyPassword) {
		this.babyPassword = babyPassword;
	}

	public String getBabyEmail() {
		return babyEmail;
	}
	@XmlElement
	public void setBabyEmail(String babyEmail) {
		this.babyEmail = babyEmail;
	}

	public String getBabyFirstName() {
		return babyFirstName;
	}
	@XmlElement
	public void setBabyFirstName(String babyFirstName) {
		this.babyFirstName = babyFirstName;
	}

	public String getBabyLastName() {
		return babyLastName;
	}
	@XmlElement
	public void setBabyLastName(String babyLastName) {
		this.babyLastName = babyLastName;
	}

	public boolean isBabySex() {
		return babySex;
	}
	@XmlElement
	public void setBabySex(boolean babySex) {
		this.babySex = babySex;
	}

	public static DateFormat getDateFormatter() {
		return date_formatter;
	}

	@Override
	public String toString() {
		return "BabyRequest [requestId=" + requestId + ", requestCreateDate=" + requestCreateDate
				+ ", requestSubmitDate=" + requestSubmitDate + ", requestAcceptDate1=" + requestAcceptDate1
				+ ", requestAcceptDate2=" + requestAcceptDate2 + ", requestRefuseDate1=" + requestRefuseDate1
				+ ", requestRefuseDate2=" + requestRefuseDate2 + ", requestScheduleDate=" + requestScheduleDate
				+ ", requestCloseDate=" + requestCloseDate + ", babyLogin=" + babyLogin + ", babyPassword="
				+ babyPassword + ", babyEmail=" + babyEmail + ", babyFirstName=" + babyFirstName + ", babyLastName="
				+ babyLastName + ", babySex=" + babySex + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((babyEmail == null) ? 0 : babyEmail.hashCode());
		result = prime * result + ((babyFirstName == null) ? 0 : babyFirstName.hashCode());
		result = prime * result + ((babyLastName == null) ? 0 : babyLastName.hashCode());
		result = prime * result + ((babyLogin == null) ? 0 : babyLogin.hashCode());
		result = prime * result + ((babyPassword == null) ? 0 : babyPassword.hashCode());
		result = prime * result + (babySex ? 1231 : 1237);
		result = prime * result + ((requestAcceptDate1 == null) ? 0 : requestAcceptDate1.hashCode());
		result = prime * result + ((requestAcceptDate2 == null) ? 0 : requestAcceptDate2.hashCode());
		result = prime * result + ((requestCloseDate == null) ? 0 : requestCloseDate.hashCode());
		result = prime * result + ((requestCreateDate == null) ? 0 : requestCreateDate.hashCode());
		result = prime * result + ((requestId == null) ? 0 : requestId.hashCode());
		result = prime * result + ((requestRefuseDate1 == null) ? 0 : requestRefuseDate1.hashCode());
		result = prime * result + ((requestRefuseDate2 == null) ? 0 : requestRefuseDate2.hashCode());
		result = prime * result + ((requestScheduleDate == null) ? 0 : requestScheduleDate.hashCode());
		result = prime * result + ((requestSubmitDate == null) ? 0 : requestSubmitDate.hashCode());
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
		BabyRequest other = (BabyRequest) obj;
		if (babyEmail == null) {
			if (other.babyEmail != null)
				return false;
		} else if (!babyEmail.equals(other.babyEmail))
			return false;
		if (babyFirstName == null) {
			if (other.babyFirstName != null)
				return false;
		} else if (!babyFirstName.equals(other.babyFirstName))
			return false;
		if (babyLastName == null) {
			if (other.babyLastName != null)
				return false;
		} else if (!babyLastName.equals(other.babyLastName))
			return false;
		if (babyLogin == null) {
			if (other.babyLogin != null)
				return false;
		} else if (!babyLogin.equals(other.babyLogin))
			return false;
		if (babyPassword == null) {
			if (other.babyPassword != null)
				return false;
		} else if (!babyPassword.equals(other.babyPassword))
			return false;
		if (babySex != other.babySex)
			return false;
		if (requestAcceptDate1 == null) {
			if (other.requestAcceptDate1 != null)
				return false;
		} else if (!requestAcceptDate1.equals(other.requestAcceptDate1))
			return false;
		if (requestAcceptDate2 == null) {
			if (other.requestAcceptDate2 != null)
				return false;
		} else if (!requestAcceptDate2.equals(other.requestAcceptDate2))
			return false;
		if (requestCloseDate == null) {
			if (other.requestCloseDate != null)
				return false;
		} else if (!requestCloseDate.equals(other.requestCloseDate))
			return false;
		if (requestCreateDate == null) {
			if (other.requestCreateDate != null)
				return false;
		} else if (!requestCreateDate.equals(other.requestCreateDate))
			return false;
		if (requestId == null) {
			if (other.requestId != null)
				return false;
		} else if (!requestId.equals(other.requestId))
			return false;
		if (requestRefuseDate1 == null) {
			if (other.requestRefuseDate1 != null)
				return false;
		} else if (!requestRefuseDate1.equals(other.requestRefuseDate1))
			return false;
		if (requestRefuseDate2 == null) {
			if (other.requestRefuseDate2 != null)
				return false;
		} else if (!requestRefuseDate2.equals(other.requestRefuseDate2))
			return false;
		if (requestScheduleDate == null) {
			if (other.requestScheduleDate != null)
				return false;
		} else if (!requestScheduleDate.equals(other.requestScheduleDate))
			return false;
		if (requestSubmitDate == null) {
			if (other.requestSubmitDate != null)
				return false;
		} else if (!requestSubmitDate.equals(other.requestSubmitDate))
			return false;
		return true;
	}

	public boolean init() {
				
		this.setBabyEmail(this.getBabyEmail().toLowerCase());
		this.setBabyFirstName(this.getBabyFirstName().toLowerCase());
		this.setBabyLastName(this.getBabyLastName().toLowerCase());
		
		if (this.getRequestCreateDate() == null) {
			
			this.setRequestCreateDate(new Date());
		}
		
		return true;
	}
	
	public boolean check_creation_constraint() {
		
		Date requestCreate = this.getRequestCreateDate();
		Date requestSubmit = this.getRequestSubmitDate();
		Date requestAccept1 = this.getRequestAcceptDate1();
		Date requestAccept2 = this.getRequestAcceptDate2();
		Date requestRefuse1 = this.getRequestRefuseDate1();
		Date requestRefuse2 = this.getRequestRefuseDate2();
		Date requestSchedule = this.getRequestScheduleDate();
		Date requestClose = this.getRequestCloseDate();
		
		if (requestCreate != null && requestSubmit != null && requestCreate.after(requestSubmit)) {
			return false;
		} else if (requestCreate != null && requestAccept1 != null && requestCreate.after(requestAccept1)) {
			return false;
		} else if (requestCreate != null && requestAccept2 != null && requestCreate.after(requestAccept2)) {
			return false;
		} else if (requestCreate != null && requestRefuse1 != null && requestCreate.after(requestRefuse1)) {
			return false;
		} else if (requestCreate != null && requestRefuse2 != null && requestCreate.after(requestRefuse2)) {
			return false;
		} else if (requestCreate != null && requestSchedule != null && requestCreate.after(requestSchedule)) {
			return false;
		} else if (requestCreate != null && requestClose != null && requestCreate.after(requestClose)) {
			return false;
		}
		
		return true;
	}
	
	public boolean check_submit_constraint() {
		
		Date requestSubmit = this.getRequestSubmitDate();
		Date requestAccept1 = this.getRequestAcceptDate1();
		Date requestAccept2 = this.getRequestAcceptDate2();
		Date requestRefuse1 = this.getRequestRefuseDate1();
		Date requestRefuse2 = this.getRequestRefuseDate2();
		Date requestSchedule = this.getRequestScheduleDate();
		Date requestClose = this.getRequestCloseDate();
		
		if (requestSubmit != null && requestAccept1 != null && requestSubmit.after(requestAccept1)) {
			return false;
		} else if (requestSubmit != null && requestAccept2 != null && requestSubmit.after(requestAccept2)) {
			return false;
		} else if (requestSubmit != null && requestRefuse1 != null && requestSubmit.after(requestRefuse1)) {
			return false;
		} else if (requestSubmit != null && requestRefuse2 != null && requestSubmit.after(requestRefuse2)) {
			return false;
		} else if (requestSubmit != null && requestSchedule != null && requestSubmit.after(requestSchedule)) {
			return false;
		} else if (requestSubmit != null && requestClose != null && requestSubmit.after(requestClose)) {
			return false;
		}
		
		return true;
	}




	
}
