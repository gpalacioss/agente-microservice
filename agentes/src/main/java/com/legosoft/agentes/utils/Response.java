package com.legosoft.agentes.utils;

import com.legosoft.agentes.model.Compania;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private int code;

    private String message;

    private Compania compania;

}
