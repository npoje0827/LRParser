public class Main {
    public static void main(String[] args) {
        LRParser parse = new LRParser();
        // Parsing input that should be accepted
        parse.parseInput("id+(id+id)$");

        // Parsing input that should result in an error
        parse.parseInput("(id*id)id+id$");
    }
}
