CREATE TABLE Usuarios (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           email VARCHAR(255) NOT NULL,
                           password VARCHAR(255) NOT NULL
);



INSERT INTO Usuarios (email, password) VALUES ('teste@teste.com.br', '$2a$10$H/qfml4KFzjjy.ZGxwQ4j.hDW/Wkrgjsoox1khFHFS5qrw9E1Dkx.');


-- Tabela Carro
CREATE TABLE carro (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       placa VARCHAR(20) NOT NULL,
                       modelo VARCHAR(100) NOT NULL
);

-- Inserir dados na tabela Carro
INSERT INTO carro (placa, modelo) VALUES
                                      ('ABC1234', 'Toyota Corolla'),
                                      ('DEF5678', 'Honda Civic'),
                                      ('GHI9101', 'Ford Fiesta'),
                                      ('JKL1121', 'Chevrolet Onix'),
                                      ('MNO2132', 'Volkswagen Gol');

-- Tabela Cliente
CREATE TABLE cliente (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         celular VARCHAR(20) NOT NULL
);

-- Inserir dados na tabela Cliente
INSERT INTO cliente (nome, celular) VALUES
                                        ('João da Silva', '11987654321'),
                                        ('Maria Oliveira', '11987651234'),
                                        ('Pedro Santos', '11987659876'),
                                        ('Ana Souza', '11987658765'),
                                        ('Carlos Pereira', '11987652345');

-- Tabela Servicos
CREATE TABLE servicos (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          lavagem VARCHAR(100) NOT NULL,
                          valorLavagem DOUBLE NOT NULL
);

-- Inserir dados na tabela Servicos
INSERT INTO servicos (lavagem, valorLavagem) VALUES
                                                 ('DUCHA', 30.00),
                                                 ('LAVAGEM SIMPLES', 50.00),
                                                 ('LAVAGEM COMPLETA', 80.00),
                                                 ('LAVAGEM COMPLETA SEM MOTOR', 70.00),
                                                 ('LAVAGEM DO MOTOR COMPLETO', 60.00);