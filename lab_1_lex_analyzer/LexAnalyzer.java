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

    public boolean isLastElement(int position) {
        return (position + 1 == theStatement.length());
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
        // if - then - else
        if (position + 1 == theStatement.length()) {
            return position;
        }
        
        // checking `if`
        if (theStatement.substring(position, position + 2).equals("if")) {
            if (position + 2 == theStatement.length()) {
                // yes, a keyword
                allTokens.add(new Token("keyword_if", "if"));
                position += 2;
                return position;
            }
            else {
                char ch = theStatement.charAt(position + 2);
                if (LETTERS.indexOf(ch) >= 0) {
                    // no, it is an identifier
                    return position;
                }
                else {
                    // yes, a keyword
                    allTokens.add(new Token("keyword_if", "if"));
                    position += 2;
                    return position;
                }
            }
        }

        // checking `then` and `else`
        if (position + 4 > theStatement.length()) {
            return position;
        }

        // checking `then`
        String strThenOrElse = theStatement.substring(position, position + 4);
        if (strThenOrElse.equals("then") || strThenOrElse.equals("else")) {
            if (position + 4 == theStatement.length()) {
                // yes, a keyword
                if (strThenOrElse.equals("then")) {
                    allTokens.add(new Token("keyword_then", "then"));
                }
                else {
                    allTokens.add(new Token("keyword_else", "else"));
                }
                position += 4;
                return position;
            }
            else {
                char ch = theStatement.charAt(position + 4);
                if (LETTERS.indexOf(ch) >= 0) {
                    // no, it is an identifier
                    return position;
                }
                else {
                    // yes, a keyword
                    if (strThenOrElse.equals("then")) {
                        allTokens.add(new Token("keyword_then", "then"));
                    }
                    else {
                        allTokens.add(new Token("keyword_else", "else"));
                    }
                    position += 4;
                    return position;
                }
            }
        }
        return position;
    }

    public int checkRelopToken(int position) {
        char ch = theStatement.charAt(position);

        int state = 0;
        switch (ch) {
            case '<':
                state = 1;
                break;
            case '>':
                state = 2;
                break;
            case '=':
                state = 3;
                break;
        }

        while(true) {
            switch(state) {
                case 1:
                    if (position + 1 == theStatement.length()) {
                        allTokens.add(new Token("relop_LT", "<"));
                        position++;
                        return position;
                    }
                    else {
                        char chnxt = theStatement.charAt(position + 1);
                        if (chnxt == '>') {
                            // <>
                            allTokens.add(new Token("relop_NE", "<>"));
                            position += 2;
                            return position;
                        }
                        else if (chnxt == '=') {
                            // <=
                            allTokens.add(new Token("relop_LE", "<="));
                            position += 2;
                            return position;
                        }
                        else {
                            // <
                            allTokens.add(new Token("relop_LT", "<"));
                            position++;
                            return position;
                        }
                    }
                    // break;
                case 2:
                    if (position + 1 == theStatement.length()) {
                        allTokens.add(new Token("relop_GT", ">"));
                        position++;
                        return position;
                    }
                    else {
                        char chnxt = theStatement.charAt(position + 1);
                        if (chnxt == '=') {
                            allTokens.add(new Token("relop_GE", ">="));
                            position += 2;
                            return position;
                        }
                        else {
                            allTokens.add(new Token("relop_GT", ">"));
                            position++;
                            return position;
                        }
                    }
                    // break;
                case 3:
                    allTokens.add(new Token("relop_EQ", "="));
                    position++;
                    return position;
                    // break;
            }
        }
    }

    public int checkDigitToken(int position) {
        int state = 1;
        int originalPosition = position;
        while (true) {
            switch(state) {
                case 1:
                    if (isLastElement(position)) {
                        position++;
                        state = 7;
                    }
                    else {
                        char ch = theStatement.charAt(position + 1);
                        if (DIGITS.indexOf(ch) >= 0) {
                            position++;
                            state = 1;
                        }
                        else if (ch == '.') {
                            position++;
                            state = 2;
                        }
                        else if (ch == 'E') {
                            position++;
                            state = 4;
                        }
                        else {
                            position++;
                            state = 7;
                        }
                    }
                break;
                case 2:
                    if (isLastElement(position)) {
                        state = 7;
                    }
                    else {
                        char ch = theStatement.charAt(position + 1);
                        if (DIGITS.indexOf(ch) >= 0) {
                            position++;
                            state = 3;
                        }
                        else {
                            state = 7;
                        }
                    }
                break;
                case 3:
                    if (isLastElement(position)) {
                        position++;
                        state = 7;
                    }
                    else {
                        char ch = theStatement.charAt(position + 1);
                        if (DIGITS.indexOf(ch) >= 0) {
                            position++;
                            state = 3;
                        }
                        else if (ch == 'E') {
                            position++;
                            state = 4;
                        }
                        else {
                            position++;
                            state = 7;
                        }
                    }
                break;
                case 4:
                    if (isLastElement(position)) {
                        state = 7;
                    }
                    else {
                        char ch = theStatement.charAt(position + 1);
                        if (DIGITS.indexOf(ch) >= 0) {
                            position++;
                            state = 6;
                        }
                        else if (ch == '+' || ch == '-') {
                            position++;
                            state = 5;
                        }
                        else {
                            state = 7;
                        }
                    }
                break;
                case 5:
                    if (isLastElement(position)) {
                        position--;
                        state = 7;
                    }
                    else {
                        char ch = theStatement.charAt(position + 1);
                        if (DIGITS.indexOf(ch) >= 0) {
                            position++;
                            state = 6;
                        }
                        else {
                            position--;
                            state = 7;
                        }
                    }
                break;
                case 6:
                    if (isLastElement(position)) {
                        position++;
                        state = 7;
                    }
                    else {
                        char ch = theStatement.charAt(position + 1);
                        if (DIGITS.indexOf(ch) >= 0) {
                            position++;
                            state = 6;
                        }
                        else {
                            position++;
                            state = 7;
                        }
                    }
                break;
                case 7:
                    String theNumber = theStatement.substring(originalPosition, position);
                    allTokens.add(new Token("number", theNumber));
                    return position;
            }
        }
    }

    public int checkIdToken(int position) {
        int originalPosition = position;
        int state = 0;
        while(true) {
            switch(state) {
                case 0:
                    if (position + 1 == theStatement.length()) {
                        // no more chars present
                        allTokens.add(new Token("identifier", theStatement.substring(originalPosition, position + 1)));
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

        int iterator = 1;

        while(true) {
            System.out.println("\n-------- RUN #" + (iterator++) + " --------");
            // scan the input
            System.out.print("Your statment (Enter `!` (exclamation) to exit): ");
            String theStatement = sc.nextLine();
            System.out.println();

            // break case
            if (theStatement.equals("!")) {
                break;
            }

            // call the Tokenizer
            Tokenizer tokenizer = new Tokenizer(theStatement);
            System.out.println("====================================");
        }

        sc.close();
    }
}