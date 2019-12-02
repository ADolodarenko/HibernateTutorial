create table certificate (
   id serial not null,
   certificate_name VARCHAR(30),
   employee_id int,
   primary key (id)
);