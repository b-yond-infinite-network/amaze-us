package com.mg.challenge.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "T_SCHEDULE")
public class Schedule implements Serializable {
	private static final long serialVersionUID = 3765199100149401665L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "F_ID")
	private Integer id;
	
	@Column(name = "F_BUSID")
	private Integer busID;

	@Column(name = "F_SSN", length = 50)
	private String driverSSN;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "F_DAY")
	@Temporal(TemporalType.DATE)
	private Date day;
	
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name = "F_TIME_FROM")
	@Temporal(TemporalType.TIME)
	private Date timeFrom;
	
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name = "F_TIME_TO")
	@Temporal(TemporalType.TIME)
	private Date timeTo;
	
	
	
}
