--
-- PostgreSQL database dump
--

-- Dumped from database version 10.15
-- Dumped by pg_dump version 10.15

-- Started on 2021-01-30 19:56:15

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
-- TOC entry 2821 (class 1262 OID 16393)
-- Name: uk_police; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE uk_police WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE uk_police OWNER TO postgres;

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
-- TOC entry 4 (class 2615 OID 16394)
-- Name: prod; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA prod;


ALTER SCHEMA prod OWNER TO postgres;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2822 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 18190)
-- Name: crime; Type: TABLE; Schema: prod; Owner: postgres
--

CREATE TABLE prod.crime (
    id bigint NOT NULL,
    category character varying(255),
    context character varying(255),
    location_subtype character varying(255),
    location_type character varying(255),
    month character varying(255),
    persistent_id character varying(255),
    location_id bigint,
    outcome_status_id bigint
);


ALTER TABLE prod.crime OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 18188)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: prod; Owner: postgres
--

CREATE SEQUENCE prod.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE prod.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 18198)
-- Name: location; Type: TABLE; Schema: prod; Owner: postgres
--

CREATE TABLE prod.location (
    id bigint NOT NULL,
    latitude double precision,
    longitude double precision,
    street_id bigint NOT NULL
);


ALTER TABLE prod.location OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 18203)
-- Name: outcome_status; Type: TABLE; Schema: prod; Owner: postgres
--

CREATE TABLE prod.outcome_status (
    id bigint NOT NULL,
    category character varying(255),
    date character varying(255)
);


ALTER TABLE prod.outcome_status OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 18211)
-- Name: street; Type: TABLE; Schema: prod; Owner: postgres
--

CREATE TABLE prod.street (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE prod.street OWNER TO postgres;

--
-- TOC entry 2685 (class 2606 OID 18197)
-- Name: crime crime_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.crime
    ADD CONSTRAINT crime_pkey PRIMARY KEY (id);


--
-- TOC entry 2687 (class 2606 OID 18202)
-- Name: location location_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- TOC entry 2689 (class 2606 OID 18210)
-- Name: outcome_status outcome_status_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.outcome_status
    ADD CONSTRAINT outcome_status_pkey PRIMARY KEY (id);


--
-- TOC entry 2691 (class 2606 OID 18215)
-- Name: street street_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.street
    ADD CONSTRAINT street_pkey PRIMARY KEY (id);


--
-- TOC entry 2692 (class 2606 OID 18216)
-- Name: crime fk3xkq94wviwgirfepd8dxrwum6; Type: FK CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.crime
    ADD CONSTRAINT fk3xkq94wviwgirfepd8dxrwum6 FOREIGN KEY (location_id) REFERENCES prod.location(id);


--
-- TOC entry 2694 (class 2606 OID 18226)
-- Name: location fk57sm5p4awhw8awu1rkmk5lt5q; Type: FK CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.location
    ADD CONSTRAINT fk57sm5p4awhw8awu1rkmk5lt5q FOREIGN KEY (street_id) REFERENCES prod.street(id);


--
-- TOC entry 2693 (class 2606 OID 18221)
-- Name: crime fkhvyimqb89v8x3678c9556yjxf; Type: FK CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.crime
    ADD CONSTRAINT fkhvyimqb89v8x3678c9556yjxf FOREIGN KEY (outcome_status_id) REFERENCES prod.outcome_status(id);


-- Completed on 2021-01-30 19:56:15

--
-- PostgreSQL database dump complete
--

