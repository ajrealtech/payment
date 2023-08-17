package com.org.ps.martek.data;

import java.util.Objects;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product_Workflow")
@Cacheable(false)

public class ProductWorkFlow {

	@Override
	public int hashCode() {
		return Objects.hash(id, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductWorkFlow other = (ProductWorkFlow) obj;
		return Objects.equals(id, other.id) && Objects.equals(username, other.username);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "workflow_Id")
	private String workflowId;
	
	@Column(name = "today_date")
	private String today;
	
	@Column(name = "status")
	private String status;


	@Column(name = "message")
	private String message;

	@Column(name = "username")
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
