package com.ivosam.webservice.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ivosam.webservice.bean.Customer;
import com.ivosam.webservice.service.CustomerDetailService;

import br.com.ivosam.CustomerDetail;
import br.com.ivosam.DeleteCustomerRequest;
import br.com.ivosam.DeleteCustomerResponse;
import br.com.ivosam.GetAllCustomerDetailRequest;
import br.com.ivosam.GetAllCustomerDetailResponse;
import br.com.ivosam.GetCustomerDetailRequest;
import br.com.ivosam.GetCustomerDetailResponse;
import br.com.ivosam.exceptions.WebServiceException;

@Endpoint
public class CustomerDetailEndPoint {

	@Autowired
	CustomerDetailService service;

	@PayloadRoot(namespace = "http://ivosam.com.br", localPart = "GetCustomerDetailRequest")
	@ResponsePayload
	public GetCustomerDetailResponse processCustomerDetailRequest(@RequestPayload GetCustomerDetailRequest req) {
		Customer customer = service.findById(req.getId());
		if (customer == null) {
			throw new WebServiceException("Invalid custom id " + req.getId());
		}
		return convertToGetCustomerDetailResponse(customer);
	}

	private GetCustomerDetailResponse convertToGetCustomerDetailResponse(Customer customer) {
		GetCustomerDetailResponse resp = new GetCustomerDetailResponse();
		resp.setCustomerDetail(convertToCustomerDetail(customer));
		return resp;
	}

	private CustomerDetail convertToCustomerDetail(Customer customer) {
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(customer.getId());
		customerDetail.setName(customer.getName());
		customerDetail.setPhone(customer.getPhone());
		customerDetail.setEmail(customer.getEmail());
		return customerDetail;
	}
	
	
	@PayloadRoot(namespace = "http://ivosam.com.br", localPart = "GetAllCustomerDetailRequest")
	@ResponsePayload
	public GetAllCustomerDetailResponse processGetAllCustomerDetailRequest(@RequestPayload GetAllCustomerDetailRequest req) {
		List<Customer>  customers = service.findAll();
		return convertToGetAllCustomerDetailResponse(customers);
	}
	
	
	
	private GetAllCustomerDetailResponse convertToGetAllCustomerDetailResponse(List<Customer> customers) {
		GetAllCustomerDetailResponse resp = new GetAllCustomerDetailResponse();
		customers.stream().forEach(c -> resp.getCustomerDetail().add(convertToCustomerDetail(c)));
		return resp;
		
	}
	
	
	@PayloadRoot(namespace = "http://ivosam.com.br", localPart = "DeleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse deleteCustomerRequest(@RequestPayload DeleteCustomerRequest req) {
		DeleteCustomerResponse resp = new DeleteCustomerResponse();
		resp.setStatus(convertStatusSoap(service.deleteById(req.getId())));
		return resp;
		
	}
	
	private br.com.ivosam.Status convertStatusSoap(	com.ivosam.webservice.helper.Status statusService){
		if(statusService == com.ivosam.webservice.helper.Status.FAILURE) {
			return br.com.ivosam.Status.FAILURE;
		}else {
			return br.com.ivosam.Status.SUCCESS;
		}
		
	}

}
