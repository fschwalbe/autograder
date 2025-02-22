package de.firemage.autograder.core.check.general;

import de.firemage.autograder.core.LocalizedMessage;
import de.firemage.autograder.core.ProblemType;
import de.firemage.autograder.core.check.ExecutableCheck;
import de.firemage.autograder.core.dynamic.DynamicAnalysis;
import de.firemage.autograder.core.integrated.IntegratedCheck;
import de.firemage.autograder.core.integrated.SpoonUtil;
import de.firemage.autograder.core.integrated.StaticAnalysis;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtField;

import java.util.Map;
import java.util.Optional;

@ExecutableCheck(reportedProblems = {ProblemType.FIELD_SHOULD_BE_FINAL})
public class FieldShouldBeFinal extends IntegratedCheck {
    @Override
    protected void check(StaticAnalysis staticAnalysis, DynamicAnalysis dynamicAnalysis) {
        staticAnalysis.processWith(new AbstractProcessor<CtField<?>>() {
            @Override
            public void process(CtField<?> ctField) {
                if (ctField.isImplicit() || !ctField.getPosition().isValidPosition() || ctField.isFinal()) {
                    return;
                }

                // check if the field is written to outside of constructors
                boolean hasWrite = SpoonUtil.hasAnyUses(
                    ctField,
                    ctElement -> ctElement instanceof CtFieldWrite<?> ctFieldWrite
                        && ctFieldWrite.getParent(CtConstructor.class) == null
                );

                // a field can not be final if it is written to from outside of constructors
                if (hasWrite) {
                    return;
                }

                // check if the field is written to in constructors, which is fine if it does not have an explicit value
                boolean hasWriteInConstructor = SpoonUtil.hasAnyUses(
                    ctField,
                    ctElement -> ctElement instanceof CtFieldWrite<?> ctFieldWrite
                        && ctFieldWrite.getParent(CtConstructor.class) != null
                );

                // we need to check if the field is explicitly initialized, because this is not allowed:
                //
                // final String a = "hello";
                // a = "world"; // error
                //
                // but this is allowed:
                //
                // final String a;
                // a = "hello";
                boolean hasExplicitValue = ctField.getDefaultExpression() != null && !ctField.getDefaultExpression().isImplicit();

                // a static field can only be final if it has an explicit value and is never written to
                if (ctField.isStatic() && (hasWriteInConstructor || !hasExplicitValue)) {
                    return;
                }

                // a field can be final if it is only written to in the constructor or it is never assigned a new value and has an explicit value
                if (!hasWriteInConstructor || !hasExplicitValue) {
                    addLocalProblem(
                        ctField,
                        new LocalizedMessage("field-should-be-final", Map.of("name", ctField.getSimpleName())),
                        ProblemType.FIELD_SHOULD_BE_FINAL
                    );
                }
            }
        });
    }

    @Override
    public Optional<Integer> maximumProblems() {
        return Optional.of(4);
    }
}
