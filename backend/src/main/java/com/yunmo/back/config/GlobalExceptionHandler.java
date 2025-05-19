package com.yunmo.back.config;

import com.yunmo.back.common.ResponseMsg;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseMsg> handleExpiredJwtException(ExpiredJwtException e) {
        ResponseMsg body = new ResponseMsg(401, "Token 已过期");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseMsg> handleSignatureException(SignatureException e) {
        ResponseMsg body = new ResponseMsg(401, "Token 签名不合法");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ResponseMsg> handleMalformedJwtException(MalformedJwtException e) {
        ResponseMsg body = new ResponseMsg(401, "Token 格式错误");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponseMsg> handleJwtException(JwtException e) {
        ResponseMsg body = new ResponseMsg(401, "Token 无效");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMsg> handleException(Exception e) {
        e.printStackTrace();
        ResponseMsg body = new ResponseMsg(500, "服务器内部错误：" + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
