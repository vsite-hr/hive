--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.4.5
-- Started on 2015-10-27 18:19:44 CET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 8 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

--CREATE SCHEMA public;


--ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2183 (class 0 OID 0)
-- Dependencies: 8
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

--COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 177 (class 3079 OID 11895)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2185 (class 0 OID 0)
-- Dependencies: 177
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 180 (class 3079 OID 145505)
-- Name: chkpass; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS chkpass WITH SCHEMA public;


--
-- TOC entry 2186 (class 0 OID 0)
-- Dependencies: 180
-- Name: EXTENSION chkpass; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION chkpass IS 'data type for auto-encrypted passwords';


--
-- TOC entry 178 (class 3079 OID 145526)
-- Name: hstore; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS hstore WITH SCHEMA public;


--
-- TOC entry 2187 (class 0 OID 0)
-- Dependencies: 178
-- Name: EXTENSION hstore; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION hstore IS 'data type for storing sets of (key, value) pairs';


--
-- TOC entry 181 (class 3079 OID 145503)
-- Name: moddatetime; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS moddatetime WITH SCHEMA public;


--
-- TOC entry 2188 (class 0 OID 0)
-- Dependencies: 181
-- Name: EXTENSION moddatetime; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION moddatetime IS 'functions for tracking last modification time';


--
-- TOC entry 179 (class 3079 OID 145515)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2189 (class 0 OID 0)
-- Dependencies: 179
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET search_path = public, pg_catalog;

--
-- TOC entry 625 (class 1247 OID 145674)
-- Name: tick_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tick_type AS ENUM (
    'Value',
    'Counter',
    'Toggle',
    'Geo',
    'Log'
);


ALTER TYPE tick_type OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 174 (class 1259 OID 145649)
-- Name: sensors; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sensors (
    sensor_id uuid DEFAULT uuid_generate_v1() NOT NULL,
    sensor_device_id text NOT NULL,
    sensor_name text,
    sensor_last_modified timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE sensors OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 145661)
-- Name: ticks; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ticks (
    tick_id uuid DEFAULT uuid_generate_v1() NOT NULL,
    tick_created_time timestamp with time zone,
    tick_received_time timestamp with time zone DEFAULT now() NOT NULL,
    tick_meta_ordinal integer,
    tick_sensor_ordinal integer,
    sensor_id uuid NOT NULL,
    tick_type tick_type NOT NULL
);


ALTER TABLE ticks OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 145685)
-- Name: ticks_value; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ticks_value (
    tick_value integer NOT NULL,
    CONSTRAINT check_ticks_value_type CHECK ((tick_type = 'Value'::tick_type))
)
INHERITS (ticks);


ALTER TABLE ticks_value OWNER TO postgres;

--
-- TOC entry 2059 (class 2604 OID 145688)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ticks_value ALTER COLUMN tick_id SET DEFAULT uuid_generate_v1();


--
-- TOC entry 2060 (class 2604 OID 145689)
-- Name: received_time; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ticks_value ALTER COLUMN tick_received_time SET DEFAULT now();


--
-- TOC entry 2064 (class 2606 OID 145657)
-- Name: pk_sensors; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sensors
    ADD CONSTRAINT pk_sensors PRIMARY KEY (sensor_id);


--
-- TOC entry 2066 (class 2606 OID 145667)
-- Name: pk_ticks; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ticks
    ADD CONSTRAINT pk_ticks PRIMARY KEY (tick_id);


--
-- TOC entry 2062 (class 1259 OID 145659)
-- Name: idx_sensors_device_id; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX idx_sensors_sensor_device_id ON sensors USING btree (sensor_device_id);


--
-- TOC entry 2068 (class 2620 OID 145660)
-- Name: update_sensor_last_modified; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_sensor_last_modified BEFORE UPDATE ON sensors FOR EACH ROW EXECUTE PROCEDURE moddatetime('sensor_last_modified');


--
-- TOC entry 2067 (class 2606 OID 145668)
-- Name: fk_ticks_sensor_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ticks
    ADD CONSTRAINT fk_ticks_sensor_id FOREIGN KEY (sensor_id) REFERENCES sensors(sensor_id);


--
-- TOC entry 2184 (class 0 OID 0)
-- Dependencies: 8
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-10-27 18:19:45 CET

--
-- PostgreSQL database dump complete
--

