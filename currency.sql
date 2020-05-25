//DROP TABLE public.date_span;
CREATE SEQUENCE public.date_span_id_seq;
CREATE TABLE public.date_span (
    date_span_id BIGINT NOT NULL DEFAULT nextval('date_span_id_seq') PRIMARY KEY,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	currency VARCHAR NOT NULL,
	min_ask REAL NOT NULL,
	max_bid REAL NOT NULL,
	min_ask_date DATE NOT NULL,
	max_bid_date DATE NOT NULL
);