# echo "You said: ${1}"
javac Main.java
java Main ${1} < "input_${1}.txt"