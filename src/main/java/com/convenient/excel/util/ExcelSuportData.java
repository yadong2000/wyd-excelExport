package com.convenient.excel.util;//package export.com.convenient.excel.beans;
//
//import com.alibaba.fastjson.JSONObject;
//import export.com.convenient.excel.beans.param.ExcelGetwayParameter;
//import lombok.Data;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.function.Predicate;
//
//@Data
//public class ExcelSuportData {
//
//    private Long parameterId;
//    private final ExcelGetwayParameter parameter;
//    public static final Predicate<String> isNotEmpty = (s) -> !StringUtils.isEmpty(s);
//
//
//    public ExcelSuportData(ExcelGetwayParameter parameter) {
//        Assert.isTrue(parameter != null, "excel parameter must not null");
//        this.parameter = parameter;
//    }
//
//
//
//
//    public Long match(final String requestParameter) {
//        return Optional.ofNullable(parameter)
//                .filter(p -> isNotEmpty.test(p.getParameter()) && isNotEmpty.test(requestParameter))
//                .map(p -> {
//                    Map<String, Object> map = JSONObject.parseObject(p.getParameter(), Map.class);
//                    Map<String, Object> requestMap = JSONObject.parseObject(requestParameter, Map.class);
//                    Set<Boolean> set = new HashSet<>();
//                    map.forEach((k, v) -> {
//                        set.add(Optional.ofNullable(requestMap.get(k)).filter(value -> value.toString().equals(v.toString())).isPresent());
//                    });
//                    boolean onlyOne = set.size() == 1;
//                    Optional<Boolean> first = set.stream().filter(f -> f.equals(Boolean.TRUE)).findFirst();
//                    if (first.isPresent() && onlyOne) {
//                        return parameterId = p.getParameter_id();
//                    }
//                    return parameterId = Long.valueOf(-1);
//                }).get();
//    }
//
//
//}
