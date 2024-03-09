package com.andrei.models;

import com.andrei.utils.enums.OperationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    private OperationEnum command;

    private Double number;
}
