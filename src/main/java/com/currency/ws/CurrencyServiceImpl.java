package com.currency.ws;

import java.time.LocalDate;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currency.service.RatesService;

@Component
@WebService(endpointInterface = "com.currency.ws.CurrencyServiceInterface", serviceName = "CurrencyService")
public class CurrencyServiceImpl implements CurrencyServiceInterface {
	@Autowired
	RatesService ratesService;

	public String getRates(LocalDate startDate, LocalDate endDate, String currency) {
		return ratesService.getRates(startDate, endDate, currency);
	}
}
