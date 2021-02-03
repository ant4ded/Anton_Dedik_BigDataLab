--
-- PostgreSQL database dump
--

-- Dumped from database version 10.15
-- Dumped by pg_dump version 10.15

-- Started on 2021-02-03 21:25:37

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
-- TOC entry 2840 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 198 (class 1259 OID 22812)
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
-- TOC entry 197 (class 1259 OID 22810)
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
-- TOC entry 199 (class 1259 OID 22820)
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
-- TOC entry 200 (class 1259 OID 22825)
-- Name: outcome_object; Type: TABLE; Schema: prod; Owner: postgres
--

CREATE TABLE prod.outcome_object (
    id character varying(255) NOT NULL,
    name character varying(255)
);


ALTER TABLE prod.outcome_object OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 22833)
-- Name: outcome_status; Type: TABLE; Schema: prod; Owner: postgres
--

CREATE TABLE prod.outcome_status (
    id bigint NOT NULL,
    category character varying(255),
    date character varying(255)
);


ALTER TABLE prod.outcome_status OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 22841)
-- Name: stop_and_search; Type: TABLE; Schema: prod; Owner: postgres
--

CREATE TABLE prod.stop_and_search (
    id uuid NOT NULL,
    age_range character varying(255),
    datetime timestamp without time zone,
    gender character varying(255),
    involved_person boolean,
    legislation character varying(255),
    object_of_search character varying(255),
    officer_defined_ethnicity character varying(255),
    operation character varying(255),
    operation_name character varying(255),
    outcome_linked_to_object_of_search boolean,
    removal_of_more_than_outer_clothing boolean,
    self_defined_ethnicity character varying(255),
    type character varying(255),
    location_id bigint,
    outcome_object_id character varying(255)
);


ALTER TABLE prod.stop_and_search OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 21144)
-- Name: stop_and_search_sequence; Type: SEQUENCE; Schema: prod; Owner: postgres
--

CREATE SEQUENCE prod.stop_and_search_sequence
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE prod.stop_and_search_sequence OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 22849)
-- Name: street; Type: TABLE; Schema: prod; Owner: postgres
--

CREATE TABLE prod.street (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE prod.street OWNER TO postgres;

--
-- TOC entry 2697 (class 2606 OID 22819)
-- Name: crime crime_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.crime
    ADD CONSTRAINT crime_pkey PRIMARY KEY (id);


--
-- TOC entry 2699 (class 2606 OID 22824)
-- Name: location location_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- TOC entry 2701 (class 2606 OID 22832)
-- Name: outcome_object outcome_object_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.outcome_object
    ADD CONSTRAINT outcome_object_pkey PRIMARY KEY (id);


--
-- TOC entry 2703 (class 2606 OID 22840)
-- Name: outcome_status outcome_status_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.outcome_status
    ADD CONSTRAINT outcome_status_pkey PRIMARY KEY (id);


--
-- TOC entry 2705 (class 2606 OID 22848)
-- Name: stop_and_search stop_and_search_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.stop_and_search
    ADD CONSTRAINT stop_and_search_pkey PRIMARY KEY (id);


--
-- TOC entry 2707 (class 2606 OID 22853)
-- Name: street street_pkey; Type: CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.street
    ADD CONSTRAINT street_pkey PRIMARY KEY (id);


--
-- TOC entry 2708 (class 2606 OID 22854)
-- Name: crime fk3xkq94wviwgirfepd8dxrwum6; Type: FK CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.crime
    ADD CONSTRAINT fk3xkq94wviwgirfepd8dxrwum6 FOREIGN KEY (location_id) REFERENCES prod.location(id);


--
-- TOC entry 2710 (class 2606 OID 22864)
-- Name: location fk57sm5p4awhw8awu1rkmk5lt5q; Type: FK CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.location
    ADD CONSTRAINT fk57sm5p4awhw8awu1rkmk5lt5q FOREIGN KEY (street_id) REFERENCES prod.street(id);


--
-- TOC entry 2712 (class 2606 OID 22874)
-- Name: stop_and_search fkb7u7sp0r25lyu3n2i1iqiv8k2; Type: FK CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.stop_and_search
    ADD CONSTRAINT fkb7u7sp0r25lyu3n2i1iqiv8k2 FOREIGN KEY (outcome_object_id) REFERENCES prod.outcome_object(id);


--
-- TOC entry 2709 (class 2606 OID 22859)
-- Name: crime fkhvyimqb89v8x3678c9556yjxf; Type: FK CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.crime
    ADD CONSTRAINT fkhvyimqb89v8x3678c9556yjxf FOREIGN KEY (outcome_status_id) REFERENCES prod.outcome_status(id);


--
-- TOC entry 2711 (class 2606 OID 22869)
-- Name: stop_and_search fktdad06k2j0729mo1ykm0y99gx; Type: FK CONSTRAINT; Schema: prod; Owner: postgres
--

ALTER TABLE ONLY prod.stop_and_search
    ADD CONSTRAINT fktdad06k2j0729mo1ykm0y99gx FOREIGN KEY (location_id) REFERENCES prod.location(id);


-- Completed on 2021-02-03 21:25:37

--
-- PostgreSQL database dump complete
--

