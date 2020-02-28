package exceltest;

import com.convenient.excel.export.annotation.ExcelExportHead;
import com.convenient.excel.export.annotation.ExcelISheet;


@ExcelISheet(name = "sheet2")
public class ExcelExportTest {


    @ExcelExportHead(title = "头部", startRow = 0, endRow = 2, startCell = 0, endCell = 3)
    private String head;

    @ExcelExportHead(title = "眼睛", startRow = 0, endRow = 1, startCell = 4, endCell = 6)
    private String eye;

    @ExcelExportHead(title = "鼻子", startRow = 0, endRow = 1, startCell = 7, endCell = 8)
    private String norse;

    @ExcelExportHead(title = "嘴", startRow = 0, endRow = 1, startCell = 9, endCell = 11)
    private String month;


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getNorse() {
        return norse;
    }

    public void setNorse(String norse) {
        this.norse = norse;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
