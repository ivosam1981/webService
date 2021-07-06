package br.com.ivosam.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://ivosam.com.br}001_Customer_Not_Found")
public class WebServiceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	
	public WebServiceException (String msg) {
		super(msg);		
	}

}
