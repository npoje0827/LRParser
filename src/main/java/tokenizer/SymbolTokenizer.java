package tokenizer;

import java.util.*;

public class SymbolTokenizer {
    // Method to break up a String into a list of tokens to be parsed
    public List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < expr.length(); ++i) {
            if (expr.charAt(i) == 'i') {
                tokens.add(expr.substring(i, i+2));
                ++i;
            } else {
                tokens.add(String.valueOf(expr.charAt(i)));
            }
        }
        return tokens;
    }
}