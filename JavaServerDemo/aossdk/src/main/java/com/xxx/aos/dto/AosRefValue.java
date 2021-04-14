package com.xxx.aos.dto;

public class AosRefValue<T> {
    public T data;

    public AosRefValue(){
        data = null;
    }

    public AosRefValue(T initialVal ){
        data = initialVal;
    }
}
