 create table topicos (
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje text not null,
    fecha_creacion datetime not null default current_timestamp,
    status boolean not null,
    autor_id bigint not null,
    curso_id bigint not null,
    primary key (id)
)