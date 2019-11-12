# Excel 导出工具


@ExcelISheet(name = "sheet1")
public class ExcelExportDemo {


    @ExcelExportHead(title = "名字", startRow = 0, endRow = 1, startCell = 0, endCell = 3)
    @ExcelIHeadStyle(wrapText = true, rowHight = 300)
    @ExcelIBodyStyle(wrapText = true, rowHight = 800)
    private String name;

    @ExcelExportHead(title = "类型", startRow = 0, endRow = 1, startCell = 4, endCell = 7)
    @ExcelIHeadStyle(wrapText = true, rowHight = 300)
    @ExcelIBodyStyle(wrapText = true, rowHight = 800)
    private String type;

    @ExcelExportHead(title = "年龄", startRow = 0, endRow = 1, startCell = 8, endCell = 9)
    @ExcelIHeadStyle(wrapText = true, rowHight = 300)
    @ExcelIBodyStyle(wrapText = true, rowHight = 800)
    private String age;

    @ExcelExportHead(title = "地址", startRow = 0, endRow = 1, startCell = 10, endCell = 12)
    @ExcelIHeadStyle(wrapText = true, rowHight = 300)
    @ExcelIBodyStyle(wrapText = true, rowHight = 800)
    private String adrress;
}
   public static void main(String[] args) throws IOException, NoSuchFieldException, NotFoundException, IllegalAccessException {
        exportMultpileExcel(args);
//        exportExcel(args);
//        exportMultpileExcelMore(args);
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
    
    
    生成的excel如果会生成在C盘下
    
