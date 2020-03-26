package com.convenient.excel.export.excel.parse.rx;

import com.convenient.excel.export.excel.parse.ExcelExportClassParse;
import io.reactivex.Observable;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;

public class RxExcelExportClassParse implements ExcelExportClassParse {
    @Override
    public <T> T getAnnotation(Class clazz, String fieldName, String annotationName, String key) {
        return null;
    }

    @Override
    public <T> T getClassAnnotation(Class clazz, String annotationName, String key) throws NotFoundException {
        if (clazz == null || StringUtils.isBlank(key) || StringUtils.isBlank(annotationName)) {
            return null;
        }
//        Observable<Class> clazzobservable = Observable.just(clazz);
//        Observable<String> annotationNameobservable = Observable.just(annotationName);
//        Observable<String> keyobservable = Observable.just(key);
//        Observable<String> mergeWith = keyobservable.mergeWith(annotationNameobservable);
//        mergeWith.filter(m->StringUtils.isNotBlank(m))
        return null;
    }

    @Override
    public <T> T getListFieldAnnotation(Class clazz, String annotationName) throws NotFoundException {
        return null;
    }
}
