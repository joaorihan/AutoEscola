# Sistema de Auto-Escola - Spring Boot

Sistema de gerenciamento de auto-escola desenvolvido em Spring Boot, incluindo cadastro de instrutores, alunos e agendamento de instruções.

## 👥 Integrantes da Equipe

| Nome | RM |
|------|-----|
| Rodrigo Fernandes Serafim | RM550816 |
| João Antonio Rihan | RM99656 |
| Adriano Lopes | RM98574 |
| Henrique de Brito | RM98831 |
| Rodrigo Lima | RM98326 |

## Funcionalidades

### Gestão de Instrutores
- Cadastro de instrutores com dados completos
- Listagem paginada (10 registros por página)
- Atualização de dados (nome, telefone, endereço)
- Exclusão lógica (marca como inativo)
- Especialidades: Motos, Carros, Vans e Caminhões

###  Gestão de Alunos
- Cadastro de alunos com dados completos
- Listagem paginada (10 registros por página)
- Atualização de dados (nome, telefone, endereço)
- Exclusão lógica (marca como inativo)

### Agendamento de Instruções
- Agendamento com validações de negócio
- Escolha automática de instrutor disponível
- Cancelamento com antecedência mínima de 24h
- Validações de horário de funcionamento
- Controle de limite de instruções por aluno

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **MySQL 8.0**
- **Hibernate**
- **Maven**
- **Lombok**
- **Bean Validation**

##  Como Executar

### 1. Clone o Repositório
```bash
git clone https://github.com/joaorihan/AutoEscola.git
cd AutoEscola
```

### 2. Configure o Banco de Dados

#### 2.1. Instale e inicie o MySQL
Certifique-se de que o MySQL está rodando na sua máquina.

#### 2.2. Crie o banco de dados
```sql
CREATE DATABASE auto_escola;
```

#### 2.3. Configure as credenciais
Edite o arquivo `src/main/resources/application.properties` e ajuste as configurações do banco:

```properties
spring.datasource.url=jdbc:mysql://localhost/auto_escola
spring.datasource.username=root
spring.datasource.password=sua_senha_aqui
```

### 3. Execute o Projeto

#### Usando Maven
```bash
mvn spring-boot:run
```

### 4. Testando

A aplicação estará disponível em: **http://localhost:8080**

## 📚 Endpoints da API

### Instrutores
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/instrutor` | Listar instrutores (paginado) |
| `POST` | `/instrutor` | Cadastrar instrutor |
| `PUT` | `/instrutor` | Atualizar instrutor |
| `DELETE` | `/instrutor/{id}` | Excluir instrutor (lógico) |

### Alunos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/aluno` | Listar alunos (paginado) |
| `POST` | `/aluno` | Cadastrar aluno |
| `PUT` | `/aluno` | Atualizar aluno |
| `DELETE` | `/aluno/{id}` | Excluir aluno (lógico) |

### Agendamentos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/agendamento` | Listar agendamentos (paginado) |
| `POST` | `/agendamento` | Agendar instrução |
| `POST` | `/agendamento/cancelar` | Cancelar agendamento |

## 📝 Exemplos de Uso

### Cadastrar Instrutor
```bash
curl -X POST http://localhost:8080/instrutor \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "telefone": "11999999999",
    "cnh": "12345678901",
    "especialidade": "CARROS",
    "endereco": {
      "logradouro": "Rua A",
      "bairro": "Centro",
      "cidade": "São Paulo",
      "uf": "SP",
      "cep": "01234567"
    }
  }'
```

### Cadastrar Aluno
```bash
curl -X POST http://localhost:8080/aluno \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "email": "maria@email.com",
    "telefone": "11888888888",
    "cpf": "12345678901",
    "endereco": {
      "logradouro": "Rua B",
      "bairro": "Vila Nova",
      "cidade": "São Paulo",
      "uf": "SP",
      "cep": "01234567"
    }
  }'
```

### Agendar Instrução
```bash
curl -X POST http://localhost:8080/agendamento \
  -H "Content-Type: application/json" \
  -d '{
    "alunoId": 1,
    "instrutorId": 1,
    "dataHora": "2024-12-25T10:00:00"
  }'
```

## Configurações

### Banco de Dados
O projeto está configurado para usar o Hibernate para criar as tabelas automaticamente (`spring.jpa.hibernate.ddl-auto=create-drop`).

### Paginação
- **Tamanho padrão**: 10 registros por página
- **Ordenação**: Por nome (crescente)

### Validações de Negócio

#### Agendamentos
- **Horário de funcionamento**: Segunda a sábado, 06:00 às 21:00
- **Duração**: 1 hora fixa
- **Antecedência mínima**: 30 minutos
- **Limite por aluno**: Máximo 2 instruções por dia
- **Cancelamento**: Antecedência mínima de 24 horas



## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br/com/fiap3espg/spring_boot_project/
│   │       ├── controller/          # Controllers REST
│   │       ├── aluno/              # Entidades e DTOs de Aluno
│   │       ├── instrutor/          # Entidades e DTOs de Instrutor
│   │       ├── agendamento/        # Entidades e DTOs de Agendamento
│   │       ├── endereco/           # Classe de Endereço
│   │       └── SpringBootProject3EspgApplication.java
│   └── resources/
│       ├── application.properties  # Configurações
│       └── db/migration/          # Migrações do banco (não utilizadas)
└── test/                          # Testes
```
