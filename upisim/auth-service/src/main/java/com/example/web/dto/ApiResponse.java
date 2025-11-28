package com.example.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private String status;
    private String message;
    private Object data;

    public ApiResponse(){}

    public ApiResponse(String status,String message){
        this.status=status;
        this.message=message;
    }
    public ApiResponse(String status,String message,Object data){
        this.status=status;
        this.message=message;
        this.data=data;
    }

    public String getStatus() {
        return status;
    }

    public ApiResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ApiResponse setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ApiResponse{"+
                "status='"+status+'\''+
                ",message='"+message+'\''+
                ",data='"+data+
                '}';
    }
}
