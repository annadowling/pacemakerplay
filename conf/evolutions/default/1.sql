# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table activity (
  id                            bigserial not null,
  user_id                       bigint not null,
  location                      varchar(255),
  distance                      float,
  date                          timestamp,
  duration                      float,
  activity_type                 varchar(255),
  constraint pk_activity primary key (id)
);

create table friends (
  id                            bigserial not null,
  user_id                       bigint,
  friend_id                     bigint,
  added                         boolean,
  constraint pk_friends primary key (id)
);

create table location (
  id                            bigserial not null,
  activity_id                   bigint not null,
  latitude                      float,
  longitude                     float,
  constraint pk_location primary key (id)
);

create table my_user (
  id                            bigserial not null,
  firstname                     varchar(255),
  lastname                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  constraint pk_my_user primary key (id)
);

alter table activity add constraint fk_activity_user_id foreign key (user_id) references my_user (id) on delete restrict on update restrict;
create index ix_activity_user_id on activity (user_id);

alter table location add constraint fk_location_activity_id foreign key (activity_id) references activity (id) on delete restrict on update restrict;
create index ix_location_activity_id on location (activity_id);


# --- !Downs

alter table if exists activity drop constraint if exists fk_activity_user_id;
drop index if exists ix_activity_user_id;

alter table if exists location drop constraint if exists fk_location_activity_id;
drop index if exists ix_location_activity_id;

drop table if exists activity cascade;

drop table if exists friends cascade;

drop table if exists location cascade;

drop table if exists my_user cascade;

