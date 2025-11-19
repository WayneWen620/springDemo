package com.example.demo.common.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class APIReturnObject {

    private String message;

    private Map<String, Object> data;

}
