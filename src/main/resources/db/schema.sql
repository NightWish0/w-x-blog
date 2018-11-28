/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/11/28 11:01:26                          */
/*==============================================================*/


drop table if exists blog_comment;

drop table if exists blog_label;

drop table if exists blog_label_association;

drop table if exists blog_topic;

drop table if exists blog_user;

drop table if exists blog_user_category;

drop table if exists blog_user_outreach;

drop table if exists qiniu_file;

/*==============================================================*/
/* Table: blog_comment                                          */
/*==============================================================*/
create table blog_comment
(
   id                   bigint not null auto_increment comment '主键id',
   name                 varchar(50) comment '用户名',
   email                varchar(50) comment '邮箱地址',
   receiver_id          bigint comment '接收人id',
   parent_id            bigint comment '父节点id',
   topic_id             bigint comment '文章id',
   content              varchar(255) comment '评论内容',
   created_at           datetime comment '创建时间',
   author_id            char(10) comment '文章作者id,不为null则表示为文章作者的评论',
   primary key (id)
);

/*==============================================================*/
/* Table: blog_label                                            */
/*==============================================================*/
create table blog_label
(
   id                   bigint not null auto_increment comment '主键id',
   name                 varchar(50) comment '标签名',
   created_at           datetime comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: blog_label_association                                */
/*==============================================================*/
create table blog_label_association
(
   id                   bigint not null auto_increment comment '主键id',
   topic_id             bigint comment '文章id',
   label_id             varchar(50) comment '标签id',
   created_at           datetime comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: blog_topic                                            */
/*==============================================================*/
create table blog_topic
(
   id                   bigint not null auto_increment comment '主键id',
   title                varchar(255) comment '标题',
   content              text comment '内容',
   markdown_content     text comment 'markdown语法内容',
   user_id              bigint comment '作者id',
   category_id          bigint comment '分类id',
   read_count           int default 0 comment '阅读量',
   like_count           int default 0 comment '点赞数',
   created_at           datetime comment '创建时间',
   status               int comment '1-正常；
            0-草稿；
            -1-删除；',
   file_mark_hash       varchar(255) comment '资源标识,时间戳的base64转码',
   primary key (id)
);

/*==============================================================*/
/* Table: blog_user                                             */
/*==============================================================*/
create table blog_user
(
   id                   bigint not null comment '主键id',
   login_name           varchar(50) comment '登录账号',
   user_name            varchar(50) comment '用户昵称',
   password             varchar(100) comment '登录密码，散列算法 md5(md5(password+loginName+salt))的base64转码',
   salt                 varchar(50) comment '盐，32位生成码',
   avatar               varchar(255) comment '头像链接',
   profile              varchar(255) comment '个人简介',
   last_login_at        datetime comment '上次登录时间',
   file_mark_hash       varchar(255) comment '资源标识,时间戳的base64转码',
   created_at           datetime comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: blog_user_category                                    */
/*==============================================================*/
create table blog_user_category
(
   id                   bigint not null auto_increment comment '主键id',
   name                 varchar(255) comment '名称',
   user_id              bigint comment '用户id',
   sort                 int comment '排序',
   created_at           datetime comment '创建时间',
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
   icon     varchar(255),
   created_at           datetime,
   primary key (id)
);

alter table blog_user_outreach comment 'GitHub/邮箱/Gitee/等等';

/*==============================================================*/
/* Table: qiniu_file                                            */
/*==============================================================*/
create table qiniu_file
(
   id                   bigint not null auto_increment comment '主键id',
   etag                 varchar(255) comment '资源指纹',
   hash_key             varchar(255) comment '资源名',
   bucket               varchar(255) comment '资源空间',
   fsize                bigint comment '资源尺寸,单位为字节',
   mime_type            varchar(100) comment '资源类型',
   created_at           datetime comment '创建时间',
   mark_hash            varchar(255) comment '资源标识，时间戳的base64转码',
   primary key (id)
);

