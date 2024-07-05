create table topicos (
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje text not null,
    fecha_creacion datetime not null default current_timestamp,
    status boolean not null,
    autor varchar(100) not null,
    curso varchar(100) not null,
    respuestas varchar(100),
    primary key (id)
)