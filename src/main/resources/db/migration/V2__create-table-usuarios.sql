create table usuarios (
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    correo_electronico varchar(100)  unique not null ,
    contraseña varchar(100) not null,
    primary key (id)
)
