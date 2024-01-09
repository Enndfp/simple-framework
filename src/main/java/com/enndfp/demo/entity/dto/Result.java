package com.enndfp.demo.entity.dto;

import lombok.Data;

/**
 * @author Enndfp
 */
@Data
public class Result<T> {

    // 本次请求结果的状态码，200表示成功
    private int code;

    // 本次请求结果的详情
    private String msg;

    // 本次请求返回的结果集
    private T data;
}
