package com.currency.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "date_span")
public class DateSpan {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(name = "currency", nullable = false)
	private String currency;

	@Column(name = "min_ask", nullable = false)
	private float minAsk;

	@Column(name = "max_bid", nullable = false)
	private float maxBid;

	@Column(name = "min_ask_date", nullable = false)
	private LocalDate minAskDate;

	@Column(name = "max_bid_date", nullable = false)
	private LocalDate maxBidDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public float getMinAsk() {
		return minAsk;
	}

	public void setMinAsk(float minAsk) {
		this.minAsk = minAsk;
	}

	public float getMaxBid() {
		return maxBid;
	}

	public void setMaxBid(float maxBid) {
		this.maxBid = maxBid;
	}

	public LocalDate getMinAskDate() {
		return minAskDate;
	}

	public void setMinAskDate(LocalDate minAskDate) {
		this.minAskDate = minAskDate;
	}

	public LocalDate getMaxBidDate() {
		return maxBidDate;
	}

	public void setMaxBidDate(LocalDate maxBidDate) {
		this.maxBidDate = maxBidDate;
	}
}