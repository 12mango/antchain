package com.example.demo.handler;

import com.example.demo.VO.ApiVo;
import com.example.demo.exception.CustomException;
import com.example.demo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author 12mango
 * @since 2021/5/9
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(value = CustomException.class)
    public <T> ResponseEntity<ApiVo<T>> handlerException(CustomException e) {
        return error(e, HttpStatus.FORBIDDEN);
    }

    private <T> ResponseEntity<ApiVo<T>> error(ApiVo<T> result, Exception e, HttpStatus status) {
        return new ResponseEntity<>(result, status);
    }

    private <T> ResponseEntity<ApiVo<T>> error(CustomException e, HttpStatus status) {
        ApiVo<T> r = R.error(e.getCode(), e.getMsg());
        return new ResponseEntity<>(r, status);
    }

    /*
    public <T> ResponseEntity<ApiVo<T>> handlerException(Exception e) {
        if (e instanceof NullPointerException && config.isTestingEnvironment()) {
            log.error("----不得了拉，NullPointerException------", e);
            return error(R.error(ExceptionCode.B0001, "快去暴打gl"), e, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        if (e instanceof RecruitmentPermissionException) {
            log.error("权限不足：", e);
            return error((RecruitmentException) e, HttpStatus.FORBIDDEN);
        }

        if (e instanceof RecruitmentRequestException) {
            log.error("请求参数错误：", e);
            return error((RecruitmentException) e, HttpStatus.BAD_REQUEST);
        }

        if (e instanceof RecruitmentException) {
            log.error("内部其他错误：", e);
            return error((RecruitmentException) e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.error("系统出现未知异常：", e);
        return error(R.error(ExceptionCode.B0001), e, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private <T> ResponseEntity<ApiVo<T>> error(ApiVo<T> result, Exception e, HttpStatus status) {
        result.setDebug(e.getMessage());
        return new ResponseEntity<>(result, status);
    }

    private <T> ResponseEntity<ApiVo<T>> error(RecruitmentException e, HttpStatus status) {
        ApiVo<T> r = R.error(e.getCode(), e.getMsg());
        r.setDebug(e.getDebugMsg());
        return new ResponseEntity<>(r, status);
    }
     */

}
