package com.ms.reminder.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "REMINDER")
public class Reminder implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "REMINDER_ID", nullable = false)
	Long id;
	
    @Column(name = "REMINDER_NAME")
    private String name;
   

	@Column(name = "REMINDER_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    
    @Column(name = "IS_COMPLETE")
    private Boolean isComplete;

	@Override
	public String toString() {
		return "Reminder [id=" + id + ", name=" + name + ", date=" + date + ", isComplete=" + isComplete + "]";
	}
	
	public Reminder() {
		
	}
	public Reminder(ReminderBuilder builder) {
		
		this.id=builder.id;
		this.name=builder.name;
		this.isComplete=builder.isComplete;
		this.date=builder.date;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Reminder other = (Reminder) obj;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}
		
	public static ReminderBuilder builder() {
        return new ReminderBuilder();
    }
	
	public static class ReminderBuilder{
		
		private Long id;
	    private String name;
	    private LocalDateTime date;
	    private Boolean isComplete;
	    
	    private ReminderBuilder() {
    	}
	    
	    
		public ReminderBuilder name(String name) {
			this.name=name;
			return this;
		}
		public ReminderBuilder id(Long id) {
			this.id=id;
			return this;
		}
		
		public ReminderBuilder isComplete(Boolean isComplete) {
			this.isComplete=isComplete;
			return this;
		}
		
		public ReminderBuilder date(LocalDateTime dateTime) {
			this.date=dateTime;
			return this;
		}
		
		public Reminder build() {
			
			return new Reminder(this);
		}
	}
}
