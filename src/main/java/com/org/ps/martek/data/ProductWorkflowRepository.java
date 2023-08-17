package com.org.ps.martek.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductWorkflowRepository extends JpaRepository<ProductWorkFlow, Integer> {
	

	public static final String WORKFLOW = "select * from product_workflow where workflow_id= :id ";
	@Query(value = WORKFLOW, nativeQuery = true)
	public List<ProductWorkFlow> getWorkflowfromId(@Param("id") String id );

	
	public static final String SEARCH = "select * from product_workflow where workflow_id= :id and status=:status";
	@Query(value = SEARCH, nativeQuery = true)
	public ProductWorkFlow getSearch(@Param("id") String id, @Param("status") String status );
	
	public static final String PREVIOUS_ID = "select max(id)+1 from product_workflow ";
	@Query(value = PREVIOUS_ID, nativeQuery = true)
	public Long getLastID();

	public static final String SET_PROCESSED = "update product_workflow set status = null where today_date = :today and employee_id= :empId ";
	@Modifying(clearAutomatically = true)
	@Query(value = SET_PROCESSED, nativeQuery = true)
	public void setProcessed(@Param("today") String today, @Param("empId") Long empId  );

}
