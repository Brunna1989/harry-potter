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

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL (via Docker)
- HikariCP (connection pool)
- HTML + JavaScript (frontend simples)
- Lombok
- Swagger (documentação da API)

---

## ⚙️ Configurações da API

### CORS

Todos os endpoints permitem requisições de qualquer origem:

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
````

| Método | URL                                     | Descrição                                                             |
| ------ | --------------------------------------- | --------------------------------------------------------------------- |
| POST   | `/api/bruxos`                           | Cria um bruxo. Corpo JSON: `{ "nome": "Draco", "casa": "Sonserina" }` |
| GET    | `/api/bruxos`                           | Lista todos os bruxos cadastrados                                     |
| GET    | `/api/bruxos/listar-nomes`              | Lista apenas os nomes dos bruxos                                      |
| GET    | `/api/bruxos/batalha?bruxo1=X&bruxo2=Y` | Realiza batalha entre dois bruxos                                     |
| DELETE | `/api/bruxos`                           | Deleta todos os bruxos                                                |
| DELETE | `/api/bruxos/{id}`                      | Deleta um bruxo específico pelo ID                                    |


````
🧾 Estrutura de DTOs

BruxoRequestDTO: nome, casa

BruxoResponseDTO: id, nome, casa, feitico

BatalhaResponseDTO: nomeBruxo1, feiticoBruxo1, nomeBruxo2, feiticoBruxo2, vencedor

````

````
💻 Frontend

Arquivo: index.html (simples interface para iniciar batalhas)

Seleciona dois bruxos

Dispara uma requisição para /api/bruxos/batalha

Exibe resultado da batalha em tela

````

```

🗂 Estrutura de Classes

model/Bruxo (abstrato)

model/BruxoGrifinoria e model/BruxoSonserina

model/Magia (interface para lançar feitiço)

repository/BruxoRepository

service/BruxoService

controller/BruxoController

dto/* (request e response)

exception/BruxoNotFoundException e GlobalExceptionHandler

````

# 🐘 Configuração PostgreSQL com Docker

docker-compose.yml:

````Yaml
version: '3.9'
services:
batalha:
image: postgres:latest
container_name: batalha
restart: always
environment:
POSTGRES_USER: harry
POSTGRES_PASSWORD: potter
POSTGRES_DB: batalha
ports:
- "5432:5432"
volumes:
- postgres_data:/var/lib/postgresql/data
- ./init-db:/docker-entrypoint-initdb.d

pgadmin:
image: dpage/pgadmin4:latest
container_name: pgadmin
restart: always
environment:
PGADMIN_DEFAULT_EMAIL: admin@harrypotter.com
PGADMIN_DEFAULT_PASSWORD: 123456
ports:
- "5050:80"
depends_on:
- batalha

volumes:
postgres_data:
````

```properties
# ===============================
# 🔗 CONEXÃO COM O BANCO POSTGRES
# ===============================
spring.datasource.url=jdbc:postgresql://localhost:5432/batalha
spring.datasource.username=harry
spring.datasource.password=potter
spring.datasource.driver-class-name=org.postgresql.Driver

# ===============================
# ⚙️ JPA / HIBERNATE
# ===============================
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

# ===============================
# 🚀 HIKARI CONNECTION POOL
# ===============================
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.pool-name=HikariCP
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.validation-timeout=5000

# ===============================
# 🧾 LOGGING
# ===============================
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
logging.level.org.springframework.jdbc.core=DEBUG
logging.level.com.br.harrypotter=DEBUG

# ===============================
# 🌐 SERVIDOR WEB
# ===============================
server.port=8080
server.servlet.context-path=/

# ===============================
# 🧩 TESTES DE INTEGRAÇÃO
# ===============================
spring.test.database.replace=none
spring.jpa.properties.hibernate.default_schema=public
````

# 📖 Funcionamento do Swagger no Projeto Harry Potter

O Swagger (via SpringDoc) foi configurado para gerar a **documentação interativa da API** automaticamente a partir dos seus controllers e endpoints.

---

## 1️⃣ URL de Acesso

Após iniciar a aplicação (`Application.java`), abra o navegador e acesse:

http://localhost:8080/swagger-ui.html


Isso abrirá a interface do Swagger UI, onde você verá todos os endpoints disponíveis do projeto, organizados por **tags** (ex.: "Bruxos").

O JSON da documentação pode ser acessado diretamente em:

http://localhost:8080/v3/api-docs



---

## 2️⃣ Funcionalidades do Swagger

- **Listar endpoints**: Todos os métodos do `BruxoController` são exibidos com suas rotas, métodos HTTP e descrições.
- **Testar requisições**: Você pode enviar requisições diretamente pelo Swagger UI, preenchendo parâmetros e bodies.
- **Visualizar respostas**: O Swagger mostra o modelo de dados (`BruxoRequestDTO`, `BruxoResponseDTO`, `BatalhaResponseDTO`) e os códigos de resposta (`200`, `400`, `404`, etc.).
- **Filtros de pacotes e paths**: Apenas endpoints dentro de `com.br.harrypotter.controller` e com path `/api/**` estão disponíveis.

---

## 3️⃣ Exemplos de Requisições

### Criar um bruxo

POST /api/bruxos
Body:
{
"nome": "Harry Potter",
"casa": "Grifinória",
"poder": 85
}


### Listar Bruxos

GET /api/bruxos

### Realizar uma batalha entre dois bruxos

GET /api/bruxos/batalha?bruxo1=Harry&bruxo2=Draco

### Deletar um bruxo por ID

DELETE /api/bruxos/{id}

### Deletar todos os bruxos

DELETE /api/bruxos


---

## 4️⃣ Observações

- O Swagger UI é **interativo**, permitindo testar todos os endpoints sem precisar de ferramentas externas.
- Todos os DTOs, parâmetros e respostas estão documentados automaticamente via **anotações `@Operation`, `@ApiResponse` e `@Parameter`** no controller.
- Qualquer alteração nos controllers será refletida automaticamente no Swagger ao reiniciar a aplicação.


## Você ainda pode acessar o index.html dentro de resources>static:

**Abra o arquivo index.html em um navegador de sua preferência.**

- Cadastre os bruxos via requisição postman: 

http://localhost:8080/api/bruxos

{
"nome": "Marlene",
"casa": "Sonserina"
}



4️⃣ **Escolha os bruxos e clique em “Iniciar Batalha”**
⚡✨🧙‍♂️💥


