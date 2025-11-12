-- apply changes
create table user (
  id                            integer not null,
  full_name                     varchar(255),
  pesel                         varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  constraint pk_user primary key (id)
);

