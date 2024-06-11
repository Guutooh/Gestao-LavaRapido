CREATE TABLE Usuarios
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);



INSERT INTO Usuarios (email, password)
VALUES ('teste@teste.com.br', '$2a$10$H/qfml4KFzjjy.ZGxwQ4j.hDW/Wkrgjsoox1khFHFS5qrw9E1Dkx.');


-- Tabela Carro
CREATE TABLE carro
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    placa  VARCHAR(20)  NOT NULL,
    modelo VARCHAR(100) NOT NULL
);

-- Inserir dados na tabela Carro
INSERT INTO carro (placa, modelo)
VALUES ('ABC1234', 'Toyota Corolla'),
       ('DEF5678', 'Honda Civic'),
       ('GHI9101', 'Ford Fiesta'),
       ('JKL1121', 'Chevrolet Onix'),
       ('MNO2132', 'Volkswagen Gol');

-- Tabela Cliente
CREATE TABLE cliente
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome    VARCHAR(100) NOT NULL,
    celular VARCHAR(20)  NOT NULL
);

-- Inserir dados na tabela Cliente
INSERT INTO cliente (nome, celular)
VALUES ('João da Silva', '11987654321'),
       ('João da santos', '11987654322'),
       ('João da gomes', '11987654323'),
       ('Maria Oliveira', '11987651234'),
       ('Pedro Santos', '11987659876'),
       ('Ana Souza', '11987658765'),
       ('Carlos Pereira', '11987652345');

-- Tabela Servicos
CREATE TABLE servicos
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    COMPLETA_SEM_MOTOR VARCHAR(100) NOT NULL,
    valorCOMPLETA_SEM_MOTOR DOUBLE NOT NULL
);

-- Inserir dados na tabela Servicos
INSERT INTO servicos (COMPLETA_SEM_MOTOR, valorCOMPLETA_SEM_MOTOR)
VALUES ('DUCHA', 30.00),
       ('COMPLETA_SEM_MOTOR SIMPLES', 50.00),
       ('COMPLETA_SEM_MOTOR COMPLETA', 80.00),
       ('COMPLETA_SEM_MOTOR COMPLETA SEM MOTOR', 70.00),
       ('COMPLETA_SEM_MOTOR DO MOTOR COMPLETO', 60.00);


CREATE TABLE ordem_servico
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    data       TIMESTAMP,
    carro_id   INT,
    lavagem    VARCHAR(255),
    adicionais VARCHAR(255),
    cliente_id INT
);

INSERT INTO ordem_servico (data, carro_id, lavagem, adicionais, cliente_id)
VALUES ('2024-05-28 10:00:00', 1, 'DUCHA', 'OLEO', 1),
       ('2024-05-28 10:00:00', 1, 'COMPLETA', 'OLEO', 1),
       ('2024-05-28 11:00:00', 2, 'COMPLETA_SEM_MOTOR', 'CERA', 2),
       ('2024-05-28 12:00:00', 3, 'SIMPLES', 'VASELINA', 1),
       ('2024-05-28 13:00:00', 4, 'DUCHA', NULL, 3),
       ('2024-05-28 14:00:00', 5, 'COMPLETA_SEM_MOTOR', 'OLEO', 1),
       ('2024-05-28 15:00:00', 1, 'SIMPLES', 'CERA', 2),
       ('2024-05-28 16:00:00', 2, 'DUCHA', 'VASELINA', 1),
       ('2024-05-28 17:00:00', 3, 'COMPLETA_SEM_MOTOR', NULL, 3),
       ('2024-05-28 18:00:00', 4, 'SIMPLES', 'OLEO', 2),
       ('2024-05-28 19:00:00', 5, 'DUCHA', 'CERA', 1),
       ('2024-05-28 20:00:00', 1, 'COMPLETA_SEM_MOTOR', 'VASELINA', 2),
       ('2024-05-28 21:00:00', 2, 'SIMPLES', NULL, 1),
       ('2024-05-28 22:00:00', 3, 'DUCHA', 'OLEO', 3),
       ('2024-05-28 23:00:00', 4, 'COMPLETA_SEM_MOTOR', 'CERA', 1),
       ('2024-05-29 00:00:00', 5, 'SIMPLES', 'VASELINA', 2),
       ('2024-05-29 01:00:00', 1, 'DUCHA', NULL, 1),
       ('2024-05-29 02:00:00', 2, 'COMPLETA_SEM_MOTOR', 'OLEO', 2),
       ('2024-05-29 03:00:00', 3, 'SIMPLES', 'CERA', 3),
       ('2024-05-29 04:00:00', 4, 'DUCHA', 'VASELINA', 1),
       ('2024-05-29 05:00:00', 5, 'COMPLETA_SEM_MOTOR', NULL, 2),
       ('2024-05-29 06:00:00', 1, 'SIMPLES', 'OLEO', 1),
       ('2024-05-29 07:00:00', 2, 'DUCHA', 'CERA', 2),
       ('2024-05-29 08:00:00', 3, 'COMPLETA_SEM_MOTOR', 'VASELINA', 1),
       ('2024-05-29 09:00:00', 4, 'SIMPLES', NULL, 3),
       ('2024-05-29 10:00:00', 5, 'DUCHA', 'OLEO', 1),
       ('2024-05-29 11:00:00', 1, 'COMPLETA_SEM_MOTOR', 'CERA', 2),
       ('2024-05-29 12:00:00', 2, 'SIMPLES', 'VASELINA', 1),
       ('2024-05-29 13:00:00', 3, 'DUCHA', NULL, 3),
       ('2024-05-29 14:00:00', 4, 'COMPLETA_SEM_MOTOR', 'OLEO', 1),
       ('2024-05-29 15:00:00', 5, 'SIMPLES', 'CERA', 2);

















