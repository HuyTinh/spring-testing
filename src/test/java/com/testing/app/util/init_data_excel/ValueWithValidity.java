package com.testing.app.util.init_data_excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ValueWithValidity<T> {

    private T value;
    private String message;
    private boolean valid;
}
