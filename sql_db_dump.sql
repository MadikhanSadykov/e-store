-- Table: public.user

DROP TABLE IF EXISTS public."user" CASCADE;

CREATE TABLE IF NOT EXISTS public."user"
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(60) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(60) COLLATE pg_catalog."default",
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(20) COLLATE pg_catalog."default",
    address text COLLATE pg_catalog."default",
    password character varying(512) COLLATE pg_catalog."default" NOT NULL,
    is_admin boolean NOT NULL DEFAULT false,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT user_email_key UNIQUE (email),
    CONSTRAINT user_phone_number_key UNIQUE (phone_number)
)

TABLESPACE pg_default;

-------------------------------------------------------------------------------

-- Table: public.language

DROP TABLE IF EXISTS public.language CASCADE;

CREATE TABLE IF NOT EXISTS public.language
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(60) COLLATE pg_catalog."default" NOT NULL,
    short_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT language_pkey PRIMARY KEY (id),
    CONSTRAINT name UNIQUE (name),
    CONSTRAINT short_name UNIQUE (short_name)
)

TABLESPACE pg_default;

-------------------------------------------------------------------------------

-- Table: public.status

DROP TABLE IF EXISTS public.status CASCADE;

CREATE TABLE IF NOT EXISTS public.status
(
    id integer NOT NULL,
    name character varying(60) COLLATE pg_catalog."default" NOT NULL,
    id_language integer NOT NULL,
    CONSTRAINT status_pkey PRIMARY KEY (id, id_language),
    CONSTRAINT status_id_id_language_key UNIQUE (id, id_language),
    CONSTRAINT status_id_language_fkey1 FOREIGN KEY (id_language)
        REFERENCES public.language (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)

TABLESPACE pg_default;

-----------------------------------------------------------------------------------

-- Table: public.category

DROP TABLE IF EXISTS public.category CASCADE;

CREATE TABLE IF NOT EXISTS public.category
(
    id bigint NOT NULL,
    name character varying(60) COLLATE pg_catalog."default" NOT NULL,
    url character varying(60) COLLATE pg_catalog."default" NOT NULL,
    product_count integer NOT NULL DEFAULT 0,
    id_language integer NOT NULL,
    CONSTRAINT category_pkey PRIMARY KEY (id_language, id),
    CONSTRAINT category_id_id_language_key UNIQUE (id, id_language),
    CONSTRAINT category_name_key UNIQUE (name),
    CONSTRAINT category_id_language_fkey FOREIGN KEY (id_language)
        REFERENCES public.language (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
)

TABLESPACE pg_default;

----------------------------------------------------------------------------------------

-- Table: public.producer

DROP TABLE IF EXISTS public.producer CASCADE;

CREATE TABLE IF NOT EXISTS public.producer
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(60) COLLATE pg_catalog."default" NOT NULL,
    product_count integer NOT NULL DEFAULT 0,
    CONSTRAINT id_pk1 PRIMARY KEY (id),
    CONSTRAINT name_u1 UNIQUE (name)
)

TABLESPACE pg_default;

---------------------------------------------------------------------------------------

-- Table: public.product

DROP TABLE IF EXISTS public.product CASCADE;

CREATE TABLE IF NOT EXISTS public.product
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    image_link character varying(255) COLLATE pg_catalog."default" NOT NULL,
    price numeric(8,2) NOT NULL,
    id_category bigint NOT NULL,
    id_producer bigint NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id),

    CONSTRAINT product_id_producer_fkey FOREIGN KEY (id_producer)
        REFERENCES public.producer (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
)

TABLESPACE pg_default;

------------------------------------------------------------------------------------

-- Table: public.cart

DROP TABLE IF EXISTS public.cart CASCADE;

CREATE TABLE IF NOT EXISTS public.cart
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    product_count integer,
    id_product bigint,
    id_user bigint,
    CONSTRAINT cart_pkey1 PRIMARY KEY (id),
    CONSTRAINT cart_id_product_fkey FOREIGN KEY (id_product)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT cart_id_user_fkey1 FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)

TABLESPACE pg_default;

---------------------------------------------------------------------------------------------

-- Table: public.order

DROP TABLE IF EXISTS public."order" CASCADE;

CREATE TABLE IF NOT EXISTS public."order"
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    total_cost numeric(8,2) NOT NULL,
    created timestamp without time zone NOT NULL DEFAULT now(),
    finished timestamp without time zone,
    id_user bigint NOT NULL,
    id_status integer NOT NULL,
    CONSTRAINT order_pkey PRIMARY KEY (id),
    CONSTRAINT order_id_status_id_status_fkey FOREIGN KEY (id_status, id_status)
        REFERENCES public.status (id, id_language) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID,
    CONSTRAINT order_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
)

TABLESPACE pg_default;

---------------------------------------------------------------------------------------------

-- Table: public.order_item

DROP TABLE IF EXISTS public.order_item CASCADE;

CREATE TABLE IF NOT EXISTS public.order_item
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    product_count integer NOT NULL,
    cost numeric(8,2) NOT NULL,
    id_order bigint NOT NULL,
    id_product bigint NOT NULL,
    CONSTRAINT order_item_pkey PRIMARY KEY (id),
    CONSTRAINT order_item_id_order_fkey FOREIGN KEY (id_order)
        REFERENCES public."order" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT order_item_id_product_fkey FOREIGN KEY (id_product)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
)

TABLESPACE pg_default;

--------------------------------------------------------------------------------------






