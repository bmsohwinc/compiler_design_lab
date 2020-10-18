#include  <bits/stdc++.h>
using namespace std;


// Some Defs
#define MAX_RECUR 20


// Some Global Vars
vector<char> Terms;                                 // all Terminals
vector<char> NonTerms;                              // all Non Terminals
char StartSymbol;                                   // the Start Symbol
map<char, vector<string>> Grammar;                  // the Grammar
vector<char> unusedSymbols;                         // all Unused Symbols
map<char, set<char>> Firsts;                        // the Firsts
map<char, bool> visited;                            // check for visited symbols in `Firsts` finding


// Some Functions
void getAvlNTerms();
void printGram();
void getTerms();
void getNonTerms();
void getGrammar();
void warner(string);
void inputTheGrammar();
void leftFactorise();
void findFirsts();
void findFirstsUtil();

int main() {

    // 1. Input
    inputTheGrammar();

    // 2. Firsts
    findFirstsUtil();

    return 0;
}

// Take Grammar input first
void inputTheGrammar() {
    getTerms();
    getNonTerms();
    getGrammar();
    printGram();
}


// Find Firsts for all Non Terminals
void findFirstsUtil() {

    // start from first symbol
    // mark it visited
    // for each production
        // for each symbol of that prod
            // get the char
            // if char is Terminal or it is epsilon
                // add it to the set
            // elsif char is Non Terminal (complex case)
                // if char == sameSymbol
                    // continue; as it doesn't really matter
                // elsif 



}


// Get the Terminals
void getTerms() {
    int tempsize, i;
    string tempvar;
    cout << "Enter number of Terminals and them: ";
    cin >> tempsize;
    for (i = 0; i < tempsize; i++) {
        cin >> tempvar;
        Terms.push_back(tempvar[0]);
    }
}


// Get the Non Terminals
void getNonTerms() {
    int tempsize, i;
    string tempvar;
    cout << "Enter number of Non-Terminals and them: ";
    cin >> tempsize;
    for (i = 0; i < tempsize; i++) {
        cin >> tempvar;
        NonTerms.push_back(tempvar[0]);
    }

    cout << "Enter the start symbol: ";
    string temp;
    cin >> temp;
    StartSymbol = temp[0];
}


// Stores the unused symbols
void getAvlNTerms() {
    map<char, bool> mp;
    for (int i = 0; i < 26; i++) {
        mp[ (char)('A' + i)] = 1;
    }

    for (auto nterm : NonTerms) {
        mp[nterm] = 0;
    }

    for (auto nterm : mp) {
        if (nterm.second) {
            unusedSymbols.push_back(nterm.first);
        }
    }
}


// Get the Grammar's productions
void getGrammar() {
    cout << "Enter the Grammar now.\n";
    for (int i = 0; i < NonTerms.size(); i++) {
        cout << "Enter the Non Terminal: ";
        string nt;
        cin >> nt;
        cout << "Enter the productions (-1 to exit): ";
        int j = 0;
        while (1) {
            string prod;
            cin >> prod;

            if (prod == "-1") {
                break;
            }

            Grammar[nt[0]].push_back(prod);
            if (j++ == MAX_RECUR) {
                warner("EXITED FROM AN INFINITE LOOP");
            }
        }
    }
}


// Pretty Print the Grammar
void printGram() {
    cout << "\n\n";
    for (auto row : Grammar) {
        cout << row.first << "\t->\t";
        for (auto col : row.second) {
            cout << col << ", ";
        }
        cout << "\n";
    }
    cout << "\n";
}


// Signal the passed Warning
void warner(string s) {
    cout << "\n\n****************************************\n";
    cout << s;
    cout << "\n****************************************\n";
    exit(EXIT_FAILURE);
}

