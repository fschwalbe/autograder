package de.firemage.autograder.core.check.exceptions;

import de.firemage.autograder.core.LinterException;
import de.firemage.autograder.core.LocalizedMessage;
import de.firemage.autograder.core.Problem;
import de.firemage.autograder.core.ProblemType;
import de.firemage.autograder.core.check.AbstractCheckTest;
import de.firemage.autograder.core.compiler.JavaVersion;
import de.firemage.autograder.core.file.StringSourceInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestExceptionMessageCheck extends AbstractCheckTest {
    private static final List<ProblemType> PROBLEM_TYPES = List.of(ProblemType.EXCEPTION_WITHOUT_MESSAGE);

    void assertMissingMessage(Problem problem) {
        assertEquals(
            this.linter.translateMessage(new LocalizedMessage(
                "exception-message"
            )),
            this.linter.translateMessage(problem.getExplanation())
        );
    }

    @Test
    void testUtilityClassConstructor() throws IOException, LinterException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Main",
            """
                public class Main {
                    private Main() {
                        throw new UnsupportedOperationException();
                    }

                    public static void main(String[] args) {
                    }
                }
                """
        ), PROBLEM_TYPES);

        problems.assertExhausted();
    }

    @Test
    void testCustomException() throws IOException, LinterException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Main",
            """
                class Bar extends Exception {
                    public Bar() {
                        super("Bar");
                    }
                }

                public class Main {
                    void foo() throws Bar {
                        throw new Bar(); /*# ok #*/
                    }
                }
            """
        ), PROBLEM_TYPES);

        problems.assertExhausted();
    }

    @Test
    void testEmptyMessageString() throws IOException, LinterException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Main",
            """
                public class Main {
                    void c() {
                        throw new IllegalArgumentException(""); /*# not ok #*/
                    }

                    void d() {
                        throw new IllegalArgumentException(" "); /*# not ok #*/
                    }

                    void f() {
                        throw new IllegalArgumentException("", new Exception()); /*# not ok #*/
                    }
                }
            """
        ), PROBLEM_TYPES);

        assertMissingMessage(problems.next());
        assertMissingMessage(problems.next());
        assertMissingMessage(problems.next());

        problems.assertExhausted();
    }

    @Test
    void testExceptionMessage() throws IOException, LinterException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Main",
            """
                public class Main {
                    void a() {
                        throw new IllegalArgumentException("foo"); /*# ok #*/
                    }

                    void b() {
                        throw new IllegalArgumentException(); /*# not ok #*/
                    }
                }
            """
        ), PROBLEM_TYPES);

        assertMissingMessage(problems.next());

        problems.assertExhausted();
    }

    @Test
    void testSwitchDefault() throws IOException, LinterException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Main",
            """
                public class Main {
                    void foo(String string) {
                        switch (string) {
                            case "a" -> {}
                            case "b" -> {}
                            default -> throw new IllegalStateException(); /*# ok #*/
                        }
                    }
                }
            """
        ), PROBLEM_TYPES);

        problems.assertExhausted();
    }

    @Test
    void testExceptionCatchAndThrow() throws IOException, LinterException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Main",
            """
                public class Main {
                    private java.io.File file;

                    public String[] readFile() {
                        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(this.file))) {
                            return reader.lines().toArray(String[]::new);
                        } catch (final java.io.IOException e) {
                            throw new RuntimeException(e); /*# ok #*/
                        }
                    }
                }
            """
        ), PROBLEM_TYPES);

        problems.assertExhausted();
    }
}
