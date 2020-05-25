package com.currency.ws;

import javax.jws.WebService;

import java.time.LocalDate;

import javax.jws.WebMethod;

@WebService
public interface CurrencyServiceInterface {
	@WebMethod(operationName = "getRates")
	public String getRates(LocalDate startDate, LocalDate endDate, String currency);
}
