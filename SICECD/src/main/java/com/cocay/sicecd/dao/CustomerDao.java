package com.cocay.sicecd.dao;

import java.util.List;

import com.cocay.sicecd.model.Customer;

public interface CustomerDao {
	
	  public void insert(List<? extends Customer> customers);
	  List<Customer> loadAllCustomers();

}
