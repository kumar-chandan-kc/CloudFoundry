package com.trail.files;

import javax.persistence.*;

@Entity
@Table(name = "master_list")

public class masterlist {
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