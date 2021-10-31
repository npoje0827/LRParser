import tokenizer.SymbolTokenizer;
import java.util.List;
import java.util.Stack;

public class LRParser {
    // 2D Array to store parse table
    public String[][] parseTable = {
            {" ", "id", "+", "*", "(", ")", "$", "E", "T", "F"},
            {"0", "s5", " ", " ", "s4", " ", " ", "1", "2", "3"},
            {"1",  " ", "s6", " ", " ", " ", "accept", " ", " ", " "},
            {"2", " ", "r2", "s7", " ", "r2", "r2", " ", " ", " "},
            {"3", " ", "r4", "r4", " ", "r4", "r4", " ", " ", " "},
            {"4", "s5", " ", " ", "s4", " ", " ", "8", "2", "3"},
            {"5", " ", "r6", "r6", " ", "r6", "r6", " ", " ", " "},
            {"6", "s5", " ", " ", "s4", " ", " ", " ", "9", "3"},
            {"7", "s5", " ", " ", "s4", " ", " ", " ", " ", "10"},
            {"8", " ", "s6", " ", " ", "s11", " ", " ", " ", " "},
            {"9", " ", "r1", "s7", " ", "r1", "r1", " ", " ", " "},
            {"10", " ", "r3", "r3", " ", "r3", "r3", " ", " ", " "},
            {"11", " ", "r5", "r5", " ", "r5", "r5", " ", " ", " "}
    };

    // Array to hold grammar rules
    public String[] reduceRules = {"E->E+T", "E->T", "T->T*F", "T->F", "F->(E)", "F->id"};

    // Initializing stack to hold symbols and current state
    public Stack<String> stackParse = new Stack<>();

    // Initializing String to hold current action
    public String currAction = "";

    public void parseInput(String expr) throws IllegalArgumentException {
        // Using helper class SymbolTokenizer to parse input into tokens
        SymbolTokenizer st = new SymbolTokenizer();
        List<String> tokens = st.tokenize(expr);

        // Setting initial state to 0
        stackParse.push("0");
        int index = 0;
        System.out.println("Input               Action");
        // Looping through all tokens from input
        while (index < tokens.size()) {
            currAction = action(stackParse.peek(), tokens.get(index));
            // When current action is to shift
            if (currAction.charAt(0) == 's'){
                stackParse.push(tokens.get(index));
                stackParse.push(currAction.substring(1));
                printStep(tokens, index, currAction);
                ++index;

            }
            // When current action is to reduce
            else if (currAction.charAt(0) == 'r'){
                // Finds grammar rule corresponding to the current reduce action
                String str = reduceRules[Integer.parseInt(String.valueOf(currAction.charAt(1))) - 1];

                // Finding length for RHS of grammar rule to determine amount of symbols to reduce
                int len = str.substring(3).length();
                if (str.substring(3).equals("id")){
                    --len;
                }
                // Pops off state and symbol for number of symbols needed to be reduced
                for (int i = 0; i < len; ++i){
                    stackParse.pop();
                    stackParse.pop();
                }
                // Go to table
                String newState = action(stackParse.peek(), String.valueOf(str.charAt(0)));
                stackParse.push(String.valueOf(str.charAt(0)));
                stackParse.push(newState);
                printStep(tokens, index, currAction);
            }
            // Breaks out of loop once current action is to accept
            else if (currAction.equals("accept")){
                currAction = "Accept";
                printStep(tokens, index, currAction);
                break;
            }
            // Exception thrown if no action is found on parse table and input is invalid
            else {
                throw new IllegalArgumentException("Error - No action was found on parse table. Input entered is invalid.");
            }
        }

    }
    // Traverses parse table to find corresponding action for state and symbol provided
    public String action(String state, String input){
        for (int i = 1; i < 13; ++i){
            if (parseTable[i][0].equals(state)){
                for (int j = 1; j < 10; ++j){
                    if (parseTable[0][j].equals(input)){
                        return parseTable[i][j];
                    }
                }
            }
        }
        return ("Action not found");
    }
    // Helper method to print remaining input and current action to be performed
    public void printStep(List<String> tokens, int index, String currAction){
        String remainingInput = String.join("", tokens.subList(index, tokens.size()));
        System.out.printf("%1$-20s", remainingInput);
        System.out.printf("%-10s", currAction);
        System.out.println();
    }
}
