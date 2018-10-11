/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/10/11 14:38:03                          */
/*==============================================================*/


drop table if exists blog_comment;

drop table if exists blog_label;

drop table if exists blog_label_association;

drop table if exists blog_topic;

drop table if exists blog_user;

drop table if exists blog_user_category;

drop table if exists blog_user_outreach;

/*==============================================================*/
/* Table: blog_comment                                          */
/*==============================================================*/
create table blog_comment
(
   id                   bigint not null auto_increment,
   name                 varchar(50),
   email                varchar(50),
   association_id       bigint,
   association_type     varchar(20),
   content              varchar(255),
   created_at           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: blog_label                                            */
/*==============================================================*/
create table blog_label
(
   id                   bigint not null auto_increment,
   name                 varchar(50),
   created_at           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: blog_label_association                                */
/*==============================================================*/
create table blog_label_association
(
   id                   bigint not null auto_increment,
   association_id       bigint,
   association_type     varchar(50),
   created_at           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: blog_topic                                            */
/*==============================================================*/
create table blog_topic
(
   id                   bigint not null auto_increment,
   title                varchar(255),
   content              text,
   user_id              bigint,
   category_id          bigint,
   read_count           int,
   like_count           int,
   created_at           datetime,
   status               int comment '1-正常；
            0-草稿；
            -1-删除；',
   primary key (id)
);

/*==============================================================*/
/* Table: blog_user                                             */
/*==============================================================*/
create table blog_user
(
   id                   bigint not null,
   login_name           varchar(50),
   user_name            varchar(50),
   password             varchar(100),
   salt                 varchar(50),
   avatar               varchar(100),
   profile              varchar(255),
   last_login_at        datetime,
   created_at           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: blog_user_category                                    */
/*==============================================================*/
create table blog_user_category
(
   id                   bigint not null auto_increment,
   name                 varchar(255),
   user_id              bigint,
   sort                 int,
   created_at           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: blog_user_outreach                                    */
/*==============================================================*/
create table blog_user_outreach
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   type                 varchar(50),
   url                  varchar(255),
   icon                 varchar(255),
   created_at           datetime,
   primary key (id)
);

alter table blog_user_outreach comment 'GitHub/邮箱/Gitee/等等';

