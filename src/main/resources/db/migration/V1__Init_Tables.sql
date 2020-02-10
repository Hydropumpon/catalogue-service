create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

create sequence category_id_seq start with 1 increment by 1 cache 5;

alter sequence category_id_seq owner to postgres;

create sequence item_id_seq start with 1 increment by 1 cache 5;

alter sequence item_id_seq owner to postgres;

create sequence manufacturer_id_seq start with 1 increment by 1 cache 5;

alter sequence manufacturer_id_seq owner to postgres;

create sequence item_category_id_seq start with 1 increment by 1 cache 5;

alter sequence item_category_id_seq owner to postgres;

create table if not exists category
(
    id integer not null
        constraint category_pkey
            primary key,
    name varchar(255) not null
);

alter table category owner to postgres;

create table if not exists manufacturer
(
    id integer not null
        constraint manufacturer_pkey
            primary key,
    building_number integer,
    city varchar(255) not null ,
    country varchar(255) not null,
    street varchar(255) not null,
    zip varchar(255) not null,
    email varchar(255) not null,
    foundation_year varchar(255) not null,
    name varchar(255) not null
);

alter table manufacturer owner to postgres;

create table if not exists item
(
    id integer not null
        constraint item_pkey
            primary key,
    description varchar(255) not null,
    name varchar(255) not null,
    price numeric(19,2) not null ,
    manufacturer_id integer not null
        constraint fk_manufacturer_id
            references manufacturer
);

alter table item owner to postgres;

create table if not exists item_category
(
    id integer not null
        constraint item_category_pkey
            primary key,
    category_id integer
        constraint fk_category_id
            references category,
    item_id integer
        constraint fk_item_id
            references item
);

alter table item_category owner to postgres;
