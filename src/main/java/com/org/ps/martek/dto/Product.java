package com.org.ps.martek.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {

	private String name;
	
	private String id;
	
	private String location;
	
	private String flowId;
	
	private int quantity;
}
