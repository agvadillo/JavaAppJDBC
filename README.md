# JavaAppJDBC

This netbean project is an example for connect a java app with mysql by JDBC driver.

## Prerequisites

Database schema required

```
create schema contactapp;

use contactapp;

create table contactapp.contact (
    `pk_id_contact` int auto_increment,
    `name` varchar (50),
    `lastname` varchar (50),
    `address` varchar(100),
    primary key (`pk_id_contact`)
);

create table contactapp.phone (
    `pk_id_phone` int auto_increment,
    `number` int (15),
    `fk_id_contact` int not null,
    primary key (`pk_id_phone`),
    foreign key (fk_id_contact) references contactapp.contact(pk_id_contact) on update cascade
);
```

## Built With

* [NetBeans 8.2](https://netbeans.org) - Development environment used