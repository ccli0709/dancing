# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table course (
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
  constraint pk_course primary key (id))
;

create table course_date (
  id                        bigint auto_increment not null,
  course_id                 bigint,
  serial                    integer,
  course_date               datetime,
  remark                    varchar(255),
  constraint pk_course_date primary key (id))
;

create table setting (
  id                        bigint auto_increment not null,
  type                      varchar(255),
  value1                    varchar(255),
  value2                    varchar(255),
  value3                    varchar(255),
  value4                    varchar(255),
  created_time              datetime,
  updated_time              datetime,
  deleted_time              datetime,
  constraint pk_setting primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;

alter table course_date add constraint fk_course_date_course_1 foreign key (course_id) references course (id) on delete restrict on update restrict;
create index ix_course_date_course_1 on course_date (course_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table course;

drop table course_date;

drop table setting;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

