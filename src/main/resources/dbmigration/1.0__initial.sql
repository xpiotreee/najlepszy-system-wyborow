-- apply changes
create table candidate (
  id                            integer not null,
  name                          varchar(255) not null,
  description                   TEXT,
  photo_url                     varchar(255),
  external_link                 varchar(255),
  election_id                   integer not null,
  constraint pk_candidate primary key (id),
  foreign key (election_id) references election (id) on delete restrict on update restrict
);

create table election (
  id                            integer not null,
  title                         varchar(255) not null,
  description                   TEXT,
  start_date                    timestamp not null,
  end_date                      timestamp not null,
  result_visibility             varchar(11) not null,
  constraint ck_election_result_visibility check ( result_visibility in ('ALWAYS','AFTER_VOTE','AFTER_CLOSE')),
  constraint pk_election primary key (id)
);

create table user (
  id                            integer not null,
  full_name                     varchar(255) not null,
  pesel                         varchar(11) not null,
  email                         varchar(255) not null,
  password                      varchar(255) not null,
  constraint pk_user primary key (id)
);

