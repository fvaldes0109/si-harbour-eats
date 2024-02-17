package com.example.harbourquests.security;


import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;

@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
public class SecurityInterceptor {

  @Around("@within(org.springframework.web.bind.annotation.RestController)")
  public Object aroundControllerMethodsAdvice(ProceedingJoinPoint proceedingJoinPoint)
          throws Throwable {


    return proceedingJoinPoint.proceed();
  }

  private Optional<String> getHeaderValue(String headerName) {
    var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (Objects.isNull(requestAttributes)) {
      return Optional.empty();
    }

    var request = requestAttributes.getRequest();
    var header = request.getHeader(headerName);

    return Optional.ofNullable(header);
  }
}