# LR(1) PARSING

## File Structure
1. `q1/`
	- Contains the answer for the Question 1 (manually finding DFA, Parse Table, etc)
2. `ideaway/`
	- Contains the code for automatic LR1 Parser Generator for any Grammar
	- `results/`
		- Few Results of running JUnit Tests
	- `rsrs/`	
		- input files to be tested
	- `src/com/cdlabthree/`
		- all the source code and Unit Tests

## Running
0. Install IntelliJ Idea for best results
1. Download the code and import in Idea
2. Either run Unit Tests or Directly the Main function
	- **Using JUnit**:
		- Open the `AllTests` file
		- Run any `runx()` function (where `x` is the input file that is to be tested)
		- It will display the tests that passed
	- **Using Main function**
		- Just change the input argument in Main function and run
3. To provide new inputs
	- Add a `inputx.txt` file to `rsrs/` directory
	- First line should contain all `Non Terminals`
	- Second line should contain all the `Terminals`
	- Third line should contain the `Start Symbol` and space-separated `Input strings` to be tested
	- Following lines should contain the Grammar with the `Non Terminal` and space-separated `Productions`
4. To add new JUnit test
	- Just copy any of the `runx()` methods into the same `AllTests` Class
	- Change the file names
	- Change the `actualResults` list as needed
	- Run

## Output
The code outputs:
	1. The Left Factored Grammar
	2. The Non-Left-Recursive Grammar
	3. The First Sets of each Non Terminal
	4. The DFA of States and Items
	5. The Parse Table
	6. The Actions during the Parsing of String
	7. Whether String was Accepted or Rejected