create table if not exists authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique if not exists index ix_auth_username on authorities (username,authority);

create table if not exists persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null,last_used timestamp not null);

create table if not exists AUDITORIUM(NAME varchar_ignorecase(50) not null primary key, SEATS_NUMBER numeric, VIP_SEATS varchar_ignorecase(500) not null)
