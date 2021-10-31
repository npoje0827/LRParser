package tokenizer;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SymbolTokenizerTest {
    @Test
    public void tokenize_test(){
        SymbolTokenizer st = new SymbolTokenizer();
        String input = "id+(id+id)$";
        String[] expected = {"id", "+", "(", "id", "+", "id", ")", "$"};
        Assertions.assertArrayEquals(expected, st.tokenize(input).toArray());
    }
}
