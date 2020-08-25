# CONVERT REGEX TO NFA
## Also Optimize and Simulate the NFA
---
### **Language**
- `Python 3`
### **File Description**
1. `Regex2DFA.py` 
    - the main file having the conversion code
2. `trial/` 
    - a scrap folder
### **Execution**
- Just run : `python3 Regex2DFA.py` in a terminal
- You will be prompted to enter a regular expression
- Once entered, the code will 
    - derive the postfix notation
    - construct the epsilon-NFA (***Thompson construction***)
    - construct the DFA (***epsilon closures***)
    - print all of them
- Then, you will be prompted to enter a word to simulate over the DFA
- You can enter `-1` to exit the simulation prompt
- You can press `Ctrl+C` to exit the regex prompt
## References:
1. [Regex to Postfix](https://medium.com/@gregorycernera/converting-regular-expressions-to-postfix-notation-with-the-shunting-yard-algorithm-63d22ea1cf88)
2. [Postfix to NFA](https://medium.com/swlh/visualizing-thompsons-construction-algorithm-for-nfas-step-by-step-f92ef378581b)