package com.boot2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Employee {

   private String firstName;
   private String lastName;
   private int deptId;
   private double salary;

}
