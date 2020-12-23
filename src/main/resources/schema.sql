create table phone_numbers
(
    id integer not null auto_increment,
    phone_number varchar(255) unique not null,
    is_valid boolean,
    country_name varchar(255) not null,
    country_code varchar(5) not null,
    country_prefix varchar(5) not null,
    primary key(id)
);