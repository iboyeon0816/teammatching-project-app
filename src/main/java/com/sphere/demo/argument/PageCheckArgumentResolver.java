package com.sphere.demo.argument;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.ex.PageException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageCheckArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PageCheck.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String page = webRequest.getParameter("page");

        if (page == null) {
            throw new PageException(ErrorStatus.PAGE_REQUIRED);
        }

        int pageInt = Integer.parseInt(page);
        if (pageInt < 1) {
            throw new PageException(ErrorStatus.PAGE_NOT_POSITIVE);
        }

        return pageInt - 1;
    }
}
