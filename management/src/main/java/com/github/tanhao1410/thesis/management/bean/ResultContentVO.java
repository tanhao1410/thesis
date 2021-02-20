package com.github.tanhao1410.thesis.management.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by lizhengwu on 2017/8/2.
 */
@Setter
@Getter
public class ResultContentVO<T> implements Serializable{

    private Integer code;
    private String resultMessage;
    private String codeMsg;
    private T result;

    public ResultContentVO(){

    }

    public ResultContentVO(Integer result, String resultMessage){
        this.code = result;
        this.resultMessage = resultMessage;
    }

    public ResultContentVO(Integer result, String resultMessage, T content) {
        this.code = result;
        this.resultMessage = resultMessage;
        this.result = content;
    }

    public static <T> ResultContentVO<T> Fail(Integer result, String getResultMessage) {
        return new ResultContentVO(result, getResultMessage);
    }

    public static <T> ResultContentVO<T> Su(T t) {
        return new ResultContentVO(0, "success", t);
    }

    public static <T> ResultContentVO<T> SuButNoData(T t) {
        return new ResultContentVO(0, "没有查询到数据", t);
    }
}
