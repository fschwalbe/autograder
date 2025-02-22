package de.firemage.autograder.core.check.api;

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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCollectionAddAll extends AbstractCheckTest {
    private static final List<ProblemType> PROBLEM_TYPES = List.of(
        ProblemType.COLLECTION_ADD_ALL,
        ProblemType.COMMON_REIMPLEMENTATION_ADD_ENUM_VALUES,
        ProblemType.COMMON_REIMPLEMENTATION_ADD_ALL
    );

    private void assertEqualsReimplementation(Problem problem, String suggestion) {
        assertEquals(
            this.linter.translateMessage(
                new LocalizedMessage(
                    "common-reimplementation",
                    Map.of(
                        "suggestion", suggestion
                    )
                )),
            this.linter.translateMessage(problem.getExplanation())
        );
    }

    @Test
    void testListAdd() throws LinterException, IOException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Test",
            """
                import java.util.List;

                public class Test {
                    public void foo(List<String> list) {
                        list.add(" ");
                        list.add("a");
                        list.add("b");
                        list.add("c");
                    }
                }
                """
        ), PROBLEM_TYPES);

        assertEqualsReimplementation(problems.next(), "list.addAll(List.of(\" \", \"a\", \"b\", \"c\"))");

        problems.assertExhausted();
    }


    @Test
    void testEnumValuesAddAllUnorderedSet() throws LinterException, IOException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Test",
            """
                import java.util.HashSet;
                import java.util.Set;

                enum Fruit {
                    APPLE, BANANA, CHERRY;
                }

                public class Test {
                    public static void main(String[] args) {
                        Set<Fruit> fruits = new HashSet<>();
                        
                        fruits.add(Fruit.APPLE);
                        fruits.add(Fruit.BANANA);
                        fruits.add(Fruit.CHERRY);

                        System.out.println(fruits);
                    }
                }
                """
        ), PROBLEM_TYPES);

        assertEqualsReimplementation(problems.next(), "fruits.addAll(Arrays.asList(Fruit.values()))");
        problems.assertExhausted();
    }

    @Test
    void testEnumValuesAddAllOrderedList() throws LinterException, IOException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Test",
            """
                import java.util.ArrayList;
                import java.util.List;

                enum GodCard {
                    APOLLO,
                    ARTEMIS,
                    ATHENA,
                    ATLAS,
                    DEMETER,
                    HERMES;
                }

                public class Test {
                    private static List<GodCard> getAvailableCards() {
                        List<GodCard> availableCards = new ArrayList<>();

                        availableCards.add(GodCard.APOLLO);
                        availableCards.add(GodCard.ARTEMIS);
                        availableCards.add(GodCard.ATHENA);
                        availableCards.add(GodCard.ATLAS);
                        availableCards.add(GodCard.DEMETER);
                        availableCards.add(GodCard.HERMES);

                        return availableCards;
                    }

                    // NOTE: Enum.values() returns the variants in the order they are declared
                    //       For Sets this is not a problem, but for Lists the order in which add
                    //       is called is important
                    private static List<GodCard> getReversedAvailableGodCards() {
                        List<GodCard> availableCards = new ArrayList<>();

                        availableCards.add(GodCard.HERMES);
                        availableCards.add(GodCard.DEMETER);
                        availableCards.add(GodCard.ATLAS);
                        availableCards.add(GodCard.APOLLO);
                        availableCards.add(GodCard.ATHENA);
                        availableCards.add(GodCard.ARTEMIS);

                        return availableCards;
                    }
                    
                    private static List<GodCard> getAvailableCardsDuplicateMiddle() {
                        List<GodCard> availableCards = new ArrayList<>();

                        availableCards.add(GodCard.APOLLO);
                        availableCards.add(GodCard.ARTEMIS);
                        availableCards.add(GodCard.ATHENA);
                        availableCards.add(GodCard.ATLAS);
                        availableCards.add(GodCard.ATLAS);
                        availableCards.add(GodCard.DEMETER);
                        availableCards.add(GodCard.HERMES);

                        return availableCards;
                    }

                    private static List<GodCard> getAvailableCardsDuplicateEnd() {
                        List<GodCard> availableCards = new ArrayList<>();

                        availableCards.add(GodCard.APOLLO);
                        availableCards.add(GodCard.ARTEMIS);
                        availableCards.add(GodCard.ATHENA);
                        availableCards.add(GodCard.ATLAS);
                        availableCards.add(GodCard.DEMETER);
                        availableCards.add(GodCard.HERMES);
                        availableCards.add(GodCard.HERMES);

                        return availableCards;
                    }
                }
                """
        ), PROBLEM_TYPES);

        assertEqualsReimplementation(problems.next(), "availableCards.addAll(Arrays.asList(GodCard.values()))");
        assertEqualsReimplementation(problems.next(), "availableCards.addAll(List.of(GodCard.HERMES, GodCard.DEMETER, GodCard.ATLAS, GodCard.APOLLO, GodCard.ATHENA, GodCard.ARTEMIS))");
        assertEqualsReimplementation(problems.next(), "availableCards.addAll(List.of(GodCard.APOLLO, GodCard.ARTEMIS, GodCard.ATHENA, GodCard.ATLAS, GodCard.ATLAS, GodCard.DEMETER, GodCard.HERMES))");
        assertEqualsReimplementation(problems.next(), "availableCards.addAll(List.of(GodCard.APOLLO, GodCard.ARTEMIS, GodCard.ATHENA, GodCard.ATLAS, GodCard.DEMETER, GodCard.HERMES, GodCard.HERMES))");
        problems.assertExhausted();
    }

    @Test
    void testAddAllArray() throws LinterException, IOException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Main",
            """
                import java.util.Collection;
                import java.util.ArrayList;

                public class Main {
                    public static <T> Collection<T> toCollection(T[] array) {
                        Collection<T> result = new ArrayList<>();
                        
                        for (T element : array) {
                            result.add(element);
                        }
                        
                        return result;
                    }
                }
                """
        ), PROBLEM_TYPES);

        assertEqualsReimplementation(problems.next(), "result.addAll(Arrays.asList(array))");
        problems.assertExhausted();
    }

    @Test
    void testAddAllCollection() throws LinterException, IOException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Main",
            """
                import java.util.Collection;
                import java.util.ArrayList;

                public class Main {
                    public static <T> Collection<T> toCollection(Iterable<T> input) {
                        Collection<T> result = new ArrayList<>();
                        
                        for (T element : input) {
                            result.add(element);
                        }

                        return result;
                    }
                }
                """
        ), PROBLEM_TYPES);

        assertEqualsReimplementation(problems.next(), "result.addAll(input)");
        problems.assertExhausted();
    }

    @Test
    void testAddAllCast() throws LinterException, IOException {
        ProblemIterator problems = this.checkIterator(StringSourceInfo.fromSourceString(
            JavaVersion.JAVA_17,
            "Main",
            """
                import java.util.Collection;
                import java.util.ArrayList;

                public class Main {
                    public static <T, U> Collection<U> toCollection(Iterable<T> input) {
                        Collection<U> result = new ArrayList<>();
                        
                        for (T element : input) {
                            result.add((U) element);
                        }

                        return result;
                    }
                }
                """
        ), PROBLEM_TYPES);

        problems.assertExhausted();
    }
}
