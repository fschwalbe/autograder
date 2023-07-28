package de.firemage.autograder.core.check.general;

import de.firemage.autograder.core.LinterException;
import de.firemage.autograder.core.LocalizedMessage;
import de.firemage.autograder.core.Problem;
import de.firemage.autograder.core.ProblemType;
import de.firemage.autograder.core.file.StringSourceInfo;
import de.firemage.autograder.core.check.AbstractCheckTest;
import de.firemage.autograder.core.compiler.JavaVersion;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUseDifferentVisibility extends AbstractCheckTest {
    private static final String LOCALIZED_MESSAGE_KEY = "use-different-visibility";
    private static final ProblemType PROBLEM_TYPE = ProblemType.USE_DIFFERENT_VISIBILITY;
    private static final ProblemType PROBLEM_TYPE_PEDANTIC = ProblemType.USE_DIFFERENT_VISIBILITY_PEDANTIC;
    private static final List<ProblemType> PROBLEM_TYPES = List.of(PROBLEM_TYPE, PROBLEM_TYPE_PEDANTIC);

    @Test
    void testNoOtherReferences() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "Example",
                    """
                        public class Example {
                            String exampleVariable;
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);


        assertEquals(0, problems.size());
    }

    @Test
    void testPackagePrivateRoot() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "Example",
                    """
                        public class Example {
                            String exampleVariable;
                        }
                        """
                ),
                Map.entry(
                    "Other",
                    """
                        public class Other {
                            private static void foo() {
                                Example example = new Example();
                                example.exampleVariable = "foo";
                            }
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);


        assertEquals(0, problems.size());
    }

    @Test
    void testPackagePrivateDifferentPackage() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "com.Example",
                    """
                        package com;
                        public class Example {
                            public String exampleVariable;
                        }
                        """
                ),
                Map.entry(
                    "Other",
                    """
                        import com.Example;

                        public class Other {
                            private static void foo() {
                                Example example = new Example();
                                example.exampleVariable = "foo";
                            }
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);


        assertEquals(0, problems.size());
    }

    @Test
    void testPrivate() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "Example",
                    """
                        public class Example {
                            public String exampleVariable;
                                            
                            private void foo() {
                                exampleVariable = "foo";
                            }
                            
                            static class Inner {
                                private void bar() {
                                    Example example = new Example();
                                    example.exampleVariable = "bar";
                                }
                            }
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);


        assertEquals(1, problems.size());
        assertEquals(PROBLEM_TYPE, problems.get(0).getProblemType());
        assertEquals(
            super.linter.translateMessage(
                new LocalizedMessage(
                    LOCALIZED_MESSAGE_KEY,
                    Map.of(
                        "name", "exampleVariable",
                        "suggestion", "private"
                    )
                )),
            super.linter.translateMessage(problems.get(0).getExplanation())
        );
    }

    @Test
    void testPrivateNestedClass() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "Example",
                    """
                        public class Example {
                                            
                            private void foo() {
                                Inner inner = new Inner();
                                inner.exampleVariable = "foo";
                            }
                            
                            static class Inner {
                                public String exampleVariable;
                            }
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);


        assertEquals(1, problems.size());
        assertEquals(PROBLEM_TYPE, problems.get(0).getProblemType());
        assertEquals(
            super.linter.translateMessage(
                new LocalizedMessage(
                    LOCALIZED_MESSAGE_KEY,
                    Map.of(
                        "name", "exampleVariable",
                        "suggestion", "private"
                    )
                )),
            super.linter.translateMessage(problems.get(0).getExplanation())
        );
    }

    @Test
    void testMainMethod() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "Main",
                    """
                        public class Main {
                            public static void main(String[] args) {}
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);


        assertEquals(0, problems.size());
    }

    @Test
    void testMethodVisibility() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "Main",
                    """
                        public class Main {
                            public void foo() {} // Not Ok
                            
                            void bar() {} // Not Ok
                            
                            private void baz() {} // Ok
                            
                            void a() {} // Not Ok

                            private void b() {
                                a();
                            }
                            
                            public void c() {} // Not Ok
                            
                            void d() {} // Ok
                            
                            void e() {
                                // so that all methods are used
                                foo();
                                bar();
                                baz();
                                a();
                                b();
                                c();
                                d();
                            } 
                        }
                        """
                ),
                Map.entry(
                    "Other",
                    """
                        public class Other {
                            private void call() {
                                Main main = new Main();
                                main.c();
                                main.d();
                                main.e();
                            }
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);

        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("foo", "private");
        expected.put("bar", "private");
        expected.put("a", "private");
        expected.put("c", "default");

        int i = 0;
        for (var entry : expected.entrySet()) {
            assertEquals(
                this.linter.translateMessage(
                    new LocalizedMessage(
                        LOCALIZED_MESSAGE_KEY,
                        Map.of(
                            "name", entry.getKey(),
                            "suggestion", entry.getValue()
                        )
                    )),
                this.linter.translateMessage(problems.get(i).getExplanation())
            );
            i += 1;
        }
    }

    @Test
    void testOverriddenMethod() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "Main",
                    """
                        public class Main {
                            @Override
                            public boolean equals(Object other) {
                                return true;
                            }
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);

        assertEquals(0, problems.size());
    }

    @Test
    void testBackwardReference() throws LinterException, IOException {
        // The checkstyle enforces that constants should be sorted by their visibility, so
        // for example, public variables should be before private variables.
        //
        // In the example below, both constants have default visibility, but `DATE_FORMAT` could
        // be private. However, since `DATE_FORMAT2` references `DATE_FORMAT`, according to the
        // checkstyle it would have to be declared before `DATE_FORMAT`, which is not possible:
        //
        // static final String DATE_FORMAT2 = DATE_FORMAT + " HH:mm:ss";
        // private static final String DATE_FORMAT = "yyyy-MM-dd";
        //
        // ^ this will not compile
        //
        // The solution: Constants must not have a lower visibility than the constants (in the same class)
        //               they are referenced in.
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "Main",
                    """
                        public class Main {
                            static final String DATE_FORMAT = "yyyy-MM-dd";
                            static final String DATE_FORMAT2 = DATE_FORMAT + " HH:mm:ss";
                        }
                        """
                ),
                Map.entry(
                    "Other",
                    """
                        public class Other {
                            public static void main(String[] args) {
                                System.out.println(Main.DATE_FORMAT2);
                            }
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);

        assertEquals(0, problems.size());
    }

    @Test
    void testBackwardReferenceCanBeLowered() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "Main",
                    """
                        public class Main {
                            static final String DATE_FORMAT = "yyyy-MM-dd";
                            private static final String DATE_FORMAT2 = DATE_FORMAT + " HH:mm:ss";
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);

        assertEquals(1, problems.size());
    }

    @Test
    void testVisibilityProtected() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "foo.Parent",
                    """
                        package foo;

                        public class Parent {
                            protected static final String SOME_VAR = "foo";
                        }
                        """
                ),
                Map.entry(
                    "Child",
                    """
                        import foo.Parent;

                        public class Child extends Parent {
                            public static void main(String[] args) {
                                System.out.println(Parent.SOME_VAR);
                            }
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);

        assertEquals(0, problems.size());
    }

    @Test
    void testImplementedProtectedMethod() throws LinterException, IOException {
        List<Problem> problems = super.check(StringSourceInfo.fromSourceStrings(
            JavaVersion.JAVA_17,
            Map.ofEntries(
                Map.entry(
                    "ui.Command",
                    """
                        package ui;

                        public abstract class Command {
                            protected static final String SOME_VAR = "foo";
                            
                            public void execute() {
                                executePlatform();
                            }
                            
                            protected abstract void executePlatform();
                        }
                        """
                ),
                Map.entry(
                    "ui.commands.ExampleCommand",
                    """
                        package ui.commands;
                        
                        import ui.Command;

                        public class ExampleCommand extends Command {
                            @Override
                            protected void executePlatform() {}
                        }
                        """
                ),
                Map.entry(
                    "Main",
                    """
                        import ui.Command;
                        import ui.commands.ExampleCommand;

                        public class Main {
                            public static void main(String[] args) {
                                Command command = new ExampleCommand();
                                command.execute();
                            }
                        }
                        """
                )
            )
        ), PROBLEM_TYPES);

        assertEquals(0, problems.size());
    }
}