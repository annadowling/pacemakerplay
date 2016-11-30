# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table activity (
  id                            bigint not null,
  user_id                       bigint not null,
  location                      varchar(255),
  distance                      double,
  date                          timestamp,
  duration                      double,
  activity_type                 varchar(255),
  constraint pk_activity primary key (id)
);
create sequence activity_seq;

create table friends (
  id                            bigint not null,
  user_id                       bigint,
  friend_id                     bigint,
  added                         boolean,
  constraint pk_friends primary key (id)
);
create sequence friends_seq;

create table location (
  id                            bigint not null,
  activity_id                   bigint not null,
  latitude                      float,
  longitude                     float,
  constraint pk_location primary key (id)
);
create sequence location_seq;

create table my_user (
  id                            bigint not null,
  firstname                     varchar(255),
  lastname                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  constraint pk_my_user primary key (id)
);
create sequence my_user_seq;

alter table activity add constraint fk_activity_user_id foreign key (user_id) references my_user (id) on delete restrict on update restrict;
create index ix_activity_user_id on activity (user_id);

alter table location add constraint fk_location_activity_id foreign key (activity_id) references activity (id) on delete restrict on update restrict;
create index ix_location_activity_id on location (activity_id);


# --- !Downs

alter table activity drop constraint if exists fk_activity_user_id;
drop index if exists ix_activity_user_id;

alter table location drop constraint if exists fk_location_activity_id;
drop index if exists ix_location_activity_id;

drop table if exists activity;
drop sequence if exists activity_seq;

drop table if exists friends;
drop sequence if exists friends_seq;

drop table if exists location;
drop sequence if exists location_seq;

drop table if exists my_user;
drop sequence if exists my_user_seq;

