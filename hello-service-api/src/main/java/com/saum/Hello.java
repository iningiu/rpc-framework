package com.saum;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
@Data
public class Hello implements Serializable {
    private String message;
    private String description;
}
