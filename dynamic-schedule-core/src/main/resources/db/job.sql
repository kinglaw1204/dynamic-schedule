create table tb_job
(
  id        bigint not null primary key,
  status    int    not null,
  timestamp bigint not null,
  strategy  text,
  body      text
);