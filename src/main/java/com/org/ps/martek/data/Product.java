package com.org.ps.martek.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "product")
@Getter
@Setter
public class Product {


	@Id
	@Field(name = "id")
	private String id;

	@Field(name = "productid")
	private String productid;
	
	
	@Field(name = "name")
	private String name;
	
	@Field(name = "productname")
	private String productname;
	
	@Field(name = "location")	
	private String location;
	

	@Field(name = "quantity")	
	private int quantity;
	

}
