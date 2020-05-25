package com.currency.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.currency.domain.DateSpan;

@Repository
public class DateSpanDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void addDateSpan(DateSpan datespan) {
		entityManager.persist(datespan);
	}

	@Transactional
	public List<DateSpan> getDateSpans(LocalDate startDate, LocalDate endDate, String currency) {
		TypedQuery<DateSpan> query = entityManager.createQuery(
				"SELECT d FROM DateSpan d WHERE d.currency = :currency AND d.startDate = :startDate AND d.endDate = :endDate",
				DateSpan.class);

		return query.setParameter("currency", currency).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).getResultList();

	}

}
