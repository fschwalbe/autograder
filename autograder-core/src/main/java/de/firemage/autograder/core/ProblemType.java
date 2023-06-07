package de.firemage.autograder.core;

public enum ProblemType {
    UI_INPUT_SEPARATION,
    UI_OUTPUT_SEPARATION,
    AVOID_INNER_CLASSES,
    USE_STRING_FORMATTED,
    OPTIONAL_TRI_STATE,
    OPTIONAL_ARGUMENT,
    AVOID_LABELS,
    AVOID_SHADOWING,
    DO_NOT_USE_SYSTEM_EXIT,
    SCANNER_MUST_BE_CLOSED,
    EQUALS_HASHCODE_COMPARABLE_CONTRACT,
    UNCHECKED_TYPE_CAST,
    DEPRECATED_COLLECTION_USED,
    COLLECTION_IS_EMPTY_REIMPLEMENTED,
    STRING_IS_EMPTY_REIMPLEMENTED,
    INVALID_AUTHOR_TAG,
    COMMENTED_OUT_CODE,
    INCONSISTENT_COMMENT_LANGUAGE,
    MUTABLE_ENUM,
    INVALID_COMMENT_LANGUAGE,
    JAVADOC_STUB_DESCRIPTION,
    JAVADOC_STUB_PARAMETER_TAG,
    JAVADOC_STUB_RETURN_TAG,
    JAVADOC_STUB_THROWS_TAG,
    JAVADOC_MISSING_PARAMETER_TAG,
    JAVADOC_UNKNOWN_PARAMETER_TAG,
    PACKAGE_NAMING_CONVENTION,
    JAVADOC_INCOMPLETE_RETURN_TAG,
    JAVADOC_UNDOCUMENTED_THROWS,
    UNUSED_DIAMOND_OPERATOR,
    EXPLICITLY_EXTENDS_OBJECT,
    FOR_WITH_MULTIPLE_VARIABLES,
    REDUNDANT_DEFAULT_CONSTRUCTOR,
    REDUNDANT_IF_FOR_BOOLEAN,
    REDUNDANT_MODIFIER,
    REDUNDANT_VOID_RETURN,
    REDUNDANT_SELF_ASSIGNMENT,
    REDUNDANT_LOCAL_BEFORE_RETURN,
    REDUNDANT_BOOLEAN_EQUAL,
    AVOID_RECOMPILING_REGEX,
    UNUSED_IMPORT,
    PRIMITIVE_WRAPPER_INSTANTIATION,
    ASSERT,
    EXCEPTION_PRINT_STACK_TRACE,
    CUSTOM_EXCEPTION_INHERITS_RUNTIME_EXCEPTION,
    CUSTOM_EXCEPTION_INHERITS_ERROR,
    EMPTY_CATCH,
    EXCEPTION_CAUGHT_IN_SURROUNDING_BLOCK,
    RUNTIME_EXCEPTION_OR_ERROR_CAUGHT,
    OBJECTS_COMPARED_VIA_TO_STRING,
    FIELD_SHOULD_BE_CONSTANT,
    CONSTANT_IN_INTERFACE,
    DO_NOT_HAVE_CONSTANTS_CLASS,
    STATIC_INTERFACE,
    STATIC_METHOD_IN_INTERFACE,
    DO_NOT_USE_RAW_TYPES,
    DUPLICATE_CODE,
    REASSIGNED_PARAMETER,
    DOUBLE_BRACE_INITIALIZATION,
    NON_COMPLIANT_EQUALS,
    INSTANCE_FIELD_CAN_BE_LOCAL,
    FOR_CAN_BE_FOREACH,
    OVERRIDE_ANNOTATION_MISSING,
    SYSTEM_SPECIFIC_LINE_BREAK,
    BOOLEAN_GETTER_NOT_CALLED_IS,
    MEANINGLESS_CONSTANT_NAME,
    CONFUSING_IDENTIFIER,
    SINGLE_LETTER_LOCAL_NAME,
    IDENTIFIER_IS_ABBREVIATED_TYPE,
    IDENTIFIER_CONTAINS_TYPE_NAME,
    USE_GUARD_CLAUSES,
    CONCRETE_COLLECTION_AS_FIELD_OR_RETURN_VALUE,
    LIST_NOT_COPIED_IN_GETTER,
    METHOD_USES_PLACEHOLDER_IMPLEMENTATION,
    UTILITY_CLASS_NOT_FINAL,
    UTILITY_CLASS_INVALID_CONSTRUCTOR,
    UTILITY_CLASS_ABSTRACT,
    DEFAULT_PACKAGE_USED,
    COMMON_REIMPLEMENTATION_ARRAY_COPY,
    COMMON_REIMPLEMENTATION_STRING_REPEAT,
    ABSTRACT_CLASS_WITHOUT_ABSTRACT_METHOD,
    SHOULD_BE_INTERFACE,
    COMPOSITION_OVER_INHERITANCE,
    USE_ENTRY_SET,
    EMPTY_BLOCK,
    UNUSED_CODE_ELEMENT,
    SIMILAR_IDENTIFIER,
    REPEATED_MATH_OPERATION,
    STATIC_FIELD_SHOULD_BE_INSTANCE,
    FIELD_SHOULD_BE_FINAL,
    STRING_COMPARE_BY_REFERENCE,
    REDUNDANT_NEGATION,
    REDUNDANT_ARRAY_INIT,
    USE_OPERATOR_ASSIGNMENT,
    JAVADOC_UNEXPECTED_TAG,
    UNMERGED_ELSE_IF,
    MERGE_NESTED_IF,
    EXCEPTION_WITHOUT_MESSAGE,
    EMPTY_INTERFACE,
    SUPPRESS_WARNINGS_USED,
    COMPLEX_REGEX,
    USE_FORMAT_STRING,
    LOCAL_VARIABLE_SHOULD_BE_CONSTANT,
    USE_ENUM_COLLECTION,
    INSTANCEOF,
    INSTANCEOF_EMULATION,
    COMPARE_CHAR_VALUE,
    REDUNDANT_CATCH,
    SHOULD_BE_ENUM_ATTRIBUTE,
    CLOSED_SET_OF_VALUES,
    MATH_FLOOR_DIVISION,
    TYPE_HAS_DESCRIPTIVE_NAME,
    IDENTIFIER_REDUNDANT_NUMBER_SUFFIX,
    IMPORT_TYPES,
    USE_DIFFERENT_VISIBILITY,
    USE_DIFFERENT_VISIBILITY_PEDANTIC,
    COMPARE_TO_ZERO,
    EQUALS_USING_HASHCODE,
    EQUALS_UNSAFE_CAST,
    EQUALS_INCOMPATIBLE_TYPE,
    INCONSISTENT_HASH_CODE,
    UNDEFINED_EQUALS,
    EQUALS_BROKEN_FOR_NULL,
    NON_OVERRIDING_EQUALS,
    ARRAYS_HASHCODE,
    EQUALS_REFERENCE,
    ARRAY_AS_KEY_OF_SET_OR_MAP,
    MULTIPLE_INLINE_STATEMENTS,
    UNNECESSARY_BOXING,
    REDUNDANT_UNINITIALIZED_VARIABLE
}
