drop table notice;
drop table users;

create  table  notice ( 
	num  number(4) primary key, 
	writer  varchar2(15),
	indate  varchar2(20), 
	title varchar2(50),
	cont  varchar2(300)
);

create table users (
	id  varchar2(15) primary key, 
	pw  varchar2(15)
);

insert into users values('admin','admin1004');
insert into users values('user01','password01');

insert into notice values('0','은규','하하','호호','히히');
commit;
