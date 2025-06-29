CREATE DATABASE "curso-jsp"
    WITH
    OWNER = nando
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
    
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(320) NOT NULL UNIQUE,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

    
INSERT INTO usuario(nome, email, login, senha)VALUES ('Fernando Adriano','lfernando.adriano@gmail.com' ,'admin', 'admin');

ALTER TABLE usuario ADD COLUMN admin BOOLEAN NOT NULL DEFAULT false;

ALTER TABLE usuario ADD COLUMN usuario_id BIGINT NOT NULL DEFAULT 1;

ALTER TABLE usuario ALTER COLUMN usuario_id DROP DEFAULT;

ALTER TABLE usuario ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE usuario ADD COLUMN perfil_id SMALLINT NOT NULL DEFAULT 1;

ALTER TABLE usuario ALTER COLUMN perfil_id DROP DEFAULT;

ALTER TABLE usuario ADD COLUMN sexo CHAR(1) NOT NULL DEFAULT 'F' CHECK (sexo IN ('F', 'M'));

ALTER TABLE usuario ALTER COLUMN sexo DROP DEFAULT;

CREATE TABLE imagem (
  id BIGSERIAL PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  imagem BYTEA NOT NULL, -- Armazena os dados binários da imagem ou // caminho VARCHAR(255) NOT NULL,
  extensao VARCHAR(10) NOT NULL, -- Exemplo: 'jpg', 'png', 'gif'
  tipo VARCHAR(50) NOT NULL,     -- Exemplo: 'perfil', 'galeria'
  descricao TEXT, -- Opcional
  data_upload TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);


CREATE TABLE endereco (
  id SERIAL PRIMARY KEY,
  cep VARCHAR(9),         -- Apenas números, 9 dígitos
  rua VARCHAR(100),
  numero VARCHAR(10),     -- Pode ter "S/N", letras e poucos dígitos
  bairro VARCHAR(60),
  cidade VARCHAR(60),
  estado VARCHAR(60),     -- Nome completo do estado (ex: "São Paulo")
  uf CHAR(2)              -- Sigla do estado (ex: "SP", "RJ")
);

ALTER TABLE usuario ADD COLUMN endereco_id BIGINT NOT NULL DEFAULT 1;

ALTER TABLE usuario ALTER COLUMN endereco_id DROP DEFAULT;

ALTER TABLE usuario
ADD CONSTRAINT fk_endereco FOREIGN KEY (endereco_id)
REFERENCES endereco(id)
ON DELETE RESTRICT;

ALTER TABLE usuario
ADD CONSTRAINT uq_usuario_endereco UNIQUE (endereco_id);

CREATE TABLE telefone (
  id BIGSERIAL PRIMARY KEY,
  numero VARCHAR(30) NOT NULL,
  usuario_id BIGINT NOT NULL,
  usuario_inclusao_id BIGINT NOT NULL,
  tipo_contato VARCHAR(20) NOT NULL,
  info_adicional VARCHAR(255) NULL,
  CONSTRAINT fk_telefone_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
  CONSTRAINT fk_telefone_usuario_inclusao FOREIGN KEY (usuario_inclusao_id) REFERENCES usuario(id) ON DELETE CASCADE
);

ALTER TABLE usuario ADD COLUMN data_nascimento DATE;

ALTER TABLE usuario ADD COLUMN renda_mensal NUMERIC(10,2);

CREATE TABLE versionadordb(
  id BIGSERIAL PRIMARY KEY,
  arquivo_sql VARCHAR(50) NOT NULL
);
