package com.org.ps.martek.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {

	private String name;
	
	private String password;
	
	private String address;
	
	private String phone;
	
	@NotNull
	private String username;
}
