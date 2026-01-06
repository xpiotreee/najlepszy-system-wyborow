-- apply changes
create table admin (
  id                            integer not null,
  full_name                     varchar(255) not null,
  email                         varchar(255) not null,
  password                      varchar(255) not null,
  constraint pk_admin primary key (id)
);

create table candidate (
  id                            integer not null,
  name                          varchar(255) not null,
  description                   TEXT,
  photo_url                     varchar(255),
  external_link                 varchar(255),
  constraint pk_candidate primary key (id)
);

create table candidate_election (
  candidate_id                  integer not null,
  election_id                   integer not null,
  constraint pk_candidate_election primary key (candidate_id,election_id),
  foreign key (candidate_id) references candidate (id) on delete restrict on update restrict,
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

create table vote (
  id                            integer not null,
  user_id                       integer not null,
  election_id                   integer not null,
  candidate_id                  integer not null,
  constraint uq_vote_user_id_election_id unique (user_id,election_id),
  constraint pk_vote primary key (id),
  foreign key (user_id) references user (id) on delete restrict on update restrict,
  foreign key (election_id) references election (id) on delete restrict on update restrict,
  foreign key (candidate_id) references candidate (id) on delete restrict on update restrict
);

