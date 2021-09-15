package de.firemage.codelinter.web.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Optional;

public record SuccessfulResult(@JsonIgnore SpoonResult spoonResult,
                               @JsonIgnore PMDResult pmdResult,
                               @JsonIgnore CompilationResult compilationResult,
                               @JsonIgnore SpotbugsResult spotbugsResult,
                               @JsonIgnore CPDResult cpdResult) implements LintingResult {

    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    public Optional<SpoonResult> getSpoon() {
        return Optional.ofNullable(this.spoonResult);
    }

    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    public Optional<PMDResult> getPMD() {
        return Optional.ofNullable(this.pmdResult);
    }

    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    public Optional<CompilationResult> getCompilation() {
        return Optional.ofNullable(this.compilationResult);
    }

    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    public Optional<SpotbugsResult> getSpotbugs() {
        return Optional.ofNullable(this.spotbugsResult);
    }

    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    public Optional<CPDResult> getCPD() {
        return Optional.ofNullable(this.cpdResult);
    }
}