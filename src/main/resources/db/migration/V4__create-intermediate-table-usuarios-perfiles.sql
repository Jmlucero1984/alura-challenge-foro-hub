CREATE TABLE usuarios_perfiles (
    id bigint not null auto_increment,
    usuario_id bigint NOT NULL,
    perfil_id bigint NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (perfil_id) REFERENCES perfiles(id),
    primary key (id)
);