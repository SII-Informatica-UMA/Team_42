create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, clientes integer array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create table dieta_clientes (dieta_id integer not null, clientes integer, clientes_order integer not null, primary key (dieta_id, clientes_order));
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create table dieta_clientes (dieta_id integer not null, clientes integer, clientes_order integer not null, primary key (dieta_id, clientes_order));
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create table dieta_clientes (dieta_id integer not null, clientes integer, clientes_order integer not null, primary key (dieta_id, clientes_order));
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create table dieta_clientes (dieta_id integer not null, clientes integer, clientes_order integer not null, primary key (dieta_id, clientes_order));
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create table dieta_clientes (dieta_id integer not null, clientes integer, clientes_order integer not null, primary key (dieta_id, clientes_order));
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create table dieta_clientes (dieta_id integer not null, clientes integer, clientes_order integer not null, primary key (dieta_id, clientes_order));
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create table dieta_clientes (dieta_id integer not null, clientes integer, clientes_order integer not null, primary key (dieta_id, clientes_order));
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create table dieta_clientes (dieta_id integer not null, clientes integer, clientes_order integer not null, primary key (dieta_id, clientes_order));
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
create table dieta_clientes (dieta_id integer not null, clientes integer, clientes_order integer not null, primary key (dieta_id, clientes_order));
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
