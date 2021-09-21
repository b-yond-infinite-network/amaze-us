package com.mg.challenge.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_SCHEDULE")
public class Schedule {
	//given day/time
	@Id
	private SchedulePK primaryKey;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "F_DATEFROM")
	@Temporal(TemporalType.DATE)
	private Date from;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "F_DATETO")
	@Temporal(TemporalType.DATE)
	private Date to;
}

/*
 CREATE TABLE T_SCHEDULE (
 	F_BUSID INTEGER,
 	F_SSN VARCHAR2(50),
 	F_DATEFROM DATE NOT NULL,
 	F_DATETO DATE NOT NULL,
 	PRIMARY KEY (F_BUSID, F_SSN)
 )
 */
