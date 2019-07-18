package com.kepler.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "RESOURCE")
@XmlRootElement(name = "resource")
public class Resource {
    
	public static final DateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.FRENCH);
	
	/* Definition of the attribute members composing a role
	 * **************************************************** */
	@Id
	@Column(name = "RESOURCE_ID")
	private Long resourceId;
	
	@Column(name="RESOURCE_NAME")
	private String resourceName;
	
	@Column(name="RESOURCE_DESCRIPTION")
	private String resourceDescription;
	
	@Column(name = "RESOURCE_CRITICALITY")
	private Long resourceCriticality;
	
	@Column(name = "RESOURCE_QUANTITY_VALUE")
	private float resourceQuantityValue;
	
	@Column(name = "RESOURCE_QUANTITY_DATE")
	private Date resourceQuantityDate;
	
	@Column(name = "RESOURCE_WARNING_LEVEL")
	private float resourceWarningLevel;
	
	@Column(name = "RESOURCE_EMERGENCY_LEVEL")
	private float resourceEmergencyLevel;
	
	@OneToMany(mappedBy="resource", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<ResourceVariation> resourceVariations;
	    
	/* Definition of the constructors 
	 * ****************************** */
	public Resource() {
		
		super();
	} 

	public Resource(Long resourceId, String resourceName, String resourceDescription, Long resourceCriticality,
			float resourceQuantityValue, Date resourceQuantityDate, float resourceWarningLevel,
			float resourceEmergencyLevel, List<ResourceVariation> resourceVariations) {
		super();
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.resourceDescription = resourceDescription;
		this.resourceCriticality = resourceCriticality;
		this.resourceQuantityValue = resourceQuantityValue;
		this.resourceQuantityDate = resourceQuantityDate;
		this.resourceWarningLevel = resourceWarningLevel;
		this.resourceEmergencyLevel = resourceEmergencyLevel;
		this.resourceVariations = resourceVariations;
	}

	/* GETTERS / SETTERS
	 * ***************** */	
	public Long getResourceId() {
		return resourceId;
	}
	@XmlElement
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}
	@XmlElement
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceDescription() {
		return resourceDescription;
	}
	@XmlElement
	public void setResourceDescription(String resourceDescription) {
		this.resourceDescription = resourceDescription;
	}

	public Long getResourceCriticality() {
		return resourceCriticality;
	}
	@XmlElement

	public void setResourceCriticality(Long resourceCriticality) {
		this.resourceCriticality = resourceCriticality;
	}

	public float getResourceQuantityValue() {
		return resourceQuantityValue;
	}
	@XmlElement
	public void setResourceQuantityValue(float resourceQuantityValue) {
		this.resourceQuantityValue = resourceQuantityValue;
	}

	public Date getResourceQuantityDate() {
		return resourceQuantityDate;
	}
	@XmlElement
	public void setResourceQuantityDate(Date resourceQuantityDate) {
		this.resourceQuantityDate = resourceQuantityDate;
	}

	public float getResourceWarningLevel() {
		return resourceWarningLevel;
	}
	@XmlElement
	public void setResourceWarningLevel(float resourceWarningLevel) {
		this.resourceWarningLevel = resourceWarningLevel;
	}

	public float getResourceEmergencyLevel() {
		return resourceEmergencyLevel;
	}
	@XmlElement
	public void setResourceEmergencyLevel(float resourceEmergencyLevel) {
		this.resourceEmergencyLevel = resourceEmergencyLevel;
	}

	public List<ResourceVariation> getResourceVariations() {
		return resourceVariations;
	}
	@XmlElement
	public void setResourceVariations(List<ResourceVariation> resourceVariations) {
		this.resourceVariations = resourceVariations;
	}

	public static DateFormat getDateFormatter() {
		return date_formatter;
	}

	@Override
	public String toString() {
		return "Resource [resourceId=" + resourceId + ", resourceName=" + resourceName + ", resourceDescription="
				+ resourceDescription + ", resourceCriticality=" + resourceCriticality + ", resourceQuantityValue="
				+ resourceQuantityValue + ", resourceQuantityDate=" + resourceQuantityDate + ", resourceWarningLevel="
				+ resourceWarningLevel + ", resourceEmergencyLevel=" + resourceEmergencyLevel + ", resourceVariations="
				+ resourceVariations + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceCriticality == null) ? 0 : resourceCriticality.hashCode());
		result = prime * result + ((resourceDescription == null) ? 0 : resourceDescription.hashCode());
		result = prime * result + Float.floatToIntBits(resourceEmergencyLevel);
		result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
		result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
		result = prime * result + ((resourceQuantityDate == null) ? 0 : resourceQuantityDate.hashCode());
		result = prime * result + Float.floatToIntBits(resourceQuantityValue);
		result = prime * result + ((resourceVariations == null) ? 0 : resourceVariations.hashCode());
		result = prime * result + Float.floatToIntBits(resourceWarningLevel);
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
		Resource other = (Resource) obj;
		if (resourceCriticality == null) {
			if (other.resourceCriticality != null)
				return false;
		} else if (!resourceCriticality.equals(other.resourceCriticality))
			return false;
		if (resourceDescription == null) {
			if (other.resourceDescription != null)
				return false;
		} else if (!resourceDescription.equals(other.resourceDescription))
			return false;
		if (Float.floatToIntBits(resourceEmergencyLevel) != Float.floatToIntBits(other.resourceEmergencyLevel))
			return false;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		if (resourceName == null) {
			if (other.resourceName != null)
				return false;
		} else if (!resourceName.equals(other.resourceName))
			return false;
		if (resourceQuantityDate == null) {
			if (other.resourceQuantityDate != null)
				return false;
		} else if (!resourceQuantityDate.equals(other.resourceQuantityDate))
			return false;
		if (Float.floatToIntBits(resourceQuantityValue) != Float.floatToIntBits(other.resourceQuantityValue))
			return false;
		if (resourceVariations == null) {
			if (other.resourceVariations != null)
				return false;
		} else if (!resourceVariations.equals(other.resourceVariations))
			return false;
		if (Float.floatToIntBits(resourceWarningLevel) != Float.floatToIntBits(other.resourceWarningLevel))
			return false;
		return true;
	}

	public boolean init() {
		
		this.setResourceName(this.getResourceName().toLowerCase());
		this.setResourceDescription(this.getResourceDescription().toLowerCase());
		this.setResourceVariations(new ArrayList<ResourceVariation>());
		
		return true;
	}
	
	public void add(ResourceVariation resourceVariation) {
		
		if (this.getResourceVariations() == null) {
			this.setResourceVariations(new ArrayList<ResourceVariation>());
		}
		
		this.getResourceVariations().add(resourceVariation);
		resourceVariation.setResource(this);
	}
}
