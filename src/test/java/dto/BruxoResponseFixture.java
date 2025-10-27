package dto;

import com.br.harrypotter.dto.BruxoResponseDTO;

public class BruxoResponseFixture {

    public static BruxoResponseDTO criarBruxoResponsePadrao() {
        return new BruxoResponseDTO(1L, "Harry Potter", "Grifinória", "Expelliarmus lançado com sucesso!");
    }

    public static BruxoResponseDTO criarBruxoResponsePersonalizado(Long id, String nome, String casa, String mensagemFeitico) {
        return new BruxoResponseDTO(id, nome, casa, mensagemFeitico);
    }
}