create sequence book_seq start with 1 increment by 50;
create sequence dieta_seq start with 1 increment by 50;
create table book (id bigint not null, isbn varchar(255), nombre varchar(255), primary key (id));
create table book_etiquetas (book_id bigint not null, etiquetas varchar(255));
create table dieta (id integer not null, alimentos varchar(255) array, clientes integer array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
alter table if exists book_etiquetas add constraint FKdf5om74surofqord1qa8rx64c foreign key (book_id) references book;
