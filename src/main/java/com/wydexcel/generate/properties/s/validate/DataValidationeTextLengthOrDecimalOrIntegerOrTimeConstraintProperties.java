package com.wydexcel.generate.properties.s.validate;

public class DataValidationeTextLengthOrDecimalOrIntegerOrTimeConstraintProperties extends ExcelDataValidationBaseProperties {
    private int operatorType;
    private String formula1;
    private String formula2;

    public int getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(int operatorType) {
        this.operatorType = operatorType;
    }

    public String getFormula1() {
        return formula1;
    }

    public void setFormula1(String formula1) {
        this.formula1 = formula1;
    }

    public String getFormula2() {
        return formula2;
    }

    public void setFormula2(String formula2) {
        this.formula2 = formula2;
    }
}