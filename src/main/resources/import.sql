INSERT INTO estado (nome, sigla) VALUES ('Acre', 'AC');
INSERT INTO estado (nome, sigla) VALUES ('Amazonas', 'AM');
INSERT INTO estado (nome, sigla) VALUES ('Goiás', 'GO');
INSERT INTO estado (nome, sigla) VALUES ('Pará', 'PA');
INSERT INTO estado (nome, sigla) VALUES ('Tocantins', 'TO');

INSERT INTO cidade (nome, id_estado) VALUES ('Manaus', 2);
INSERT INTO cidade (nome, id_estado) VALUES ('Palmas', 5);
INSERT INTO cidade (nome, id_estado) VALUES ('Guaraí', 5);
INSERT INTO cidade (nome, id_estado) VALUES ('Belém', 4);
INSERT INTO cidade (nome, id_estado) VALUES ('Goiânia', 3);

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Representando sofisticação intemporal, um design clássico com detalhes refinados.', 'ÉTERNEL');

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Focado em alta precisão e excelência em relojoaria.', 'PRÉCISION');

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Relógios que refletem um brilho elegante com materiais preciosos.', 'LUMIÈRE');

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Inspirado no cosmos, com designs únicos e detalhes celestiais.', 'ASTRAL');

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Design enigmático, com complicações exclusivas e acabamentos luxuosos.', 'MYSTIQUE');

INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Uma coleção icônica que redefine o design tradicional com sofisticação.', 'TANK COLLECTION');

INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Inspirada na elegância clássica, esta coleção simboliza inovação e estilo.', 'SANTOS COLLECTION');

INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Uma coleção que destaca formas arredondadas e fluidez no design.', 'BALLON BLEU COLLECTION');

INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Design ousado com uma abordagem moderna e elementos únicos.', 'PASHA COLLECTION');

INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Estilo refinado e intemporal, simbolizando luxo e exclusividade.', 'PANTHÈRE COLLECTION');

INSERT INTO fabricante (nome, descricao, anoFundacao, localizacao)
VALUES ('MAISON LUMIÈRE', 'Um ateliê renomado pela criação de relógios com detalhes meticulosos, combinando inovação e tradição, representando o luxo francês.', '1888-01-28', 'Paris, França');

INSERT INTO fabricante (nome, descricao, anoFundacao, localizacao)
VALUES ('HORLOGERIE ÉLITE', 'Especialistas em relojoaria de alto padrão, conhecidos por seus mecanismos precisos e designs sofisticados, que evocam elegância atemporal.', '1977-11-09', 'New York, EUA');

INSERT INTO usuario (nome, cpf, email, login, senha, perfil) VALUES ('Gabi User', '928.742.910-92', 'gabiuser.ra@gmail.com', 'gabiuser', '$2a$12$WQJbO2iSYyOQEsrZ2Gg3Se885ZcNEZ.7cCh5ifDC7FAdhFjuc1fRm', 'USER');
INSERT INTO usuario (nome, cpf, email, login, senha, perfil) VALUES ('Gabi Admin', '111.111.111-11', 'gabiadmin@gmail.com','gabiadmin','$2a$12$WQJbO2iSYyOQEsrZ2Gg3Se885ZcNEZ.7cCh5ifDC7FAdhFjuc1fRm', 'ADMIN');

INSERT INTO telefone (numero, id_usuario) VALUES ('(011) 98456-7812', 1);
INSERT INTO telefone (numero, id_usuario) VALUES ('(061) 99901-5842', 1);
INSERT INTO telefone (numero, id_usuario) VALUES ('(061) 99933-0572', 1);
INSERT INTO telefone (numero, id_usuario) VALUES ('(069) 99933-0572', 2);
