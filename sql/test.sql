create database test;
create table user (
  id       varchar(64) not null,
  password varchar(255) default null,
  username varchar(255) default null,
  primary key (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  default charset = utf8;

insert into test.user (id, password, username)
values ('1', '123456', 'hh');