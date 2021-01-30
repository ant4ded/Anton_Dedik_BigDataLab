--
-- PostgreSQL database dump
--

-- Dumped from database version 10.15
-- Dumped by pg_dump version 10.15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE uk_police;
--
-- Name: uk_police; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE uk_police WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


\connect uk_police

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: prod; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA prod;


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: crime; Type: TABLE; Schema: prod; Owner: -
--

CREATE TABLE prod.crime (
    id bigint NOT NULL,
    category character varying(255),
    context character varying(255),
    date character varying(255),
    outcome_status_category character varying(255),
    outcome_status_date character varying(255),
    persistent_id character varying(255),
    street_id bigint
);


--
-- Name: location; Type: TABLE; Schema: prod; Owner: -
--

CREATE TABLE prod.location (
    street_id bigint NOT NULL,
    latitude double precision,
    location_subtype character varying(255),
    location_type character varying(255),
    longitude double precision,
    street_name character varying(255)
);


--
-- Data for Name: crime; Type: TABLE DATA; Schema: prod; Owner: -
--

COPY prod.crime (id, category, context, date, outcome_status_category, outcome_status_date, persistent_id, street_id) FROM stdin;
\.


--
-- Data for Name: location; Type: TABLE DATA; Schema: prod; Owner: -
--

COPY prod.location (street_id, latitude, location_subtype, location_type, longitude, street_name) FROM stdin;
\.


--
-- Name: crime crime_pkey; Type: CONSTRAINT; Schema: prod; Owner: -
--

ALTER TABLE ONLY prod.crime
    ADD CONSTRAINT crime_pkey PRIMARY KEY (id);


--
-- Name: location location_pkey; Type: CONSTRAINT; Schema: prod; Owner: -
--

ALTER TABLE ONLY prod.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (street_id);


--
-- Name: crime fk41g9axaf8h6x34f44a6w5v0jf; Type: FK CONSTRAINT; Schema: prod; Owner: -
--

ALTER TABLE ONLY prod.crime
    ADD CONSTRAINT fk41g9axaf8h6x34f44a6w5v0jf FOREIGN KEY (street_id) REFERENCES prod.location(street_id);


--
-- PostgreSQL database dump complete
--

