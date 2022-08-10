create table if not exists fund(
 fid integer not null auto_increments,
 fname varchar(50),
 createtime datetime default current_timestamp,
 primary key(fid)
);

create table inf not exists fundstock(
	sid integer not null auto_increments,
	fid integer,
 	symbol varchar(50),
 	share integer,
 	foreign key(fid) references fund(fid),
 	primary key(fid)
);

insert into fund(fname) values('A');
insert into fund(fname) values('B');
insert into fund(fname) values('C');
insert into fund(fname) values('D');
insert into fund(fname) values('E');
insert into fund(fname) values('F');
insert into fund(fname) values('G');

insert into fundstock(fid,symbol,share) values(1,"2330.TW",50000);
insert into fundstock(fid,symbol,share) values(2,"2330.TW",60000);
insert into fundstock(fid,symbol,share) values(2,"2303.TW",70000);
insert into fundstock(fid,symbol,share) values(3,"2376.TW",80000);
insert into fundstock(fid,symbol,share) values(3,"1101.TW",90000);
insert into fundstock(fid,symbol,share) values(3,"2317.TW",80000);
insert into fundstock(fid,symbol,share) values(4,"3231.TW",70000);
insert into fundstock(fid,symbol,share) values(4,"2356.TW",60000);
insert into fundstock(fid,symbol,share) values(4,"2880.TW",50000);
insert into fundstock(fid,symbol,share) values(4,"2891.TW",40000);
insert into fundstock(fid,symbol,share) values(5,"2330.TW",30000);
insert into fundstock(fid,symbol,share) values(5,"1101.TW",20000);
insert into fundstock(fid,symbol,share) values(5,"2317.TW",10000);
insert into fundstock(fid,symbol,share) values(5,"2886.TW",20000);
insert into fundstock(fid,symbol,share) values(5,"2002.TW",30000);
insert into fundstock(fid,symbol,share) values(6,"1201.TW",30000);

-- 向左關聯找出目前基金中對應fundstock的資料
select f.fid,f.fname,f.createtime,
s.sid,s.fid,s.symbol,s.share
from fund f left join fundstock s 
on f.fid=s.fid
where s.fid is null

-- 向左關聯_差集找出目前基金中尚未對應到fundstock的資料
select f.fid,f.fname,f.createtime,
s.sid,s.fid,s.symbol,s.share
from fund f left join fundstock s 
on f.fid=s.fid
where s.fid is null;

-- 新增測試資料(沒有掛到fund資料)
insert into fundstock (symbol) values("2480.TW");
insert into fundstock (symbol) values("1201.TW");
insert into fundstock (symbol) values("1216.TW");

-- 向右關聯差集, 找出在fundstock中尚未掛載fund的資料
select f.fid , f.fname , f.createtime ,
s.sid , s.fid , s.symbol , s.share 
from fund f right join fundstock s
on f.fid = s.fid 
where s.fid is null

