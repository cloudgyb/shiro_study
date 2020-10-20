create table users(
    id int unsigned primary key auto_increment,
    username varchar(100) not null,
    password varchar(50) not null ,
    password_salt varchar(50)
);
insert into users(username,password,password_salt) value('geng','123456','aaaaa');
insert into users(username,password,password_salt) value('zhangsan','666666','aaaaa');
insert into users(username,password,password_salt) value('lisi','123456','aaaaa');
insert into users(username,password,password_salt) value('wangwu','123456','aaaaa');

create table user_roles(
    id int unsigned primary key auto_increment,
    username varchar(100),
    role_name varchar(50)
);
insert into user_roles(username, role_name) value ('geng','admin');
insert into user_roles(username, role_name) value ('zhangsan','user');
insert into user_roles(username, role_name) value ('lisi','user');
insert into user_roles(username, role_name) value ('wangwu','audit');

create table roles_permissions(
    id int unsigned primary key auto_increment,
    role_name varchar(100),
    permission varchar(50)
);

insert into roles_permissions(role_name, permission) value ('admin','*');
insert into roles_permissions(role_name, permission) value ('user','sys:task:*');
insert into roles_permissions(role_name, permission) value ('audit','sys:log:*');