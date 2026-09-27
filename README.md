# Student Management System (SMS)
### Object-Oriented Programming in Java — Mini Project
**Course:** B.Sc. (BSTCs), Semester V — Java Programming (Units I & II)  
**Author:** A Adisri Menon (SRN: `R24SA001`)  
**Institution:** REVA University, Bengaluru  
**Environment:** Java SE (JDK 17+)  

---

## 1. Project Overview & Key Features

The **Student Management System (SMS)** is an in-memory, console-based Java application built to manage university student records, fee assessments, academic evaluations, and institutional reporting. It is structured to demonstrate fundamental and advanced Object-Oriented Programming (OOP) concepts in Java.

### Key Features
- **Student Registration:** Captures student identity, academic department, semester, course credits, hostel residency, and scholarship percentage.
- **Academic Grading:** Evaluates 5-subject core marks, computing percentage and letter grades (`O`, `A`, `B`, `C`, `D`, `F`) according to REVA University standards.
- **Fee Assessment Engine:** Computes net payable semester fees based on credit load, department credit rates, campus amenities, hostel charges, and scholarship deductions.
- **Record Search & Query:** Fast searching by exact SRN or partial student name.
- **In-Place Record Sorting:** Bubble sort algorithm organizing student records alphabetically by name.
- **Department Fee Analytics:** Summarizes total enrolled students, cleared vs. outstanding balances, gross revenue due, and average fee per student.
- **Polymorphic University Directory:** Dynamic method dispatch over heterogeneous arrays containing both `Student` and `Faculty` instances, with `instanceof` type checking.
- **OOP Syllabus Demonstrations (Evaluator Mode):** Dedicated menu option demonstrating pass-by-value semantics, operator precedence, bitwise masking, method overloading, interface reference access, and `Object` class method contracts.

---

## 2. Architecture & Package Structure

The project enforces separation of concerns across three packages:

```
student-management-system/
├── bin/                                         # Compiled bytecode (.class files)
├── src/                                         # Source code root
│   └── com/
│       └── reva/
│           └── sms/
│               ├── main/
│               │   └── Main.java                # Console UI, menu loop, and input validation
│               ├── model/
│               │   ├── Department.java          # Department enum with credit tuition rates
│               │   ├── Faculty.java             # Concrete Person subclass
│               │   ├── Payable.java             # Billable entity interface (extends Reportable)
│               │   ├── Person.java              # Abstract base class with encapsulated identity
│               │   ├── Reportable.java          # Contract interface with default reporting header
│               │   └── Student.java             # Concrete subclass implementing Payable
│               └── service/
│                   ├── FeeCalculator.java       # Tuition, hostel, amenities & scholarship logic
│                   ├── GradeCalculator.java     # Final utility class for grading
│                   └── StudentService.java      # Fixed-capacity storage, CRUD, search, sorting
├── sample_output.txt                            # Captured console execution transcript
├── test_input.txt                               # Scripted input for end-to-end execution
├── run.bat                                      # Windows compilation and launch script
└── README.md                                    # Project documentation and syllabus matrix
```

### Class and Interface Hierarchy
```
           <<interface>>
            Reportable
                ▲
                │ extends
           <<interface>>
             Payable
                ▲
                │ implements
   ┌────────────┴────────────┐
   │                         │
[Person] (abstract)          │
   ▲                         │
   ├── [Student] ────────────┘ (extends Person, implements Payable)
   └── [Faculty]               (extends Person)
```

---

## 3. Mandatory 21-Item Rubric Traceability Matrix

| # | Java / OOP Rubric Requirement | Implementation Location | Description |
|---|---|---|---|
| **1** | Encapsulation & Classes (3–4+ classes) | `Person`, `Student`, `Faculty`, `Department`, `FeeCalculator`, `GradeCalculator`, `StudentService`, `Main` | Private fields, protected inheritance fields, and controlled public getters/setters with validation. |
| **2** | Data Types, Scope & Constants | `Student.java`, `FeeCalculator.java`, `Person.java` | Primitives (`byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`), instance/static/local scope, and `public static final` constants. |
| **3** | Operators & Precedence | `FeeCalculator.java`, `Student.java`, `Main.java` | Explicit operator precedence in `FeeCalculator.demonstrateOperatorPrecedence()` (`10 + 5 * 2 = 20` vs `(10 + 5) * 2 = 30`), bitwise operators (`&`, `|`, `^`, `~`, `<<`, `>>`), relational, logical, ternary, and compound assignment. |
| **4** | Type Conversion & Casting | `GradeCalculator.java`, `FeeCalculator.java`, `StudentService.java` | Implicit widening (`short` to `double` in multiplication) and explicit narrowing (`(double) total / max`, `(byte) ...`, `(short) ...`, `(Student) person`). |
| **5** | Enumerated Types (`enum`) | `Department.java` | `enum Department` containing 11 university departments with codes, names, credit rates, constructor, getters, and static `fromString()`. |
| **6** | Control Flow Statements | `Main.java`, `StudentService.java`, `GradeCalculator.java` | Selection (`if-else-if`, `switch-case`), loops (`while`, `do-while` for input validation, `for`, enhanced `for-each`), and jump statements (`break`, `continue`, `return`). |
| **7** | Arrays & Multi-Element Processing | `Student.java`, `StudentService.java`, `GradeCalculator.java` | 5-subject `int[] marks`, fixed-size `Student[]` array, in-place Bubble Sort, array left-shift on deletion, and defensive copying via `Arrays.copyOf()`. |
| **8** | Console I/O & Formatted Output | `Main.java`, `StudentService.java` | Interactive `Scanner` reading strings, integers, doubles, and booleans; tabular reporting formatted via `System.out.printf`. |
| **9** | Constructors & `this` Keyword | `Person.java`, `Student.java`, `Faculty.java` | Parameterized constructors, default constructors, constructor chaining via `this(...)`, and disambiguating field shadowing with `this.field`. |
| **10** | Method Overloading | `GradeCalculator.java`, `FeeCalculator.java`, `StudentService.java` | Compile-time polymorphism with overloaded signatures: `calculateGrade(double)`, `(int[])`, `(int, int)`; `calculateSemesterFee(short, Department)`, `(short, Department, boolean, float)`, `(Student)`; `searchStudents(Department)`, `(String)`. |
| **11** | Parameter Passing (Pass-by-Value) | `StudentService.demonstrateParameterPassing()` | Demonstrates pass-by-value for primitives (caller value unchanged) versus object references (state mutated through reference copy). |
| **12** | Static Members | `Person.java`, `Student.java`, `GradeCalculator.java`, `StudentService.java` | Static counters (`totalPersonInstances`, `studentCounter`), static utility methods (`calculateGrade`, `generateNextIdSafe`), and static constants. |
| **13** | Access Control Modifiers | All source files | Systematic application of `public`, `protected` (inheritance access for `name`, `age`), `private`, and default package-private visibility. |
| **14** | `java.lang.String` Class API | `StudentService.java`, `Department.java`, `Main.java` | Multiple `String` methods exercised: `length()`, `toUpperCase()`, `toLowerCase()`, `trim()`, `contains()`, `substring()`, `equalsIgnoreCase()`, `compareToIgnoreCase()`. |
| **15** | Inheritance, `super` & `final` | `Person.java`, `Student.java`, `Faculty.java`, `GradeCalculator.java` | Hierarchical inheritance (`Person` extended by `Student` and `Faculty`), `super(...)` constructor calls, `super.toString()`, `final` class `GradeCalculator`, and `final` method `getId()`. |
| **16** | `Object` Class Method Overrides | `Person.java`, `Student.java`, `Faculty.java` | Custom overrides of `toString()`, `equals(Object)` (SRN-based for `Student`, ID-based for `Person`), and `hashCode()`. |
| **17** | Dynamic Method Binding | `StudentService.demonstratePolymorphicDispatch()` | Runtime polymorphism calling overridden `displayDetails()` across heterogeneous `Person[]` arrays, with `instanceof` pattern matching and downcasting. |
| **18** | Abstract Classes & Methods | `Person.java` | `abstract class Person` declaring abstract contract method `public abstract void displayDetails();` implemented by derived subclasses. |
| **19** | Interfaces & Default Methods | `Reportable.java`, `Payable.java`, `Student.java` | Interface definition with constants, abstract methods, `default String getReportHeader()`, and polymorphic reference invocation (`Payable p = student; p.payFee(amt);`). |
| **20** | Interface Inheritance | `Payable.java` | `public interface Payable extends Reportable`, demonstrating multiple inheritance of type/behavior. |
| **21** | Packages & CLASSPATH | `com.reva.sms.*`, `run.bat` | Modular package structure across `main`, `model`, and `service`, with explicit classpath configuration. |

---

## 4. Compilation and Execution

### Prerequisites
- Java Development Kit (JDK 17 or higher)
- Windows Command Prompt or PowerShell

### Method 1: Using the Batch Script (Recommended)
```cmd
run.bat
```

### Method 2: Manual Terminal Commands
From the project root directory:

1. **Compile all packages into the `bin` directory:**
   ```powershell
   if (!(Test-Path bin)) { New-Item -ItemType Directory bin }
   javac -d bin -sourcepath src src/com/reva/sms/model/*.java src/com/reva/sms/service/*.java src/com/reva/sms/main/*.java
   ```

2. **Run the application:**
   ```powershell
   java -cp bin com.reva.sms.main.Main
   ```

### Method 3: Non-Interactive Verification Script
To execute the automated end-to-end test suite and view output:
```powershell
Get-Content test_input.txt | java -cp bin com.reva.sms.main.Main
```

---

## 5. Understanding the Java CLASSPATH

The **CLASSPATH** specifies the locations where the Java Virtual Machine (JVM) and compiler (`javac`) search for compiled `.class` files.
- **Directory Hierarchy:** In Java, package declarations correspond directly to filesystem folders (e.g., `package com.reva.sms.model;` maps to `com/reva/sms/model/Student.class`).
- **Compilation (`-d bin`):** The `-d` flag directs `javac` to place generated bytecode into the `bin/` directory while automatically creating the required package subdirectories.
- **Execution (`-cp bin`):** The `-cp` flag instructs the JVM to use `bin/` as the root of the classpath so that fully qualified class names like `com.reva.sms.main.Main` can be resolved at runtime.
