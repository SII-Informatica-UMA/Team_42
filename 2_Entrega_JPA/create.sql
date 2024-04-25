create sequence dieta_seq start with 1 increment by 50;
create table dieta (id integer not null, alimentos varchar(255) array, clientes integer array, descripcion varchar(255), duracion_dias integer not null, id_entrenador integer not null, nombre varchar(255), objetivo varchar(255), recomendaciones varchar(255), primary key (id));
