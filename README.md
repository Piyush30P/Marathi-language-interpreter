# Marathi Language Interpreter

A custom programming language interpreter that allows coding in **Marathi language syntax**, built with Java.

## ⚡ Quick Start

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Git

### Installation

```bash
git clone https://github.com/Piyush30P/Marathi-language-interpreter.git
cd Marathi-language-interpreter
```

### Compile & Run

```bash
# Compile all Java files
javac -d bin/ src/*.java

# Run GUI Editor
java -cp bin/ MarathiLangEditor

# Run with sample file
java -cp bin/ Main
```

## 🚀 Language Features

### Variable Declaration

```marathi
He aahe x = 15;        // Number
He aahe name = "John"; // String
He aahe pi = 3.14;     // Float
```

### Conditional Statements

```marathi
Jar (x < 10) {
    Chapa("Small number");
} Nahitar {
    Chapa("Large number");
}
```

### Loops

```marathi
// While loop
joparyant (x < 5) {
    Chapa(x);
    x = x + 1;
}
```

### Functions

```marathi
Karya greet(name) {
    Chapa("Hello " + name);
}
greet("World");
```

### Print Statements

```marathi
Chapa("Hello, World!");
Chapa(x + y);
```

## 📝 Marathi Keywords

| English              | Marathi     | Usage                       |
| -------------------- | ----------- | --------------------------- |
| Variable declaration | `He aahe`   | `He aahe x = 5;`            |
| If                   | `Jar`       | `Jar (condition) { }`       |
| Else                 | `Nahitar`   | `Nahitar { }`               |
| While                | `joparyant` | `joparyant (condition) { }` |
| Function             | `Karya`     | `Karya functionName() { }`  |
| Print                | `Chapa`     | `Chapa("text");`            |

## 🔧 Architecture

The interpreter follows a **3-stage compilation pipeline**:

1. **Lexical Analysis** → `MarathiTokenizer.java`
2. **Syntax Analysis** → `MarathiParser.java`
3. **Execution** → `MarathiInterpreter.java`

## 📁 Project Structure

```
src/
├── Main.java                    # CLI entry point
├── MarathiLangEditor.java       # GUI code editor
├── MarathiTokenizer.java        # Lexical analyzer
├── MarathiParser.java           # Syntax parser
├── MarathiInterpreter.java      # Code executor
├── Token.java                   # Token data structure
├── ASTNode.java                 # AST base interface
└── [AST Node Classes]           # Specific AST implementations
```

## 🎯 Example Programs

### Basic Arithmetic

```marathi
He aahe a = 6;
He aahe b = 4;
Chapa(a + b);    // Output: 10
```

### Conditional Logic

```marathi
He aahe age = 18;
Jar (age >= 18) {
    Chapa("Adult");
} Nahitar {
    Chapa("Minor");
}
```

### Function with Parameters

```marathi
Karya add(x, y) {
    Chapa(x + y);
}
add(5, 3);       // Output: 8
```

---

**Made with ❤️ for Marathi programming enthusiasts**
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

````



#### **Case 2: Basic Arithmetic Operations**
<img width="666" alt="{C5979055-F8CC-41E1-8719-6E25832CF5D2}" src="https://github.com/user-attachments/assets/482b2c62-fa59-4d2b-938f-1895e2710fca">

```marathi

He aahe a = 6;
He aahe b = 6;
Chapa(a + b);
Chapa(a);
Chapa("Hello ji");
````

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
