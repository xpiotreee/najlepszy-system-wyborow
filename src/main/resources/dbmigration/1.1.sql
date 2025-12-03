-- apply changes
create table admin (
  id                            integer not null,
  full_name                     varchar(255) not null,
  email                         varchar(255) not null,
  password                      varchar(255) not null,
  constraint pk_admin primary key (id)
);

