# 🏗️ Sistema de Logística de Entregas

Uma aplicação Java via terminal que simula a gestão de entregas de uma empresa de e-commerce. Desenvolvida com **JDBC** e **Maven**, o sistema permite gerenciar clientes, motoristas, pedidos e entregas, além de gerar relatórios analíticos.

---

## 🎯 Objetivo

Criar um sistema de logística que permita:

- Cadastrar clientes e motoristas  
- Criar pedidos e atribuí-los a entregas  
- Registrar eventos e atualizar status de entregas  
- Gerar relatórios de desempenho  

---

## 🗂️ Entidades do Sistema

| Entidade         | Campos                                                                                  |
|-----------------|-----------------------------------------------------------------------------------------|
| **Cliente**      | `id`, `nome`, `cpf_cnpj`, `endereco`, `cidade`, `estado`                                |
| **Motorista**    | `id`, `nome`, `cnh`, `veiculo`, `cidade_base`                                           |
| **Pedido**       | `id`, `cliente_id`, `data_pedido`, `volume_m3`, `peso_kg`, `status` (PENDENTE, ENTREGUE, CANCELADO) |
| **Entrega**      | `id`, `pedido_id`, `motorista_id`, `data_saida`, `data_entrega`, `status` (EM_ROTA, ENTREGUE, ATRASADA) |
| **HistoricoEntrega** | `id`, `entrega_id`, `data_evento`, `descricao`                                      |

---

## 📝 Regras de Negócio

- Um cliente pode ter vários pedidos.  
- Cada pedido pode ou não estar associado a uma entrega.  
- Um motorista pode realizar várias entregas.  
- Cada entrega pode ter múltiplos eventos registrados no histórico.

---

## 🚀 Funcionalidades do Sistema

1. Cadastrar Cliente  
2. Cadastrar Motorista  
3. Criar Pedido  
4. Atribuir Pedido a Motorista (Gerar Entrega)  
5. Registrar Evento de Entrega (Histórico)  
6. Atualizar Status da Entrega  
7. Listar Todas as Entregas com Cliente e Motorista  
8. Relatório: Total de Entregas por Motorista  
9. Relatório: Clientes com Maior Volume Entregue  
10. Relatório: Pedidos Pendentes por Estado  
11. Relatório: Entregas Atrasadas por Cidade  
12. Buscar Pedido por CPF/CNPJ do Cliente  
13. Cancelar Pedido  
14. Excluir Entrega (com validação)  
15. Excluir Cliente (com verificação de dependência)  
16. Excluir Motorista (com verificação de dependência)  
0. Sair  

---

## 💻 Requisitos Técnicos

- Projeto Maven com driver JDBC (MySQL ou PostgreSQL)  
- Uso de `PreparedStatement` e `ResultSet`  
- Estrutura em camadas: `model`, `dao`, `view`, `service`  
- Entrada via `Scanner`  
- Manipulação de datas com `java.time`  
- Consultas com `LEFT JOIN`, `GROUP BY`, filtros e condições  
- Boas práticas de modelagem relacional  

---

## 📊 Relatórios

- Total de entregas por motorista  
- Clientes com maior volume entregue  
- Pedidos pendentes por estado  
- Entregas atrasadas por cidade  

---

## 🔗 Conexão com o Banco de Dados

O sistema se conecta ao **MySQL** usando **JDBC**. As informações de conexão são:

```text
Host: localhost
Porta: 3356
Banco: SistemaLogistica
Usuário: root
Senha: mysqlPW
```

---

## 🗄️ Script SQL para Criação do Banco

```sql
CREATE DATABASE SistemaLogistica;
USE SistemaLogistica;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(100),
    endereco VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(100)
);

CREATE TABLE motorista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cnh VARCHAR(100),
    veiculo VARCHAR(100),
    cidade_base VARCHAR(100)
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    data_pedido DATE,
    volume DECIMAL(10,9),
    peso DECIMAL(10,9),
    status ENUM('Pendente','Entregue','Cancelado'),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE entrega (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT,
    motorista_id INT,
    data_saida DATE,
    data_entrada DATE,
    status ENUM('Em_Rota','Entrega','Atrasada'),
    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    FOREIGN KEY (motorista_id) REFERENCES motorista(id)
);

CREATE TABLE historico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    entrega_id INT,
    data_evento DATE,
    descricao TEXT,
    FOREIGN KEY (entrega_id) REFERENCES entrega(id)
);
