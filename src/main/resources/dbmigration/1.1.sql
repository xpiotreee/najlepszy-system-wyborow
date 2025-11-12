-- apply changes
create table election (
  id                            integer not null,
  title                         varchar(255),
  description                   varchar(255),
  from                          timestamp,
  to                            timestamp,
  result_visibility             varchar(11),
  constraint ck_election_result_visibility check ( result_visibility in ('ALWAYS','AFTER_VOTE','AFTER_CLOSE')),
  constraint pk_election primary key (id)
);

