package com.org.ps.martek.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductSearch {

	private List<Product> product;
	
	private String flowId;
	
	private String username;
}
