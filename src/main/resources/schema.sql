create table phone_numbers
(
    id integer not null auto_increment,
    phone_number varchar(255) unique not null,
    is_valid boolean,
    local_format varchar(255) not null,
    international_format varchar(255) not null,
    country_name varchar(255) not null,
    country_code varchar(5) not null,
    country_prefix varchar(5) not null,
    registered_location varchar(255) not null,
    carrier varchar(255) not null,
    lineType varchar(255) not null,
        primary key(id)
);