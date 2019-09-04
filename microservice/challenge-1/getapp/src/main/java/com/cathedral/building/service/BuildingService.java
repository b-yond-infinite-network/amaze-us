package com.cathedral.building.service;

import java.util.List;

import com.cathedral.building.model.Form;

public interface BuildingService {
	public List<Form> getFormData() throws Exception;
}