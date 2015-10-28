
SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;


CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;



COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';



CREATE EXTENSION IF NOT EXISTS chkpass WITH SCHEMA public;



COMMENT ON EXTENSION chkpass IS 'data type for auto-encrypted passwords';



CREATE EXTENSION IF NOT EXISTS hstore WITH SCHEMA public;



COMMENT ON EXTENSION hstore IS 'data type for storing sets of (key, value) pairs';



CREATE EXTENSION IF NOT EXISTS moddatetime WITH SCHEMA public;



COMMENT ON EXTENSION moddatetime IS 'functions for tracking last modification time';



CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;



COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET search_path = public, pg_catalog;


CREATE TYPE tick_type AS ENUM (
    'Value',
    'Counter',
    'Toggle',
    'Geo',
    'Log'
);


SET default_tablespace = '';

SET default_with_oids = false;


CREATE TABLE sensors (
    sensor_id uuid DEFAULT uuid_generate_v1() NOT NULL,
    sensor_device_id text NOT NULL,
    sensor_name text,
    sensor_last_modified timestamp with time zone DEFAULT now() NOT NULL
);



CREATE TABLE ticks (
    tick_id uuid DEFAULT uuid_generate_v1() NOT NULL,
    tick_created_time timestamp with time zone,
    tick_received_time timestamp with time zone DEFAULT now() NOT NULL,
    tick_meta_ordinal integer,
    tick_sensor_ordinal integer,
    sensor_id uuid NOT NULL,
    tick_type tick_type NOT NULL
);



CREATE TABLE ticks_value (
    tick_value integer NOT NULL,
    CONSTRAINT check_ticks_value_type CHECK ((tick_type = 'Value'::tick_type))
)
INHERITS (ticks);



ALTER TABLE ONLY ticks_value ALTER COLUMN tick_id SET DEFAULT uuid_generate_v1();



ALTER TABLE ONLY ticks_value ALTER COLUMN tick_received_time SET DEFAULT now();



ALTER TABLE ONLY sensors
    ADD CONSTRAINT pk_sensors PRIMARY KEY (sensor_id);



ALTER TABLE ONLY ticks
    ADD CONSTRAINT pk_ticks PRIMARY KEY (tick_id);



CREATE UNIQUE INDEX idx_sensors_sensor_device_id ON sensors USING btree (sensor_device_id);



CREATE TRIGGER update_sensor_last_modified BEFORE UPDATE ON sensors FOR EACH ROW EXECUTE PROCEDURE moddatetime('sensor_last_modified');



ALTER TABLE ONLY ticks
    ADD CONSTRAINT fk_ticks_sensor_id FOREIGN KEY (sensor_id) REFERENCES sensors(sensor_id);



REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;



REVOKE ALL ON TABLE sensors FROM PUBLIC;
REVOKE ALL ON TABLE sensors FROM postgres;
GRANT ALL ON TABLE sensors TO postgres;
GRANT ALL ON TABLE sensors TO hive;



REVOKE ALL ON TABLE ticks FROM PUBLIC;
REVOKE ALL ON TABLE ticks FROM postgres;
GRANT ALL ON TABLE ticks TO postgres;
GRANT ALL ON TABLE ticks TO hive;



REVOKE ALL ON TABLE ticks_value FROM PUBLIC;
REVOKE ALL ON TABLE ticks_value FROM postgres;
GRANT ALL ON TABLE ticks_value TO postgres;
GRANT ALL ON TABLE ticks_value TO hive;



