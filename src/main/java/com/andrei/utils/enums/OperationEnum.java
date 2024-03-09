package com.andrei.utils.enums;

import lombok.Getter;

@Getter
public enum OperationEnum {

    APPEND("append"),
    REDUCE("reduce"),
    DIVIDE("divide"),
    MULTIPLY("multiply"),
    POWER("power");

    private final String operationEnumLabel;

    OperationEnum(String operationEnumLabel) {
        this.operationEnumLabel = operationEnumLabel;
    }
}
