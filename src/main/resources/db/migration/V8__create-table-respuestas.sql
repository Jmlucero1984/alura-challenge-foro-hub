create table respuestas (
    id bigint not null auto_increment,
    mensaje varchar(300) not null,
    topico_id int not null,
    fecha_creacion datetime not null default current_timestamp,
    autor_id int not null,
    solucion text not null,
    primary key (id)
)
