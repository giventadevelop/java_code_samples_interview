package com.boot2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Simple employee model. {@code salary} is {@link BigDecimal} so money is exact
 * (prefer over {@code double}/{@code float}).
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Employee {

    private String firstName;
    private String lastName;
    private int deptId;
    private BigDecimal salary;

}
