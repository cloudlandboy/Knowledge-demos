drop table if exists sessions;

create table sessions (
  id varchar(200) primary key,
  session varchar(2000)
) charset=utf8 ENGINE=InnoDB;