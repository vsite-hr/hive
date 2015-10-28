SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET search_path = public, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = false;

ALTER TABLE sensors DISABLE TRIGGER ALL;
INSERT INTO sensors (sensor_id, sensor_device_id, sensor_name) VALUES ('3f07ffec-7cd2-11e5-8be1-64315096cc59', 'first', 'Test sensor #1');
INSERT INTO sensors (sensor_id, sensor_device_id, sensor_name) VALUES ('cc605906-7cd3-11e5-8be1-64315096cc59', 'second', 'Test sensor #2');
INSERT INTO sensors (sensor_id, sensor_device_id, sensor_name) VALUES ('d2a1a22a-7cd3-11e5-8be1-64315096cc59', 'third', 'Test sensor #3');
ALTER TABLE sensors ENABLE TRIGGER ALL;
