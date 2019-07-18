package com.kepler.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "RESOURCE_VARIATION")
@XmlRootElement(name = "resource_variation")
public class ResourceVariation {
   
	public static final DateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.FRENCH);

	/* Definition of the attribute members composing a role
	 * **************************************************** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RESOURCE_VARIATION_ID")
	private Long resourceVariationId;
	
	@Column(name="RESOURCE_VARIATION_VALUE")
	private float resourceVariationValue;
	
	@Column(name="RESOURCE_VARIATION_DATE")
	private Date resourceVariationDate;
	
	@Column(name="RESOURCE_VARIATION_APPLICATION")
	private boolean resourceVariationApplication;
	
	@Column(name="RESOURCE_VARIATION_DESCRIPTION")
	private String resourceVariationDescription;
	
	//@OneToOne(cascade = CascadeType.ALL)
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "resourceId")
	private Resource resource;

	/* Definition of the constructors 
	 * ****************************** */
	public ResourceVariation() {
		
		super();
		
	}
	
	public ResourceVariation(Long resourceVariationId, float resourceVariationValue, Date resourceVariationDate,
			boolean resourceVariationApplication, String resourceVariationDescription, Resource resource) {
		
		super();
		
		this.resourceVariationId = resourceVariationId;
		this.resourceVariationValue = resourceVariationValue;
		this.resourceVariationDate = resourceVariationDate;
		this.resourceVariationApplication = resourceVariationApplication;
		this.resourceVariationDescription = resourceVariationDescription;
		this.resource = resource;
	}

	/* GETTERS / SETTERS
	 * ***************** */	
	public Long getResourceVariationId() {
		return resourceVariationId;
	}
	@XmlElement
	public void setResourceVariationId(Long resourceVariationId) {
		this.resourceVariationId = resourceVariationId;
	}

	public float getResourceVariationValue() {
		return resourceVariationValue;
	}
	@XmlElement
	public void setResourceVariationValue(float resourceVariationValue) {
		this.resourceVariationValue = resourceVariationValue;
	}

	public Date getResourceVariationDate() {
		return resourceVariationDate;
	}
	@XmlElement
	public void setResourceVariationDate(Date resourceVariationDate) {
		this.resourceVariationDate = resourceVariationDate;
	}

	public boolean isResourceVariationApplication() {
		return resourceVariationApplication;
	}
	@XmlElement
	public void setResourceVariationApplication(boolean resourceVariationApplication) {
		this.resourceVariationApplication = resourceVariationApplication;
	}

	public String getResourceVariationDescription() {
		return resourceVariationDescription;
	}
	@XmlElement
	public void setResourceVariationDescription(String resourceVariationDescription) {
		this.resourceVariationDescription = resourceVariationDescription;
	}

	public Resource getResource() {
		return resource;
	}
	@XmlElement
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public static DateFormat getDateFormatter() {
		return date_formatter;
	}

	@Override
	public String toString() {
		return "ResourceVariation [resourceVariationId=" + resourceVariationId + ", resourceVariationValue="
				+ resourceVariationValue + ", resourceVariationDate=" + resourceVariationDate
				+ ", resourceVariationApplication=" + resourceVariationApplication + ", resourceVariationDescription="
				+ resourceVariationDescription + ", resource=" + resource + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
		result = prime * result + (resourceVariationApplication ? 1231 : 1237);
		result = prime * result + ((resourceVariationDate == null) ? 0 : resourceVariationDate.hashCode());
		result = prime * result
				+ ((resourceVariationDescription == null) ? 0 : resourceVariationDescription.hashCode());
		result = prime * result + ((resourceVariationId == null) ? 0 : resourceVariationId.hashCode());
		result = prime * result + Float.floatToIntBits(resourceVariationValue);
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
		ResourceVariation other = (ResourceVariation) obj;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		if (resourceVariationApplication != other.resourceVariationApplication)
			return false;
		if (resourceVariationDate == null) {
			if (other.resourceVariationDate != null)
				return false;
		} else if (!resourceVariationDate.equals(other.resourceVariationDate))
			return false;
		if (resourceVariationDescription == null) {
			if (other.resourceVariationDescription != null)
				return false;
		} else if (!resourceVariationDescription.equals(other.resourceVariationDescription))
			return false;
		if (resourceVariationId == null) {
			if (other.resourceVariationId != null)
				return false;
		} else if (!resourceVariationId.equals(other.resourceVariationId))
			return false;
		if (Float.floatToIntBits(resourceVariationValue) != Float.floatToIntBits(other.resourceVariationValue))
			return false;
		return true;
	}
	
	public boolean init() {
		
		this.setResourceVariationDescription(this.getResourceVariationDescription().toLowerCase());
		
		return true;
	}
	
}
