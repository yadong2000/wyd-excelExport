package com.wydexcel.generate.properties.s;

import com.wydexcel.generate.exception.ExcelParseException;
import com.wydexcel.generate.process.Process;
import com.wydexcel.generate.process.context.ExcelContextAllContext;

public  class ExcelCellBase<T extends Process> {

    private String fieldName;
    private String type;
    private String id;

    private int isHeader;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(int isHeader) {
        this.isHeader = isHeader;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (null == id || "".equals(id.trim()) || id.contains(",")) {
            throw new ExcelParseException("set excel base properties error, id must not null and is not allow , ");
        }
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return this.fieldName + this.isHeader;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void baseValidate() {
        if (null == this.id || "".equals(id)) {
            if (null == this.fieldName || "".equals(fieldName)) {
                throw new ExcelParseException("fieldName 不可以为空");
            }
        }
    }


    public void baseValidate(boolean isUsing) {
        if (!isUsing) {
            if (null == this.fieldName || "".equals(fieldName)) {
                throw new ExcelParseException("fieldName 不可以为空");
            }
        } else {
            if ((getType() == null || "".equals(getType()))) {
                throw new ExcelParseException("Type 不可以为空");
            }
        }
    }

    public T earnProcess(String type) {
        return (T) earnExcelContext().getProcessMap().get(type);
    }


    private transient ExcelContextAllContext excelContextAllContext;

    public ExcelContextAllContext earnExcelContext() {
        if (null == excelContextAllContext) {
            return excelContextAllContext = ExcelContextAllContext.getInstance();
        }
        return excelContextAllContext;
    }
}
