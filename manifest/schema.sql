CREATE ROLE store_service WITH
    NOSUPERUSER
    NOCREATEDB
    NOCREATEROLE
    INHERIT
    LOGIN
    NOREPLICATION
    NOBYPASSRLS
    CONNECTION LIMIT -1
    PASSWORD 'tGXivQhfBP';


CREATE SCHEMA store_service AUTHORIZATION store_service;


-- Permissions

GRANT ALL ON SCHEMA store_service TO store_service;

CREATE TABLE store_service.message (
	uuid varchar(100) NOT NULL,
	CONSTRAINT message_pkey PRIMARY KEY (uuid)
);

ALTER TABLE store_service.message OWNER TO store_service;
GRANT ALL ON TABLE store_service.message TO store_service;


CREATE TABLE store_service.menu (
   id bigserial NOT NULL,
   name varchar(200) NOT NULL,
   description varchar(2000) NOT NULL,
   weight int8 NOT NULL,
   CONSTRAINT menu_pkey PRIMARY KEY (id)
);

INSERT INTO store_service.menu(name, description, weight)
VALUES
    ('Дорадо на мангале',  'Нежнейшая и вкусная рыбка «Дорадо», приготовленная на мангале.', '375'),
    ('Люля-кебаб из телятины', 'Национальное блюдо Азербайджанской кухни. По внешнему виду напоминает шашлык, но готовится не из цельных кусочков, а из фарша телятины, где мелко нарубленное мясо тщательно смешивают со специями и травами.', '245'),
    ('Люля-кебаб из ягненка',   'Национальное блюдо Азербайджанской кухни. По внешнему виду напоминает шашлык, но готовится не из цельных кусочков, а из фарша ягненка, где мелко нарубленное мясо тщательно смешивают со специями и травами.', '235'),
    ('Овощи на мангале',   'Баклажан, помидор, шампиньоны, цукини, сладкий перец.', '172'),
    ('Шашлык рибай из молочного ягнёнка',   'Молочный ягненок на гриле.', '295');

ALTER TABLE store_service.menu OWNER TO store_service;
GRANT ALL ON TABLE store_service.menu TO store_service;

CREATE TABLE store_service.storage (
      menu_id int8 NOT NULL,
      price numeric(16,2) NOT NULL,
      count int8 NOT NULL,
      CONSTRAINT storage_pkey PRIMARY KEY (menu_id)
);

INSERT INTO store_service.storage(menu_id, price, count)
VALUES
    (1,  '39', '10'),
    (2,  '26', '10'),
    (3,  '26', '10'),
    (4,  '15', '10'),
    (5,  '29', '10');


ALTER TABLE IF EXISTS store_service.storage
    ADD CONSTRAINT fk_menu_id FOREIGN KEY (menu_id) REFERENCES store_service.menu (id);

ALTER TABLE store_service.storage OWNER TO store_service;
GRANT ALL ON TABLE store_service.storage TO store_service;

CREATE TABLE store_service.order (
   id bigserial NOT NULL,
   menu_id int8 NOT NULL,
   price numeric(16,2) NOT NULL,
   count int8 NOT NULL,
   order_id int8 NOT NULL,
   CONSTRAINT order_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS store_service.order
    ADD CONSTRAINT fk_menu_id FOREIGN KEY (menu_id) REFERENCES store_service.menu(id);

ALTER TABLE store_service.order OWNER TO store_service;
GRANT ALL ON TABLE store_service.order TO store_service;
