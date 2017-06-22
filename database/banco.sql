create table usuarios(
	id_usuario Integer not null primary key autoincrement,
	nome varchar(50) not null,
	email varchar(100) not null,
	senha varchar(50) not null,
	data_cadastro datetime not null
);
create table categoria(
	id_categoria Integer not null primary key autoincrement,
	nome varchar(10) not null,
	data_cadastro datetime not null
);
insert into categoria(nome, data_cadastro) values ("Crédito", datetime('now'));
insert into categoria(nome, data_cadastro) values ("Débito", datetime('now'));
create table sub_categoria(
	id_sub_categoria Integer not null primary key autoincrement,
	nome varchar(30) not null,
	id_categoria Integer not null,
	data_cadastro datetime not null,
	foreign key(id_categoria) references categoria(id_categoria)
);
