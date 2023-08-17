package com.org.ps.martek.service;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.ps.martek.data.ProductRepository;
import com.org.ps.martek.data.ProductWorkFlow;
import com.org.ps.martek.data.ProductWorkflowRepository;
import com.org.ps.martek.dto.Product;
import com.org.ps.martek.dto.ProductSearch;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;


@Service
public class ProductService {

	
	//@Autowired
	private static ProductRepository productRepository;
	
	private static ProductWorkflowRepository productFlowRepository;
	
	

	Logger log = LoggerFactory.getLogger(ProductService.class);
	
//private static KafkaTemplate<String, ProductSearch>   kafkaTemplate;
	
	//private static  List<CustomerDTO> outTimeList ;
	private static String SWIPE_IN_TOPIC ; 
	
	@PostConstruct
	public void init() {
		ProductService.productRepository = wiredrepository;
	//	ProductService.kafkaTemplate = wiredkafkaTemplate;
	//	ProductService.SWIPE_IN_TOPIC = WIRED_SWIPE_IN_TOPIC;
		ProductService.productFlowRepository = wiredFlowrepository;
	}
	
	@Autowired
	ProductRepository wiredrepository;
	
	@Autowired
	ProductWorkflowRepository wiredFlowrepository;
	
	
	@Autowired
	EntityManager entity;
	
	
//	@Autowired
	//KafkaTemplate<String, ProductSearch>   wiredkafkaTemplate;
	
	@Value(value = "${spring.kafka.swipe.in.topic}")
	private  String WIRED_SWIPE_IN_TOPIC ;
	 

	
	public String findProduct(ProductSearch product) {
		triggerWorkFlow(product);
		 return checkResponse(product);
	}
	
	private void triggerWorkFlow(ProductSearch product) {
		publishWorkflow(product);
		publishToKafka(product);
	}
	
	private void publishToKafka(ProductSearch product) {
		String brokers = "glider.srvs.cloudkafka.com:9094";
		String username = "eidflxfs";
		String password = "PxIX3BTEcbFJbljRiixvpCVAuuB1DkIN";
		KafkaService c = new KafkaService(brokers, username, password);
        c.produce(product);
	}
	
	private void publishWorkflow(ProductSearch response) {
		ProductWorkFlow productWorkflow = productFlowRepository.getSearch(response.getFlowId(), "SEARCH_COMPLETE");
		if(productWorkflow==null)
		productWorkflow = new ProductWorkFlow();
		String message="";
		try {
			 message = new ObjectMapper().writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean b= productWorkflow.getId()!=null;
		Long id = b==true?productWorkflow.getId():productFlowRepository.getLastID();
		productWorkflow.setId(id==null?1:id);
		productWorkflow.setMessage(message);
		productWorkflow.setToday(LocalDate.now().toString());
		productWorkflow.setWorkflowId(response.getFlowId());
		productWorkflow.setStatus("SEARCH_PROGRESS");
		productWorkflow.setUsername(response.getUsername());
		productFlowRepository.saveAndFlush(productWorkflow);
	}
	
	private String checkResponse(ProductSearch response) {
		entity.clear();
		while(true) {
			ProductWorkFlow pp = productFlowRepository.getSearch(response.getFlowId(), "SEARCH_COMPLETE");
			if(pp!=null) {
				return pp.getMessage();
			}
		}
	}

	public void add(Product product) {
		Optional pro = productRepository.findById(product.getId());
		com.org.ps.martek.data.Product prod = (com.org.ps.martek.data.Product)(pro.isEmpty()?new com.org.ps.martek.data.Product(): pro.get());
		//Long id = b==true?productWorkflow.getId():productFlowRepository.getLastID();
		prod.setQuantity(product.getQuantity());;
		prod.setId(product.getId());
		prod.setName(product.getName());
		prod.setLocation(product.getLocation());
		productRepository.save(prod);

	}
	
}
