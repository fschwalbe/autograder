package de.firemage.autograder.core.compiler;

import de.firemage.autograder.core.CodePosition;
import de.firemage.autograder.core.PathUtil;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public record CompilationDiagnostic(String path, int line, int column, String message, String code) {
    protected CompilationDiagnostic(Diagnostic<? extends JavaFileObject> diagnostic, Path root) {
        this(PathUtil.getSanitizedPath(diagnostic.getSource().toUri(), root),
                (int) diagnostic.getLineNumber(),
                (int) diagnostic.getColumnNumber(),
                diagnostic.getMessage(Compiler.COMPILER_LOCALE),
                diagnostic.getCode());
    }

    public static String formatMultiple(List<CompilationDiagnostic> diagnostics) {
        return diagnostics.stream()
                .map(CompilationDiagnostic::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public CodePosition codePosition() {
        return new CodePosition(Path.of(this.path), this.line, this.line, this.column, this.column);
    }

    @Override
    public String toString() {
        String message = this.path;
        if (this.line != Diagnostic.NOPOS) {
            message += ":" + this.line;
        }
        return message + " " + this.message;
    }
}
