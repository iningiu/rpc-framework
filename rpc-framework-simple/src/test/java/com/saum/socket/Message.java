package com.saum.socket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
@Data
@AllArgsConstructor
public class Message implements Serializable {
    private String content;
}
