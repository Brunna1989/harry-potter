## ⚡✨ **Batalha de Bruxos - Harry Potter** 🧙‍♂️🪄

Bem-vindo a **Batalha de Bruxos**, um projeto inspirado no universo mágico de **Harry Potter**, onde você pode escolher dois bruxos lendários e assistir a uma batalha de magias épica! 🧙‍♂️⚔️✨

---

### 📜 **Sobre o Projeto**

Neste jogo simples, desenvolvido com **Java + Spring Boot** no backend e **HTML + JS** no frontend, você poderá:
- 📋 Cadastrar bruxos com nome, idade e casa.
- 🔮 Simular uma batalha entre bruxos famosos como **Alvo Dumbledore** 🧙‍♂️✨ e **Severo Snape** 🐍🪄.
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



| Método   | URL                                     | Descrição                                                             |
| -------- | --------------------------------------- | --------------------------------------------------------------------- |
| `POST`   | `/api/bruxos`                           | Cria um bruxo. Corpo JSON: `{ "nome": "Draco", "casa": "Sonserina" }` |
| `GET`    | `/api/bruxos`                           | Lista todos os bruxos cadastrados                                     |
| `GET`    | `/api/bruxos/listar-nomes`              | Lista apenas os nomes dos bruxos                                      |
| `GET`    | `/api/bruxos/batalha?bruxo1=X&bruxo2=Y` | Realiza batalha entre dois bruxos                                     |
| `DELETE` | `/api/bruxos`                           | Deleta todos os bruxos                                                |
| `DELETE` | `/api/bruxos/{id}`                      | Deleta um bruxo específico pelo ID                                    |
