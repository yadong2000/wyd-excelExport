//package exceltest;
//
//import com.convenient.excel.export.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@ExcelISheet(name = "sheet1")
//public class ExcelExportDemo {
//
//
//    @ExcelExportHead(title = "名字", startRow = 0, endRow = 1, startCell = 0, endCell = 3)
//    @ExcelIHeadStyle(wrapText = true, rowHight = 300)
//    @ExcelIBodyStyle(wrapText = true, rowHight = 800)
//    private String name;
//
//    @ExcelExportHead(title = "类型", startRow = 0, endRow = 1, startCell = 4, endCell = 7)
//    @ExcelIHeadStyle(wrapText = true, rowHight = 300)
//    @ExcelIBodyStyle(wrapText = true, rowHight = 800)
//    private String type;
//
//    @ExcelExportHead(title = "年龄", startRow = 0, endRow = 1, startCell = 8, endCell = 9)
//    @ExcelIHeadStyle(wrapText = true, rowHight = 300)
//    @ExcelIBodyStyle(wrapText = true, rowHight = 800)
//    private String age;
//
//    @ExcelListField(startIndex = 10, rowDistance = 1, cellDistance = 2)
//    private List<Pv> pvList = new ArrayList<>();
//
//
//    @ExcelExportHead(title = "地址", startRow = 0, endRow = 1, startCell = 11, endCell = 12)
//    @ExcelIHeadStyle(wrapText = true, rowHight = 300)
//    @ExcelIBodyStyle(wrapText = true, rowHight = 800)
//    private String adrress;
//
//
//    public static class Pv {
//        @ExcelExportHead(title = "电流", startRow = 0, endRow = 1, isListField = true)
//        @ExcelIHeadStyle(wrapText = true, rowHight = 300)
//        @ExcelIBodyStyle(wrapText = true, rowHight = 800)
//        private String current;
//        @ExcelExportHead(title = "电压", startRow = 0, endRow = 1, isListField = true)
//        @ExcelIHeadStyle(wrapText = true, rowHight = 300)
//        @ExcelIBodyStyle(wrapText = true, rowHight = 800)
//        private String volt;
//
//        public String getCurrent() {
//            return current;
//        }
//
//        public void setCurrent(String current) {
//            this.current = current;
//        }
//
//        public String getVolt() {
//            return volt;
//        }
//
//        public void setVolt(String volt) {
//            this.volt = volt;
//        }
//    }
//
//    public List<Pv> getPvList() {
//        return pvList;
//    }
//
//    public void setPvList(List<Pv> pvList) {
//        this.pvList = pvList;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
//
//    public String getAdrress() {
//        return adrress;
//    }
//
//    public void setAdrress(String adrress) {
//        this.adrress = adrress;
//    }
//}
