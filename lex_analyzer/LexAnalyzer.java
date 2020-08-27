import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Token {
    String type;
    String lexeme;

    Token(String type, String lexeme) {
        this.lexeme = lexeme;
        this.type = type;
    }
}

class Tokenizer {
    // Stores all tokens
    List<Token> allTokens = new ArrayList<>();
    String RELOPS, DIGITS, LETTERS;
    String theStatement;

    // Constructor
    Tokenizer(String theStatement) {
        prepareConstStrings();
        this.theStatement = theStatement;
        tokenizeTheStatment();
        printTheTokens();
    }

    public void printTheTokens() {
        System.out.println("Tokens for the statement [ " + theStatement + " ] are: ");
        for (Token tk : allTokens) {
            System.out.println("[ " + tk.type + ", " + tk.lexeme + " ]");
        }
    }

    public void prepareConstStrings() {
        RELOPS = "<>=";
        DIGITS = LETTERS = "";

        char seed = '0';
        for (int i = 0; i < 10; i++) {            
            DIGITS += seed;
            seed++;
        }

        seed = 'A';
        for (int i = 0; i < 26; i++) {
            LETTERS += seed;
            seed++;
        }

        seed = 'a';
        for (int i = 0; i < 26; i++) {
            LETTERS += seed;
            seed++;
        }

        // System.out.println("RELOPS: " + RELOPS);
        // System.out.println("DIGITS: " + DIGITS);
        // System.out.println("LETTERS: " + LETTERS);
    }

    public int checkKeywordToken(int position) {
        return position;
    }

    public int checkRelopToken(int position) {
        return position;
    }

    public int checkDigitToken(int position) {
        return position;
    }

    public int checkIdToken(int position) {

        int originalPosition = position;
        int state = 0;
        while(true) {
            switch(state) {
                case 0:
                    if (position + 1 == theStatement.length()) {
                        // no more chars present
                        allTokens.add(new Token("letter", theStatement.substring(originalPosition, position + 1)));
                        position++;
                        return position;
                    }

                    char ch = theStatement.charAt(position + 1);
                    if (LETTERS.indexOf(ch) >= 0 || DIGITS.indexOf(ch) >= 0) {
                        state = 0;
                        position++;
                    }
                    else {
                        Token tk = new Token("identifier", theStatement.substring(originalPosition, position + 1));
                        allTokens.add(tk);
                        position++;
                        return position;
                    }
                    break;
            }

            if (position >= theStatement.length()) {
                break;
            }
        }

        return position;
    }

    // Declares the specific character as an undefined token
    public int declareUndefined(int position) {
        allTokens.add(new Token("undefined", theStatement.substring(position, position + 1)));
        position++;
        return position;
    }

    // Given the sequence of characters, returns an array of tokens for the words within
    public void tokenizeTheStatment() {
        int stringLength = theStatement.length();
        int i, j;

        i = 0;
        while (i < stringLength) {
            char ch = theStatement.charAt(i);
            if (ch == ' ') {
                i++;
                continue;
            }

            int newPosition;

            // Check the transition diagram based on the char encountered
            if (RELOPS.indexOf(ch) >= 0) {
                newPosition = checkRelopToken(i);
                System.out.println("[ T ] Relop detected");
            }
            else if (DIGITS.indexOf(ch) >= 0) {
                newPosition = checkDigitToken(i);
                System.out.println("[ T ] Number detected");
            }
            else if (LETTERS.indexOf(ch) >= 0) {
                newPosition = checkKeywordToken(i);
                if (newPosition == i) {
                    newPosition = checkIdToken(i);
                    System.out.println("[ T ] Identifier detected");
                }
                else {
                    System.out.println("[ T ] Keyword detected");
                }
            }
            else {
                newPosition = declareUndefined(i);
            }

            i = newPosition;
        }
    }
}

public class LexAnalyzer {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        while(true) {
            // scan the input
            System.out.print("Your statment (Enter `!` (exclamation) to exit): ");
            String theStatement = sc.nextLine();

            // break case
            if (theStatement.equals("!")) {
                break;
            }

            // call the Tokenizer
            Tokenizer tokenizer = new Tokenizer(theStatement);
        }

        sc.close();
    }
}