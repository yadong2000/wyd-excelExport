package exceltest;

import com.convenient.excel.export.generation.ExcelExportGenerate;
import com.convenient.excel.export.generation.ExcelMultipleSheetGenerate;
import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException, NoSuchFieldException, NotFoundException, IllegalAccessException, CannotCompileException, ClassNotFoundException {
//        exportMultpileExcel(args);
        exportExcel(args);
//        exportMultpileExcelMore(args);
    }

//    @org.junit.Test
    public static void exportExcel(String[] args) throws NoSuchFieldException, IllegalAccessException,
            NotFoundException, IOException, CannotCompileException, ClassNotFoundException {
        List list = new ArrayList();
        ExcelExportDemo demo = new ExcelExportDemo();
        demo.setAdrress("333333333333");
        demo.setName("wangyadong");
        demo.setAge("12");
        demo.setType("111");
        List<ExcelExportDemo.Pv> pvs = new ArrayList<>(2);
        ExcelExportDemo.Pv pv = new ExcelExportDemo.Pv();
        pv.setCurrent("1");
        pv.setVolt("2");
        ExcelExportDemo.Pv pv1 = new ExcelExportDemo.Pv();
        pv1.setCurrent("3");
        pv1.setVolt("4");
        pvs.add(pv);
        pvs.add(pv1);
        demo.setPvList(pvs);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ExcelExportDemo demo1 = new ExcelExportDemo();
        demo1.setAdrress("2222222221");
        demo1.setName("gaofangfang");
        demo1.setAge("31");
        demo1.setType("2222");
        List<ExcelExportDemo.Pv> pvs1 = new ArrayList<>(2);
        ExcelExportDemo.Pv pv2 = new ExcelExportDemo.Pv();
        pv2.setCurrent("1.2");
        pv2.setVolt("2.2");
        ExcelExportDemo.Pv pv3 = new ExcelExportDemo.Pv();
        pv3.setCurrent("3.2");
        pv3.setVolt("4.3");
        pvs1.add(pv3);
        pvs1.add(pv2);
        demo1.setPvList(pvs1);
        list.add(demo);
        list.add(demo1);
        new ExcelExportGenerate()
                .generateXssfWorkook()//设置excel版本
                .generateListField(demo.getPvList())
                .generateHead(ExcelExportDemo.class.getName())//设置表头
                .generateBody(list)//设置主体
                .generateFileName()//生成文件名字，测试用,文件路径默认是c://convenient.excel.export/convenient_export_id.xlsx
                .generateFile();//文件写入，测试用，不用加后缀
    }


    public static void exportMultpileExcel(String[] args) throws IllegalAccessException, IOException, NotFoundException, NoSuchFieldException, CannotCompileException, ClassNotFoundException {
        ExcelMultipleSheetGenerate generate = new ExcelMultipleSheetGenerate();
        generate.generateMultipleSheet(ExcelExportDemo.class, ExcelExportTest.class);
        List<ExcelExportDemo> listDemo = new ArrayList();
        ExcelExportDemo demo = new ExcelExportDemo();
        demo.setAdrress("淀海京北国啊啊实打实大苏打啊实打实大苏打实打实撒打算大苏打阿萨大撒大撒大苏打阿斯顿啊实打实的阿三中");
        demo.setName("白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪白垩纪");
        listDemo.add(demo);
        List<ExcelExportTest> listTest = new ArrayList<>();
        ExcelExportTest test = new ExcelExportTest();
        test.setEye("黑眼睛");
        test.setHead("虎头");
        listTest.add(test);
        generate.generateOrder(listDemo, listTest);//按照入参顺序生成

    }

    public static void exportMultpileExcelMore(String[] args) throws IllegalAccessException, IOException, NotFoundException, NoSuchFieldException, CannotCompileException, ClassNotFoundException {
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
//        generate.generateOne(listTest);//每次只生成一个sheet页，可以设置为异步生成，提升效率

    }
}
