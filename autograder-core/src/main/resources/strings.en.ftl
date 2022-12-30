# Statuses
status-compiling = Compiling
status-spotbugs = Running SpotBugs
status-pmd = Running PMD
status-cpd = Running Copy/Paste-Detection
status-model = Building the code model
status-docker = Building the Docker image
status-tests = Executing tests
status-integrated = Running integrated analysis

# Linters
linter-cpd = Copy/Paste-Detection
linter-spotbugs = SpotBugs
linter-pmd = PMD
linter-integrated = Integrated Analysis

# CPD
duplicate-code = Duplicated code ({$lines}): {$first-path}:{$first-start}-{$first-end} and {$second-path}:{$second-start}-{$second-end}

# API
is-empty-reimplemented-desc = Use isEmpty() instead of size() == 0 or similar code
is-empty-reimplemented-exp = Use isEmpty()

old-collection-desc = Don't use Java's old collection types (Vector -> ArrayList, Stack -> Deque, Hashtable -> HashMap)
old-collection-exp-vector = Use ArrayList instead of Vector
old-collection-exp-hashtable = Use HashMap instead of Hashtable
old-collection-exp-stack = Use Dequeue instead of Stack

string-is-empty-desc = Use String#isEmpty instead of '.equals("")' or '.length() == 0' (or the negation when checking if the String is not empty)
string-is-empty-exp-emptiness = Use 'isEmpty()' instead of '{$exp}' to check for emptiness
string-is-empty-exp-non-emptiness = Use '!<...>isEmpty()' instead of '{$exp}' to check for non-emptiness

# Comment
author-tag-invalid-desc = The @author-tag is invalid
author-tag-invalid-exp = The @author-tag is invalid

commented-out-code-desc = Unused code should be removed and not commented out
commented-out-code-exp = This commented out code should be removed

comment-language-desc = All comments (including Javadoc and inline comments) must be either in English or in German.
comment-language-exp-invalid = The language of this comment is neither English nor German but seems to be {$lang}
comment-language-exp-english = The code contains comments in German and in English. This comment is in English. A German comment can be found at {$path}:{$line}
comment-language-exp-german = The code contains comments in German and in English. This comment is in German. An English comment can be found at {$path}:{$line}

javadoc-param-desc = Javadoc comments for methods must mention all declared parameters
javadoc-param-exp-missing = The parameter '{$param}' is not mentioned in the Javadoc comment
javadoc-param-exp-unknown = Javadoc mentions parameter '{$param}', but there is no such parameter in the method declaration

javadoc-return-null-desc = Methods must document in the @return-annotation if they may return null
javadoc-return-null-exp = The method {$method} may return null but the @return tag doesn't mention it

javadoc-stub-desc = Auto-generated Javadoc comments should be modified for the particular method
javadoc-stub-exp-desc = Javadoc has an empty description
javadoc-stub-exp-param = Stub description for parameter {$param}
javadoc-stub-exp-return = Stub description for return value
javadoc-stub-exp-throws = Stub description for exception {$exp}

# Complexity
diamond-desc = Use the 'diamond operator' instead of repeating the generic type: new Foo<>()
diamond-exp = Use the 'diamond operator'

extends-object-desc = Explicitly extending Object is unnecessary
extends-object-exp = Unnecessary 'extends Object'

for-loop-var-desc = for-loops should have exactly one locally declared control variable
for-loop-var-exp = Each for-loop should have exactly one control variable

implicit-constructor-desc = The default constructor is implicitly generated by the compiler when there is no explicit constructor
implicit-constructor-exp = Unnecessary default constructor

redundant-if-for-bool-desc = It is unnecessary to assign/return boolean literals values in ifs - you can just assign/return the condition directly
redundant-if-for-bool-exp-return = Directly return {$exp} instead of wrapping it in an if
redundant-if-for-bool-exp-assign = Directly assign {$exp} to {$target} instead of wrapping it in an if

redundant-modifier-desc = Some modifiers are implicit
redundant-modifier-exp = Unnecessary modifier

redundant-return-desc = A void return at the end of a method is implicit
redundant-return-exp = Unnecessary return

self-assignment-desc = Assigning a variable to itself is useless
self-assignment-exp = Useless assignment of {$rhs} to {$lhs}

redundant-local-return-desc = Unnecessary declaration of a local variable that is immediately returned
redundant-local-return-exp = Directly return this value

unused-import-desc = Unused import
unused-import-exp = Unused import

wrapper-instantiation-desc = Don't instantiate primitive wrappers
wrapper-instantiation-exp = Don't instantiate primitive wrappers

repeated-math-operation = Don't repeat simple operations such as addition or multiplication, but use the higher-level operation (n + n + n => 3 * n; n * n * n => Math.pow(n, 3)) instead.
repeated-math-operation-mul = Use Math.pow instead of multiplying '{$var}' {count} times with itself.
repeated-math-operation-plus = Use a multiplication with {$count} instead of adding '{$var}' {$count} times to itself.

redundant-neg-desc = '!(a == b)' should be replaced by 'a != b'
redundant-neg-exp = '{$original}' should be written as '{$fixed}'

# Debug
assert-used-desc = Assertions crash the entire program if they evaluate to false.
              Also they can be disabled, so never rely on them to e.g. check user input.
              They are great for testing purposes, but should not be part of your final solution.
              If you want to document an invariant, consider a comment.
assert-used-exp = Assert used

print-stack-trace-desc = Don't print stack traces in your final solution
print-stack-trace-exp = Don't print stack traces in your final solution

# Exceptions
custom-exception-inheritance-desc = Custom exceptions should not extend RuntimeException or Error
custom-exception-inheritance-exp-runtime = Custom exceptions should be checked exceptions
custom-exception-inheritance-exp-error = Custom exceptions should not extend Error

empty-catch-desc = Handle all exceptions appropriately
empty-catch-exp = Empty catch block

exception-controlflow-desc = Exceptions should not be used for control flow inside of a method (i.e. throwing an exception and catching it in a directly surrounding catch block)
exception-controlfow-exp-caught = {$exp} thrown and immediately caught in a surrounding block

runtime-ex-caught-desc = Never catch runtime exceptions (aside from NumberFormatException)
runtime-ex-caught-exp = Runtime exception of type {$exp} caught

# General

compare-objects-desc = Objects should be compared directly with equals and by their String representation
compare-objects-exp = Implement an equals method for type {$type} and use it for comparisons

constant-naming-qualifier-desc = Constants that are never written to should be 'static final' and have a UPPER_SNAKE_CASE name
constant-naming-qualifier-exp = The constant field '{$field}' should be static and have a UPPER_SNAKE_CASE name

constants-interfaces-desc = Shared constants should be placed in enums or final classes and not in interfaces
constants-interfaces-exp = Interfaces must not have fields

param-reassign-desc = Don't reassign method/constructor parameters
param-reassign-exp = Don't reassign method/constructor parameters

double-brace-desc = Double Brace Initialization should be avoided
double-brace-exp = Don't use the obscure 'double brace initialization' syntax

equals-handle-null-argument-desc = equals should handle null arguments
equals-handle-null-argument-exp = equals should handle null arguments

field-local-desc = Fields should be converted to locals if they are always overwritten before being read.
field-local-exp = Field '{$field}' of class {$class} should be converted to a local variable as every method overwrites it before reading it

for-foreach-desc = for-loop should be a for-each-loop
for-foreach-exp = for-loop should be a for-each-loop

missing-override-desc = Missing @Override
missing-override-exp = Missing @Override

system-dependent-linebreak-desc = Always use system-independent line breaks such as the value obtained from System.lineSeparator() or %n in format strings
system-dependent-linebreak-exp = System-dependent line break (\n) used

field-final-desc = Constant fields should be final
field-final-exp = The attribute '{$name}' should be final

string-cmp-desc = Strings must always be compares using the 'equals' method
string-cmp-exp = Use the equals method: '{$lhs}.equals({$rhs})' instead of '{$lhs} == {$rhs}'

# Naming
bool-getter-name-desc = Methods without parameters that return booleans should not have the 'get' prefix but be named 'isXYZ'
bool-getter-name-exp = The method should be called isY() instead of getY()

constants-name-desc = Constants should have descriptive names - e.g. AUTHOR_INDEX instead of FIRST_INDEX
constants-name-exp-string = The name '{$name}' is non-descriptive for the value '{$value}'
constants-name-exp-number = The name '{$name}' is non-descriptive for the value {$value}

linguistic-desc = The code element has a confusing name. See https://pmd.github.io/latest/pmd_rules_java_codestyle.html#linguisticnaming
linguistic-exp = The code element has a confusing name. See https://pmd.github.io/latest/pmd_rules_java_codestyle.html#linguisticnaming

variable-names-desc = Local variables must have descriptive names
variable-name-exp-single-letter = Single letter names such as '{$name}' are usually non-descriptive
variable-name-exp-type = Don't use unnecessary abbreviations such as '{$name}'

# OOP
concrete-collection-desc = Use the parent interface instead of a concrete collection class (e.g. List instead of ArrayList)
concrete-collection-exp = Use the parent interface instead of a concrete collection class (e.g. List instead of ArrayList)

list-getter-desc = Copy mutable collections before returning them to avoid unwanted mutations by other classes
list-getter-exp = Copy this mutable collection before returning it

method-abstract-desc = Empty methods in abstract classes should be abstract
method-abstract-exp = {$type}::{$method} should be abstract and not provide a default implementation

utility-desc = Utility classes must be final and must have a single no-args private constructor.
utility-exp-final = Utility class is not final
utility-exp-constructor = Utility classes must have a single private no-arg constructor
utility-exp-field = Utility classes must only have final fields

static-field-desc = Static fields must be immutable
static-field-exp = The static field '{$name}' must not be static

# Structure
default-package-desc = The default package must not be used
default-package-exp = Do not use the default package

# Unnecessary
empty-block-desc = Empty block (if / else / for / while / switch / try)
empty-block-exp-if = Empty if/else block
empty-block-exp-while = Empty while block
empty-block-exp-try = Empty try block
empty-block-exp-finally = Empty finally block
empty-block-exp-switch = Empty switch block

unused-element-desc = Unused code element (local variable / parameter / private attribute / private method)
unused-element-exp = Not used
