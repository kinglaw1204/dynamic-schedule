-- auto-generated definition
create table tb_job
(
  id             bigint not null
    primary key,
  status         int    not null,
  timestamp      bigint not null,
  strategy       text,
  body           text,
  class          text,
  class_strategy text,
  class_body     text,
  class_job      text
);

