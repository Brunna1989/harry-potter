package test.model;

import com.br.harrypotter.model.Magia;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagiaTest {

    @Test
    void deveExecutarLancarFeiticoComSucesso() {
        Magia magia = new Magia() {
            @Override
            public String lancarFeitico() {
                return "Expecto Patronum!";
            }
        };

        String resultado = magia.lancarFeitico();
        assertEquals("Expecto Patronum!", resultado);
    }

    @Test
    void devePermitirUsoComExpressaoLambda() {
        Magia magiaLambda = () -> "Wingardium Leviosa!";
        assertEquals("Wingardium Leviosa!", magiaLambda.lancarFeitico());
    }

    @Test
    void deveGarantirQueMetodoNaoRetornaNulo() {
        Magia magia = () -> "Alohomora!";
        assertNotNull(magia.lancarFeitico());
    }
}
