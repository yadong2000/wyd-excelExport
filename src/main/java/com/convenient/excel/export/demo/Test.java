package com.convenient.excel.export.demo;

import com.convenient.excel.export.generation.ExcelExportGenerate;
import com.convenient.excel.export.generation.ExcelMultipleSheetGenerate;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException, NoSuchFieldException, NotFoundException, IllegalAccessException {
        exportMultpileExcel(args);
    }

    public static void exportExcel(String[] args) throws NoSuchFieldException, IllegalAccessException,
            NotFoundException, IOException {
        List list = new ArrayList<>();
        new ExcelExportGenerate()
                .generateXssfWorkook()//设置excel版本
                .generateHead(ExcelExportDemo.class.getName())//设置表头
                .generateBody(list)//设置主体
                .generateFileName()//生成文件名字，测试用,文件路径默认是c://convenient.excel.export/convenient_export_id.xlsx
                .generateFile();//文件写入，测试用，不用加后缀
    }


    public static void exportMultpileExcel(String[] args) throws IllegalAccessException, IOException, NotFoundException, NoSuchFieldException {
        ExcelMultipleSheetGenerate generate = new ExcelMultipleSheetGenerate();
        generate.generateMultipleSheet(ExcelExportDemo.class, ExcelExportTest.class);
        List<ExcelExportDemo> listDemo = new ArrayList<>();
        ExcelExportDemo demo = new ExcelExportDemo();
        demo.setAdrress("淀海京北国中");
        demo.setName("白垩纪");
        listDemo.add(demo);
        List<ExcelExportTest> listTest = new ArrayList<>();
        ExcelExportTest test = new ExcelExportTest();
        test.setEye("黑眼睛");
        test.setHead("虎头");
        listTest.add(test);
        generate.generateOrder(listDemo, listTest);//按照入参顺序生成
//        generate.generateOne(listDemo);//每次只生成一个sheet页，可以设置为异步生成，提升效率

    }

    public static void exportMultpileExcelMore(String[] args) throws IllegalAccessException, IOException, NotFoundException, NoSuchFieldException {
        ExcelMultipleSheetGenerate generate = new ExcelMultipleSheetGenerate();
        generate.generateMultipleSheet(ExcelExportDemo.class, ExcelExportTest.class);
        List<ExcelExportDemo> listDemo = new ArrayList<>();
        ExcelExportDemo demo = new ExcelExportDemo();
        demo.setAdrress("淀海京北国中");
        demo.setName("白垩纪");
        listDemo.add(demo);
        List<ExcelExportTest> listTest = new ArrayList<>();
        ExcelExportTest test = new ExcelExportTest();
        test.setEye("黑眼睛");
        test.setHead("虎头");
        listTest.add(test);
        generate.generateOne(listDemo);//每次只生成一个sheet页，可以设置为异步生成，提升效率
        generate.generateOne(listTest);//每次只生成一个sheet页，可以设置为异步生成，提升效率

    }
}
