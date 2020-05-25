package com.currency.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.dao.DateSpanDAO;
import com.currency.domain.DateSpan;

@Service
public class RatesService {

	@Autowired
	DateSpanDAO dateSpanDAO;

	public String getRates(LocalDate startDate, LocalDate endDate, String currency) {

		if (endDate.isBefore(startDate)) {
			return "Date input error";
		}

		String shortCurrency = "";
		if (currency.equalsIgnoreCase("Dolar amerykañski")) {
			shortCurrency = "USD";
		} else if (currency.equalsIgnoreCase("Rubel rosyjski")) {
			shortCurrency = "RUB";
		} else if (currency.equalsIgnoreCase("Euro")) {
			shortCurrency = "EUR";
		} else {
			return "Currency input error";
		}

		try {
			List<DateSpan> dateSpans = dateSpanDAO.getDateSpans(startDate, endDate, shortCurrency);

			if (dateSpans.isEmpty()) {
				return getUrlRates(startDate, endDate, shortCurrency);
			} else {
				return dateSpans.get(0).getCurrency() + " " + dateSpans.get(0).getMinAskDate() + " "
						+ dateSpans.get(0).getMinAsk() + " " + dateSpans.get(0).getMaxBidDate() + " "
						+ dateSpans.get(0).getMaxBid();
			}
		} catch (MalformedURLException e) {
			return "URL error";
		} catch (IOException e) {
			return "URL Stream error";
		}

	}

	private String getUrlRates(LocalDate startDate, LocalDate endDate, String currency)
			throws MalformedURLException, IOException {
		String url = "https://api.nbp.pl/api/exchangerates/rates/c/" + currency + "/" + startDate.toString() + "/"
				+ endDate.toString();
		String charset = java.nio.charset.StandardCharsets.UTF_8.name();
		String param1 = "format=json";
		String params = String.format("param=%s", URLEncoder.encode(param1, charset));

		URLConnection connection = new URL(url + "?" + params).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = connection.getInputStream();
		Scanner scanner = new Scanner(response);
		String responseBody = scanner.useDelimiter("\\A").next();
		scanner.close();
		String keyword = "effectiveDate";

		float minAsk = (float) 1000000.0000;
		LocalDate minAskDate = LocalDate.parse("0001-01-01");
		float maxBid = (float) 0.0000;
		LocalDate maxBidDate = LocalDate.parse("0001-01-01");

		int index = responseBody.indexOf(keyword);
		while (index >= 0) {
			LocalDate currentDate = LocalDate.parse(responseBody.substring(index + 16, index + 26));
			float currentAsk = Float.parseFloat(responseBody.substring(index + 48, index + 53));
			float currentBid = Float.parseFloat(responseBody.substring(index + 35, index + 40));

			if (currentAsk < minAsk) {
				minAsk = currentAsk;
				minAskDate = currentDate;
			}

			if (currentBid > maxBid) {
				maxBid = currentBid;
				maxBidDate = currentDate;
			}

			index = responseBody.indexOf(keyword, index + 1);
		}

		DateSpan datespan = new DateSpan();
		datespan.setCurrency(currency);
		datespan.setStartDate(startDate);
		datespan.setEndDate(endDate);
		datespan.setMinAsk(minAsk);
		datespan.setMinAskDate(minAskDate);
		datespan.setMaxBid(maxBid);
		datespan.setMaxBidDate(maxBidDate);
		dateSpanDAO.addDateSpan(datespan);

		return currency + " " + minAskDate.toString() + " " + minAsk + " " + maxBidDate.toString() + " " + maxBid;
	}
}
