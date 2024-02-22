package com.wydexcel.generate.process.context;


import com.wydexcel.generate.common.WydExcelReflectUtil;

public class ExcelParserLambdaBuilder<T, D extends ColumnFunction<T, Object>>  {
    private T t;


    public ExcelParserLambdaBuilder( Class c) {
        this.t = (T) c;
    }






    public T getT() {
        return t;
    }

    public String getInstance() {
        return "";
    }

    public ExcelParserLambdaBuilder run(ColumnFunction<T, Object> columnFunction, Object value) {
        String fieldName = WydExcelReflectUtil.getFieldNameByLambda(columnFunction);
        return this;
    }


}
