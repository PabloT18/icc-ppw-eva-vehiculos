create table vehicles (
   id      bigint primary key,
   brand   varchar(100) not null,
   model   varchar(100) not null,
   price   double not null,
   stock   int not null,
   deleted char(1) not null
);