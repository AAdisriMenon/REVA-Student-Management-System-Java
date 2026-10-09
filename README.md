# Student Management System (SMS)

Console-based Java OOP mini project for REVA University student records, grading, fees, and syllabus demonstrations.

| Field | Detail |
|---|---|
| **Course** | B.Sc. (BSTCs) Sem V — Java Programming (Units I & II) |
| **Author** | A Adisri Menon — SRN: `R24SA001` |
| **Institution** | REVA University, Bengaluru |
| **Environment** | Java SE (JDK 17+) |

## Features
- **Registration** — SRN, department, semester, credits, hostel, scholarship.
- **Grading** — 5-subject marks → percentage → grade (`O`/`A`/`B`/`C`/`D`/`F`).
- **Fee Engine** — tuition + hostel + amenities − scholarship discount.
- **Search & Sort** — SRN / partial-name search; bubble-sort by name, SRN, semester, fee.
- **Evaluator Demo** — 14 interactive OOP demonstrations covering all 21 rubric items.
## Workflow
<p align="center"><img src="docs/screenshots/workflow.png" alt="Workflow" width="800"></p>
## Output Screenshots
<p align="center"><img src="docs/screenshots/output-01-main-menu.png" alt="Main Menu" width="800"><br><sub>Main Menu</sub></p>
<p align="center"><img src="docs/screenshots/output-02-add-student.png" alt="Add Student — Input" width="800"><br><img src="docs/screenshots/output-03-add-student-result.png" alt="Add Student — Result &amp; Fee Breakdown" width="800"><br><sub>Add Student — Input &amp; Result</sub></p>
<p align="center"><img src="docs/screenshots/output-04-display-all.png" alt="Display All Students" width="800"><br><sub>Display All Students</sub></p>
<p align="center"><img src="docs/screenshots/output-05-search-by-srn.png" alt="Search by SRN" width="800"><br><sub>Search by SRN</sub></p>
<p align="center"><img src="docs/screenshots/output-06-search-by-name.png" alt="Search by Partial Name" width="800"><br><sub>Search by Partial Name</sub></p>
<p align="center"><img src="docs/screenshots/output-07-fee-payment.png" alt="Fee Payment" width="800"><br><sub>Fee Payment</sub></p>
<p align="center"><img src="docs/screenshots/output-08-remove-student.png" alt="Remove Student" width="800"><br><sub>Remove Student</sub></p>
<p align="center"><img src="docs/screenshots/output-09-sort-by-name.png" alt="Sort by Name" width="800"><br><img src="docs/screenshots/output-10-sort-by-name-cont.png" alt="Sort by Name (cont.)" width="800"><br><sub>Sort by Name</sub></p>
<p align="center"><img src="docs/screenshots/output-11-sort-by-srn.png" alt="Sort by SRN" width="800"><br><img src="docs/screenshots/output-12-sort-by-srn-cont.png" alt="Sort by SRN (cont.)" width="800"><br><sub>Sort by SRN</sub></p>
<p align="center"><img src="docs/screenshots/output-13-sort-by-semester.png" alt="Sort by Semester" width="800"><br><img src="docs/screenshots/output-14-sort-by-semester-cont.png" alt="Sort by Semester (cont.)" width="800"><br><sub>Sort by Semester</sub></p>
<p align="center"><img src="docs/screenshots/output-15-sort-by-fee.png" alt="Sort by Fee Due" width="800"><br><img src="docs/screenshots/output-16-sort-by-fee-cont.png" alt="Sort by Fee Due (cont.)" width="800"><br><sub>Sort by Fee Due</sub></p>
<p align="center"><img src="docs/screenshots/output-17-fee-analytics.png" alt="Fee Analytics" width="800"><br><sub>Fee Analytics</sub></p>
<p align="center"><img src="docs/screenshots/output-18-personnel-directory.png" alt="Personnel Directory — Students" width="800"><br><img src="docs/screenshots/output-19-personnel-directory-cont.png" alt="Personnel Directory — Faculty" width="800"><br><sub>Personnel Directory</sub></p>
<p align="center"><img src="docs/screenshots/output-20-oop-evaluator-menu.png" alt="OOP Evaluator Menu" width="800"><br><img src="docs/screenshots/output-21-oop-demo-data-types.png" alt="OOP Demo — Data Types" width="800"><br><img src="docs/screenshots/output-22-oop-demo-arrays.png" alt="OOP Demo — Arrays" width="800"><br><img src="docs/screenshots/output-23-oop-demo-strings.png" alt="OOP Demo — Strings" width="800"><br><sub>OOP Evaluator Demonstrations</sub></p>

## How to Run
```cmd
run.bat
javac -d bin -sourcepath src src/com/reva/sms/model/*.java src/com/reva/sms/service/*.java src/com/reva/sms/main/*.java
java -cp bin com.reva.sms.main.Main
Get-Content test_input.txt | java -cp bin com.reva.sms.main.Main
```

## Project Structure
```
JAVA/src/com/reva/sms/
  main/    Main.java              # Menu, input validation, demo dispatch
  model/   Department.java        # Enum: 11 departments, credit rates
           Faculty.java           # Person subclass
           Payable.java           # Interface extending Reportable
           Person.java            # Abstract base (id, name, age)
           Reportable.java        # Interface, default getReportHeader()
           Student.java           # Subclass implementing Payable
  service/ FeeCalculator.java     # Fee computation, operator demo
           GradeCalculator.java   # final utility class, grading
           StudentService.java    # CRUD, sort, search, analytics
  bin/  docs/  run.bat  test_input.txt  sample_output.txt
```
Hierarchy: `Reportable` ← `Payable` ← `Student`; `Person` ← `Student` / `Faculty`

## Syllabus Coverage — Unit I & II (21 Items)

| # | Topic | File(s) | Evidence |
|---|---|---|---|
| 1 | Encapsulation & Classes | see `src/` | Private fields, validated getters/setters |
| 2 | Data Types, Scope & Constants | `Student`, `Person` | All 8 primitives; static/instance/local |
| 3 | Operators & Precedence | `FeeCalculator`, `Student` | `demonstrateOperatorPrecedence()`; `<<` `\|=` `&` |
| 4 | Type Conversion & Casting | `GradeCalculator`, `Person` | `(double)total`; `short→double`; `(Person)obj` |
| 5 | Enumerated Types | `Department` | 11-constant enum, `fromString()` |
| 6 | Control Flow & Jumps | `Main`, `GradeCalculator` | `if-else-if`, `switch`, `while`, `do-while`, `break`/`continue` |
| 7 | Arrays | `Student`, `StudentService` | `int[]marks`; bubble sort; left-shift delete |
| 8 | Console I/O & Formatting | `Main`, `StudentService` | `Scanner`; `printf` tabular reports |
| 9 | Constructors & `this` | `Person`, `Student` | Overloaded constructors; `this(...)` chaining |
| 10 | Method Overloading | `GradeCalculator`, `FeeCalculator` | `calculateGrade`×3; `calculateSemesterFee`×3; `searchStudents`×2 |
| 11 | Parameter Passing | `StudentService` | `demonstrateParameterPassing()`: primitive vs reference |
| 12 | Static Members | `Person`, `Student`, `StudentService` | `generateNextIdSafe()`; counters; constants |
| 13 | Access Modifiers | all files | `public`, `protected`(`name`,`age`), `private` |
| 14 | `String` API | `StudentService`, `Department` | `trim()`, `contains()`, `equalsIgnoreCase()` |
| 15 | Inheritance, `super` & `final` | `Person`, `GradeCalculator` | `super(...)`; `final class`; `final getId()` |
| 16 | `Object` Class Overrides | `Person`, `Student` | `toString()`, SRN-keyed `equals()`, `hashCode()` |
| 17 | Dynamic Binding | `StudentService` | `demonstratePolymorphicDispatch()`; `instanceof` pattern |
| 18 | Abstract Classes | `Person` | `abstract class Person`; `abstract displayDetails()` |
| 19 | Interfaces & Default Methods | `Reportable`, `Student` | `default getReportHeader()`; `Payable p = student` |
| 20 | Interface Inheritance | `Payable` | `interface Payable extends Reportable` |
| 21 | Packages & CLASSPATH | `com.reva.sms.*` | `main`/`model`/`service`; `-d bin`/`-cp bin` |

## CLASSPATH
`javac -d bin` writes `.class` files preserving package dirs; `java -cp bin` resolves `com.reva.sms.main.Main` from that root.
