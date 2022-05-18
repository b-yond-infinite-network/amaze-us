CREATE TABLE public."user" (
    id integer NOT NULL,
    full_name text,
    email text NOT NULL,
    hashed_password text NOT NULL,
    is_manager boolean DEFAULT true
);

ALTER TABLE public."user" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."User_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000
    CACHE 1
);

CREATE TABLE public.bus (
    id integer NOT NULL,
    model text NOT NULL,
    make text,
    capacity integer DEFAULT 50 NOT NULL,
	CONSTRAINT "Bus_pkey" PRIMARY KEY (id)
);

ALTER TABLE public.bus ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Bus_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.driver (
    id integer NOT NULL,
    first_name text,
    last_name text,
    ssn text NOT NULL UNIQUE,
    email text,
	CONSTRAINT drivers_pkey PRIMARY KEY (id),
    CONSTRAINT email_format CHECK ((email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'::text)),
    CONSTRAINT ssn_9digit CHECK ((ssn ~ '^\d{9}$'::text))
);

ALTER TABLE public.driver ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.drivers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.schedule (
    bus_id integer NOT NULL,
    driver_id integer NOT NULL,
    id integer NOT NULL,
    date date NOT NULL,
    start time without time zone NOT NULL,
    "end" time without time zone NOT NULL,
	CONSTRAINT "Schedule_pkey" PRIMARY KEY (id),
	CONSTRAINT "FK_bus" FOREIGN KEY (bus_id) REFERENCES public.bus(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID,
	CONSTRAINT "FK_driver" FOREIGN KEY (driver_id) REFERENCES public.driver(id) ON UPDATE SET NULL ON DELETE SET NULL
);
ALTER TABLE public.schedule ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Schedule_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);