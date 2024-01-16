package com.enndfp.simpleframework.mvc.processor.impl;

import com.enndfp.simpleframework.core.BeanContainer;
import com.enndfp.simpleframework.mvc.RequestProcessorChain;
import com.enndfp.simpleframework.mvc.annotation.RequestMapping;
import com.enndfp.simpleframework.mvc.annotation.RequestParam;
import com.enndfp.simpleframework.mvc.annotation.ResponseBody;
import com.enndfp.simpleframework.mvc.processor.RequestProcessor;
import com.enndfp.simpleframework.mvc.render.ResultRender;
import com.enndfp.simpleframework.mvc.render.impl.JsonResultRender;
import com.enndfp.simpleframework.mvc.render.impl.ResourceNotFoundResultRender;
import com.enndfp.simpleframework.mvc.render.impl.ViewResultRender;
import com.enndfp.simpleframework.mvc.type.ControllerMethod;
import com.enndfp.simpleframework.mvc.type.RequestPathInfo;
import com.enndfp.simpleframework.utils.ConverterUtil;
import com.enndfp.simpleframework.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller请求处理器
 *
 * @author Enndfp
 */
@Slf4j
public class ControllerRequestProcessor implements RequestProcessor {

    // ICO容器
    private BeanContainer beanContainer;

    // 请求和Controller方法的映射Map
    private Map<RequestPathInfo, ControllerMethod> pathControllerMethodMap = new ConcurrentHashMap<>();

    /**
     * 依靠容器的能力，建立起请求路径、请求方法与Controller方法实例的映射
     */
    public ControllerRequestProcessor() {
        this.beanContainer = BeanContainer.getInstance();
        Set<Class<?>> requestMappingSet = beanContainer.getClassesByAnnotation(RequestMapping.class);
        initPathControllerMethodMap(requestMappingSet);
    }

    /**
     * 初始化路径到控制器方法的映射
     *
     * @param requestMappingSet
     */
    private void initPathControllerMethodMap(Set<Class<?>> requestMappingSet) {
        if (ValidationUtil.isEmpty(requestMappingSet)) return;

        for (Class<?> requestMappingClass : requestMappingSet) {
            processClassMapping(requestMappingClass);
        }
    }

    /**
     * 处理类级别的 @RequestMapping 注解
     *
     * @param requestMappingClass
     */
    private void processClassMapping(Class<?> requestMappingClass) {
        // 1. 获取类上面的请求地址
        RequestMapping classRequestMapping = requestMappingClass.getAnnotation(RequestMapping.class);
        String classBasePath = classRequestMapping.value();
        if (!classBasePath.startsWith("/")) {
            classBasePath = "/" + classBasePath;
        }

        // 2. 获取该类所有申明的方法
        Method[] methods = requestMappingClass.getDeclaredMethods();
        if (ValidationUtil.isEmpty(methods)) return;

        for (Method method : methods) {
            processMethodMapping(requestMappingClass, classBasePath, method);
        }
    }

    /**
     * 处理方法级别的 @RequestMapping 注解
     *
     * @param requestMappingClass
     * @param classBasePath
     * @param method
     */
    private void processMethodMapping(Class<?> requestMappingClass, String classBasePath, Method method) {
        if (!method.isAnnotationPresent(RequestMapping.class)) return;

        // 1. 获取方法上的请求地址
        RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
        String methodPath = methodRequestMapping.value();
        if (!methodPath.startsWith("/")) {
            methodPath = "/" + methodPath;
        }
        // 1.1 拼接完整的请求地址
        String url = classBasePath + methodPath;

        // 2. 获取方法的参数列表
        Map<String, Class<?>> methodParamMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();

        if (ValidationUtil.isEmpty(parameters)) return;

        for (Parameter parameter : parameters) {
            processParameterAnnotation(parameter, methodParamMap);
        }

        // 3. 封装 RequestPathInfo
        String httpMethod = String.valueOf(methodRequestMapping.method());
        RequestPathInfo requestPathInfo = new RequestPathInfo(httpMethod, url);
        // 4. 处理路径信息并将其添加到映射表中
        handlePathInfo(requestPathInfo, requestMappingClass, method, methodParamMap);
    }

    /**
     * 处理路径信息并将其添加到映射表中
     *
     * @param requestPathInfo
     * @param requestMappingClass
     * @param method
     * @param methodParamMap
     */
    private void handlePathInfo(RequestPathInfo requestPathInfo, Class<?> requestMappingClass, Method method, Map<String, Class<?>> methodParamMap) {
        if (pathControllerMethodMap.containsKey(requestPathInfo)) {
            log.warn("Duplicate URL:{} registration, current class {} method {} will override the former one",
                    requestPathInfo.getHttpPath(), requestMappingClass.getName(), method.getName());
        }
        ControllerMethod controllerMethod = new ControllerMethod(requestMappingClass, method, methodParamMap);
        pathControllerMethodMap.put(requestPathInfo, controllerMethod);
    }

    /**
     * 处理参数级别的 @RequestParam 注解
     *
     * @param parameter
     * @param methodParamMap
     */
    private void processParameterAnnotation(Parameter parameter, Map<String, Class<?>> methodParamMap) {
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        // 简化实现，controller里所有的参数都需要标注注解
        if (requestParam == null) {
            throw new RuntimeException("The parameter must have @RequestParam annotation");
        }
        methodParamMap.put(requestParam.value(), parameter.getType());
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        // 1. 解析HttpServletRequest的请求方法，请求路径，获取对应的ControllerMethod实例
        String requestMethod = requestProcessorChain.getRequestMethod();
        String requestPath = requestProcessorChain.getRequestPath();
        ControllerMethod controllerMethod = pathControllerMethodMap.get(new RequestPathInfo(requestMethod, requestPath));
        if (controllerMethod == null) {
            requestProcessorChain.setResultRender(new ResourceNotFoundResultRender(requestMethod, requestPath));
            return false;
        }

        // 2. 解析请求参数，并传递给获取到的ControllerMethod实例去执行
        Object result = invokeControllerMethod(controllerMethod, requestProcessorChain.getRequest());

        // 3. 根据处理的结果，选择对应的render进行渲染
        setResultRender(result, controllerMethod, requestProcessorChain);
        return false;
    }

    /**
     * 根据不同情况设置不同的渲染器
     *
     * @param result
     * @param controllerMethod
     * @param requestProcessorChain
     */
    private void setResultRender(Object result, ControllerMethod controllerMethod, RequestProcessorChain requestProcessorChain) {
        if (result == null) return;
        ResultRender resultRender;
        boolean isJson = controllerMethod.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if (isJson) {
            resultRender = new JsonResultRender(result);
        } else {
            resultRender = new ViewResultRender(result);
        }
        requestProcessorChain.setResultRender(resultRender);
    }

    /**
     * 执行对应Controller的方法
     *
     * @param controllerMethod
     * @param request
     * @return
     */
    private Object invokeControllerMethod(ControllerMethod controllerMethod, HttpServletRequest request) {
        // 1. 从请求里获取GET或者POST的参数名及其对应的值
        Map<String, String> requestParamMap = new HashMap<>();
        // GET,POST方法的请求参数获取方式
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> parameter : parameterMap.entrySet()) {
            if (!ValidationUtil.isEmpty(parameter.getValue())) {
                // 只支持一个参数对应一个值的形式
                requestParamMap.put(parameter.getKey(), parameter.getValue()[0]);
            }
        }

        // 2. 根据获取到的请求参数名及其对应的值，以及controllerMethod里面的参数和类型的映射关系，去实例化出参数列表
        List<Object> methodParams = new ArrayList<>();
        Map<String, Class<?>> methodParamMap = controllerMethod.getMethodParameters();
        for (String paramName : methodParamMap.keySet()) {
            Class<?> type = methodParamMap.get(paramName);
            String requestParamValue = requestParamMap.get(paramName);
            Object value;
            // 只支持String以及基础类型char,int,short,byte,double,long,float,boolean及它们的包装类型
            if (requestParamValue == null) {
                // 将请求里的参数值转成适配于参数类型的空值
                value = ConverterUtil.primitiveNull(type);
            } else {
                value = ConverterUtil.convert(type, requestParamValue);
            }
            methodParams.add(value);
        }

        // 3. 执行Controller里面对应的方法并返回结果
        Object controller = beanContainer.getBean(controllerMethod.getControllerClass());
        Method invokeMethod = controllerMethod.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        try {
            if (methodParams.size() == 0) {
                result = invokeMethod.invoke(controller);
            } else {
                result = invokeMethod.invoke(controller, methodParams.toArray());
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        }
        return result;
    }
}
