-- --  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --  -- -- 
-- -- Creation of the database for the curriculum vitae -- -- 
-- name : curriculumVitae_db
-- encoding : UTF8
-- no limit of connexion

CREATE DATABASE "keplerDB"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'French_France.1252'
       LC_CTYPE = 'French_France.1252'
       CONNECTION LIMIT = -1;
