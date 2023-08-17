package com.org.ps.martek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.ps.martek.dto.Product;
import com.org.ps.martek.dto.ProductSearch;
import com.org.ps.martek.service.ProductService;

@RestController
public class ProductController {
	
	  @Autowired
	 private   ProductService productService;
	 
	  /*
	   * name	-  	  Product Name
	   * location -   Delivery location
	   * store-id  -  store in which product is scanned
	   */
	  
	@PostMapping("/products")
	public String allProduct(@RequestBody ProductSearch product) {
		///Product p = new Product();
		//p.setName(name);
		return productService.findProduct(product);
	}
	
	@PostMapping("/update")
	public String updateProduct(@RequestBody Product product) {
		///Product p = new Product();
		//p.setName(name);
		 productService.add(product);
		 return "";
	}
}
