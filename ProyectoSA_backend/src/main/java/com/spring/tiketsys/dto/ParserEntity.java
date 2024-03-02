package com.spring.tiketsys.dto;

public interface ParserEntity<T,D> {


    T parseToDTO();

    D parseToEntity();

    String toCSV();
}
