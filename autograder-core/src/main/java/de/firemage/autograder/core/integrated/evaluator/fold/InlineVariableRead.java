package de.firemage.autograder.core.integrated.evaluator.fold;

import de.firemage.autograder.core.integrated.SpoonUtil;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtVariableRead;

import java.util.Optional;

/**
 * Inline reads of constant variables with its value.
 */
public final class InlineVariableRead implements Fold {
    private InlineVariableRead() {
    }

    public static Fold create() {
        return new InlineVariableRead();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> CtExpression<T> foldCtVariableRead(CtVariableRead<T> ctVariableRead) {
        Optional<CtExpression<T>> ctExpressionOptional = SpoonUtil.getEffectivelyFinalExpression(
            ctVariableRead.getVariable()
        );

        return ctExpressionOptional.flatMap(ctExpression -> {
            // only inline literals:
            if (ctExpression instanceof CtLiteral<?> ctLiteral) {
                return Optional.of((CtExpression<T>) ctLiteral);
            } else {
                return Optional.empty();
            }
        }).orElse(ctVariableRead);
    }
}
