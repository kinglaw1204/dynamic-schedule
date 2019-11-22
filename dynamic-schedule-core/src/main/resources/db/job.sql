-- auto-generated definition
create table tb_job
(
  id             text not null
    primary key,
  status         int    not null,
  timestamp      bigint not null,
  strategy       text,
  body           text,
  class_strategy text,
  class_body     text,
  class_job      text
);

