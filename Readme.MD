## ⚡✨ **Batalha de Bruxos - Harry Potter** 🧙‍♂️🪄

Bem-vindo a **Batalha de Bruxos**, um projeto inspirado no universo mágico de **Harry Potter**, onde você pode escolher dois bruxos lendários e assistir a uma batalha de magias épica! 🧙‍♂️⚔️✨

---

### 📜 **Sobre o Projeto**

Neste jogo simples, desenvolvido com **Java + Spring Boot** no backend e **HTML + JS** no frontend, você poderá:

- 📋 Cadastrar bruxos com nome e casa.
- 🔮 Simular uma batalha entre bruxos famosos como **Bruxo Grifinória** 🧙‍♂️✨ e **Bruxo Sonserina** 🐍🪄.
- 🎇 Ver qual magia cada bruxo lançou.
- 🏆 Descobrir quem vence a batalha!

---

## 🔹 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL (via Docker)
- HikariCP
- HTML + JavaScript
- Lombok
- Swagger (SpringDoc)

---
## 🌳 Estrutura do Projeto (TREE)

```css
com/br/harrypotter
├── model
│   ├── Bruxo.java
│   ├── BruxoGrifinoria.java
│   ├── BruxoSonserina.java
│   ├── Magia.java
│   └── Batalha.java
│
├── dto
│   ├── BruxoRequestDTO.java
│   ├── BruxoResponseDTO.java
│   ├── BatalhaRequestDto.java
│   └── BatalhaResponseDTO.java
│
├── repository
│   ├── BruxoRepository.java
│   └── BatalhaRepository.java
│
├── service
│   ├── BruxoService.java
│   └── BatalhaService.java
│
├── controller
│   ├── BruxoController.java
│   └── BatalhaController.java
│
├── exception
│   ├── CasaInvalidaException.java
│   ├── BruxoNaoEncontradoException.java
│   └── BatalhaInvalidaException.java
│
└── config
└── SwaggerConfig
````

## 🗂 Estrutura de Classes

Abaixo está a estrutura completa das classes utilizadas neste projeto Harry Potter – Batalha de Bruxos.

---


### 📁 **package com.br.harrypotter.config**

#### ⚙️ `SwaggerConfig`
- Configuração do Swagger/OpenAPI para documentação da API

### 📁 **package com.br.harrypotter.model**

#### 🧙‍♂️ `Bruxo` (abstract)
- `id`
- `nome`
- `getCasa()` (abstract)
- Implementa `Magia` (interface)

#### 🦁 `BruxoGrifinoria`
- Extende `Bruxo`
- `getCasa()` retorna `"Grifinoria"`
- `lancarFeitico()` lança **Expelliarmus**

#### 🐍 `BruxoSonserina`
- Extende `Bruxo`
- `getCasa()` retorna `"Sonserina"`
- `lancarFeitico()` lança **Serpensortia**

#### ✨ `Magia` (interface)
- Método: `String lancarFeitico()`

#### ⚔️ `Batalha`
- `id`
- `bruxo1` (ManyToOne)
- `bruxo2` (ManyToOne)
- `vencedorId`
- `resultado`

---

### 📁 **package com.br.harrypotter.dto**

#### 🧙‍♂️ `BruxoRequestDTO`
- `nome`
- `casa`

#### 🧙‍♂️ `BruxoResponseDTO`
- `id`
- `nome`
- `casa`
- `feitico`

#### ⚔️ `BatalhaRequestDto`
- `bruxo1` (nome)
- `bruxo2` (nome)

#### ⚔️ `BatalhaResponseDTO`
- `id`
- `nomeBruxo1`
- `feiticoBruxo1`
- `nomeBruxo2`
- `feiticoBruxo2`
- `vencedor`
- Override de `toString()`

---

### 📁 **package com.br.harrypotter.repository**

#### 📘 `BruxoRepository`
- Extende `JpaRepository<Bruxo, Long>`
- Método customizado:  
  `Optional<Bruxo> findByNome(String nome)`

#### 📙 `BatalhaRepository`
- Extende `JpaRepository<Batalha, Long>`

---

### 📁 **package com.br.harrypotter.service**

#### 🧙‍♂️ `BruxoService`
- Criar bruxo por casa
- Listar bruxos
- Buscar por nome
- Buscar por ID
- Deletar por ID
- Deletar todos

#### ⚔️ `BatalhaService`
- Regras da batalha
- Validações (nomes vazios, bruxos iguais, bruxo inexistente)
- Cálculo de poder dos feitiços
- Armazena histórico
- Buscar batalha por ID

---

### 📁 **package com.br.harrypotter.controller**

#### 🧙‍♂️ `BruxoController`
- `POST /api/bruxos` — criar bruxo
- `GET /api/bruxos` — listar bruxos
- `GET /api/bruxos/{id}` — buscar por ID
- `DELETE /api/bruxos/{id}` — deletar por ID
- `DELETE /api/bruxos` — deletar todos

#### ⚔️ `BatalhaController`
- `POST /api/batalhas` — realizar batalha
- `GET /api/batalhas/historico` — histórico
- `GET /api/batalhas/{id}` — buscar batalha

---

### 📁 **package com.br.harrypotter.exception**

#### 🚫 `CasaInvalidaException`
- Lançada se casa não for **Grifinoria** ou **Sonserina**

#### 🚫 `BruxoNaoEncontradoException`
- Lançada quando bruxo não existe (por ID ou nome)

#### 🚫 `BatalhaInvalidaException`
- Nomes vazios, repetidos ou batalha não encontrada

---

## 📚 **Endpoints da API**

| Método   | URL                          | Descrição                                                                 |
|----------|------------------------------|---------------------------------------------------------------------------|
| POST     | `/api/bruxos`                | Cria um bruxo. Body: `{ "nome": "Draco", "casa": "Sonserina" }`          |
| GET      | `/api/bruxos`                | Lista todos os bruxos cadastrados                                         |
| GET      | `/api/bruxos/{id}`           | Busca um bruxo específico pelo ID                                         |
| DELETE   | `/api/bruxos`                | Deleta todos os bruxos                                                    |
| DELETE   | `/api/bruxos/{id}`           | Deleta um bruxo específico pelo ID                                        |
| POST     | `/api/batalhas`              | Realiza batalha entre dois bruxos. Body: `{ "bruxo1": "...", "bruxo2": "..." }` |
| GET      | `/api/batalhas/historico`    | Lista todas as batalhas realizadas                                        |
| GET      | `/api/batalhas/{id}`         | Busca uma batalha específica pelo ID                                      |

---

## 🐳📦 Acessando o Banco de Dados PostgreSQL via Docker:

Este projeto utiliza o PostgreSQL em um container Docker, conforme configurado no docker-compose.yml.
Abaixo estão os comandos essenciais para acessar, listar tabelas e consultar dados diretamente no banco utilizado pela API.

---

## ▶️ Subindo os Containers (PostgreSQL + PgAdmin)

Para iniciar o banco de dados e o PgAdmin:

```bash
docker-compose up -d
```

PostgreSQL ficará disponível em: www.localhost:5432

PgAdmin ficará disponível em: http://localhost:5050

---

## 🗄️ Conectando ao Banco de Dados:

Dentro do container, execute:

```bash
psql -U harry -d batalha
```

Usuário: harry

Senha: definida no docker-compose (potter)

Banco: batalha

Você verá o prompt:

```ini
batalha=>
````

---

## 📋 Listando Tabelas do Banco

Para ver todas as tabelas existentes no schema público:

```sql
\dt
````

Se a aplicação já tiver rodado pelo menos uma vez, você verá tabelas como:

- bruxo
- batalhas

---

## 🔍 Consultando Dados dos Bruxos

Para visualizar os bruxos cadastrados via API:

```sql
SELECT * FROM bruxo;
```

---

## ⚔️ Consultando Histórico de Batalhas

```sql
SELECT * FROM batalhas;
```

---

## 🚪 Saindo do PostgreSQL

```sql
\q
````

E depois, para sair do container:

```bash
exit
````

## 📘 Documentação da API — Swagger

Este projeto utiliza **Swagger UI** e **Springdoc OpenAPI** para gerar automaticamente a documentação interativa da API.

### 🔗 Acesso ao Swagger

Após iniciar a aplicação, acesse:

👉 **http://localhost:8080/swagger-ui/index.html**

Esse endereço abrirá uma interface gráfica onde você pode:
- Visualizar todos os endpoints
- Ver modelos de entrada e saída (DTOs)
- Enviar requisições diretamente pela interface
- Testar a API sem necessidade de ferramentas externas como Postman ou Insomnia

---

## 🧭 Como funciona o Swagger neste projeto

### ✔️ Geração automática
O Swagger faz a leitura:
- das anotações das classes Controller,
- dos DTOs,
- dos modelos (Entities),
- e das respostas HTTP.

A partir disso, ele gera automaticamente a documentação com:
- Métodos disponíveis (GET, POST, DELETE…)
- URLs de cada endpoint
- Exemplo de requisição e resposta
- Códigos HTTP retornados
- Parâmetros, bodies e path variables
- Descrição dos modelos de dados

### ✔️ Interface Interativa
A interface do Swagger permite:
- Executar endpoints clicando em **"Try it out"**
- Enviar JSONs diretamente pelo navegador
- Visualizar exemplos gerados automaticamente
- Receber respostas com corpo, headers e status code

### ✔️ Facilita testes e desenvolvimento
Você pode usar o Swagger para:
- Testar CRUDs rapidamente
- Validar regras de negócio do serviço
- Verificar se as exceptions estão sendo retornadas corretamente
- Acompanhar alterações nos modelos e DTOs
- Demonstrar o funcionamento da API para outros desenvolvedores

---

## 📄 Arquivo de configuração do Swagger

O projeto contém uma classe de configuração (exemplo típico):

```java
@Configuration
@Bean
public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("API Harry Potter")
                    .version("1.0")
                    .description("Documentação da API do Sistema Harry Potter")
                    .contact(new Contact()
                            .name("Equipe de Desenvolvimento")
                            .email("devs@hogwarts.com"))
                    .license(new License()
                            .name("Apache 2.0")
                            .url("http://springdoc.org")));
}
````

## 🧪 Testes com Swagger

Embora o Swagger não faça testes por conta própria, ele é uma excelente ferramenta para:

Validar visualmente se endpoints estão funcionando

Conferir retornos das exceções personalizadas

Testar respostas com diferentes cenários

Use-o como apoio enquanto constrói Controller Tests e Integration Tests.

---

## 📌 Observações Importantes

A documentação é atualizada automaticamente quando você altera Controllers ou DTOs.

Não é necessário reiniciar a aplicação para ver mudanças nos endpoints, apenas recompilar/restart normal do backend.

O Swagger funciona tanto com JSON quanto com XML (se habilitado).

Ótimo para onboard rápido de novos desenvolvedores no seu projeto.

## 📄 Licença

Este projeto foi desenvolvido exclusivamente para fins educacionais e de aprendizado.
Você é livre para estudar, modificar, melhorar e utilizar o código como referência em seus próprios estudos ou projetos pessoais.

⚡✨🧙‍♂️💥
