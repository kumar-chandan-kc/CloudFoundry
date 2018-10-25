package com.trail.files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;


import org.apache.olingo.commons.api.data.EntitySet;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.core.data.EntityImpl;
import org.apache.olingo.commons.core.data.EntitySetImpl;
import org.apache.olingo.commons.core.data.PropertyImpl;
import org.apache.olingo.server.api.edm.provider.EntityType;
import org.apache.olingo.server.api.edm.provider.Property;
import org.apache.olingo.server.api.edm.provider.PropertyRef;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.springframework.beans.factory.annotation.Autowired;

import com.trail.edm.providers.EntityProvider;

@Entity
@Table(name = "master_list")

public class masterlist{
	@Id
	@Column(name = "sap_object_type")
	private String sot;
	@Column(name = "description")
	private String desc;

	public masterlist(String string, String string2) {
		// TODO Auto-generated constructor stub
		this.sot = string;
		this.desc = string2;
		
	}
	
	
	public masterlist() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setSot(String sot) {
		this.sot = sot;
	}

	public String getSot() {
		return sot;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	





	

}