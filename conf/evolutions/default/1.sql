# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table anniversary (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  user_id                   bigint,
  date                      datetime,
  constraint pk_anniversary primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;

create table week_class (
  id                        bigint auto_increment not null,
  dance_division            varchar(255),
  choreography              varchar(255),
  day_of_week               varchar(255),
  period                    varchar(255),
  begin_time                varchar(255),
  end_time                  varchar(255),
  begin_date                datetime,
  end_date                  datetime,
  level                     varchar(255),
  quantity                  bigint,
  location                  varchar(255),
  constraint pk_week_class primary key (id))
;

alter table anniversary add constraint fk_anniversary_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_anniversary_user_1 on anniversary (user_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table anniversary;

drop table user;

drop table week_class;

SET FOREIGN_KEY_CHECKS=1;

