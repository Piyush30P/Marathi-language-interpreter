Here's a detailed explanation of how your Marathi language interpreter works, along with a breakdown of each sample code provided. I'll explain how the interpreter processes each case, and you can use this as part of your documentation to explain the internal workings.

### Working of the Marathi Language Interpreter

1. **Tokenization:**
   - The interpreter first reads the input code and breaks it down into tokens. Tokens are the smallest units of a program (e.g., keywords, variables, operators, etc.).
   - For example, `He aahe`, `x`, `=`, and `15` are tokens in the statement `He aahe x = 15;`.

2. **Parsing**:
   - After tokenization, the interpreter parses these tokens to create an Abstract Syntax Tree (AST). The AST represents the hierarchical structure of the program, showing the relationships between different parts of the code.
   - For instance, an `If` statement will be parsed into a condition node and two branches: one for the "then" clause and another for the "else" clause.

3. **Execution**:
   - The interpreter traverses the AST to execute the program. It processes each node (e.g., variable declarations, expressions, control structures) and evaluates them step by step.
   - Functions are stored in memory and can be called when needed, executing the body of the function each time.

### **Installation Guide for the Marathi Language Interpreter**

#### **Prerequisites:**
- **Java Development Kit (JDK) installed**: Ensure you have JDK installed on your system. You can download it from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [OpenJDK](https://openjdk.java.net/).
- **Set Up JAVA_HOME**: Make sure `JAVA_HOME` is correctly configured, and Java is added to your system's `PATH` environment variable.

#### **Clone the Repository:**
```bash
git clone https://github.com/Piyush30P/Marathi-language-interpreter.git
cd Marathi-language-interpreter
```

#### **Compile the Project:**
```bash
javac -d bin/ src/*.java
``` 

By following these steps, you will successfully set up and compile the Marathi Language Interpreter on your system. Once compiled, you can proceed to run the interpreter and start coding using Marathi syntax.
### Sample Code and Execution Explanation:

#### **Case 1: Conditional Statements and Variable Declaration**
<img width="670" alt="{7E4486B4-64D3-4BC4-8C78-35E16157FA33}" src="https://github.com/user-attachments/assets/e22edb9c-6e12-4ae3-8d74-e99422861e49">

```marathi


He aahe x = 15;  
Jar (x < 10) {
    Chapa("X is less than 10");
} Nahitar {
    Chapa("X is greater or equal to 10");
}

He aahe y = 9;
Chapa(y);
Chapa("Hello, World!");
```
**Explanation**:
- Declares a variable `x` with a value of `15`.
- Checks if `x` is less than `10` using an `If` statement. Since `x` is `15`, it executes the `Nahitar` (else) branch.
- Declares another variable `y` and prints its value.
- Prints a string: "Hello, World!".

**Output**:
```
X is greater or equal to 10
9
Hello, World!
```



#### **Case 2: Basic Arithmetic Operations**
<img width="666" alt="{C5979055-F8CC-41E1-8719-6E25832CF5D2}" src="https://github.com/user-attachments/assets/482b2c62-fa59-4d2b-938f-1895e2710fca">

```marathi

He aahe a = 6;
He aahe b = 6;
Chapa(a + b);
Chapa(a);
Chapa("Hello ji");
```
**Explanation**:
- Declares two variables `a` and `b`, both set to `6`.
- Prints the result of `a + b`, which is `12`.
- Prints the value of `a`, which is `6`.
- Prints a string: "Hello ji".

**Output**:
```
12
6
Hello ji
```



#### **Functions: Single and Multiple Parameters**
<img width="671" alt="{CA265878-C5FA-4FBA-BA08-222022762A72}" src="https://github.com/user-attachments/assets/090b63fb-d19d-4b0c-bffb-b9ac0a0f9b93">

```marathi
Karya greet(name) {
    Chapa("Hello " + name);
}

greet("Utkarsh");


```
**Explanation**:
- Defines a function `greet` that takes a single parameter and prints a greeting.
- Calls `greet` with `"Utkarsh"` as an argument, printing a personalized message.
- Defines another `greet` function with two parameters to provide more information, including a calculation.
- Calls the second `greet` function with `"Utkarsh"` and `21`.

**Output**:
```
Hello Utkarsh
Hello Utkarsh, you are 21 years old.
Next year you will be 22
```


#### **While Loop**
<img width="671" alt="{31F2459F-2C46-4EC8-8520-A8409A562067}" src="https://github.com/user-attachments/assets/dd66c88a-f1dd-42cd-b2a3-f14046d0eb70">

```marathi
He aahe x = 0;

joparyant (x < 5) {
    Chapa(x);
    x = x + 1;
}

Chapa("Loop finished!");
```
**Explanation**:
- Initializes `x` to `0`.
- Uses a `while` loop (`joparyant`) to print `x` until `x` is less than `5`, incrementing `x` by `1` on each iteration.
- After exiting the loop, it prints "Loop finished!".

**Output**:
```
0
1
2
3
4
Loop finished!
```





### General Workflow:
1. **Run the Interpreter GUI**:
   ```
   java -cp bin/ MarathiLangEditor
   ```
2. **Write Your Code**: In the code editor, write or paste any of the example codes.
3. **Execute the Code**: Click the **Run** button, and the interpreter will parse, analyze, and display the results in the output window.


