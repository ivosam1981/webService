package com.ivosam.webservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ivosam.webservice.bean.Customer;
import com.ivosam.webservice.helper.Status;

@Component
public class CustomerDetailService {

	private static List<Customer> customers = new ArrayList<>();

	static {
		Customer customer1 = new Customer(1, "Bob", "99999", "bob@gmail.com");
		Customer customer2 = new Customer(1, "Mark", "88888", "mark@gmail.com");
		Customer customer3 = new Customer(1, "Joeph", "33333", "joseph@gmail.com");
		Customer customer4 = new Customer(1, "Klay", "44444", "klay@gmail.com");
		customers.addAll(Arrays.asList(customer1, customer2, customer3, customer4));
	}

	public Customer findById(Integer id) {
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		if (customerOptional.isPresent()) {
			return customerOptional.get();
		} else {
			return null;
		}
	}

	public List<Customer> findAll() {
		return customers;

	}

	public Status deleteById(Integer id) {
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		if (customerOptional.isPresent()) {
			customers.remove(customerOptional.get());
			return Status.SUCCESS;
		} else {
			return Status.FAILURE;
		}

	}

}
