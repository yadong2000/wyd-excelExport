package com.convenient.excel.export.generation;


import com.convenient.excel.export.constant.ExcelVersionEnum;
import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ExcelMultipleSheetGenerate {

    private List<Class> list = new ArrayList();
    private ExcelExportGenerate generate;
    private AtomicInteger startIndex = new AtomicInteger(0);


    public ExcelMultipleSheetGenerate() {
        this.generate = new ExcelExportGenerate();
    }

    public ExcelMultipleSheetGenerate(ExcelVersionEnum versionEnum) {
        if (versionEnum.equals(ExcelVersionEnum.HSSF)) {
            this.generate = new ExcelExportGenerate(versionEnum);
        }
    }

    public void generateMultipleSheet(Class... classes) {
        if (classes == null || classes.length == 0) {
            throw new IllegalArgumentException("Generate excel class must not null");
        }
        this.list = Arrays.stream(classes).collect(Collectors.toList());
    }


    public static void asGenerate(Class... classes) {

    }


    /**
     * 生成多sheet页，参数传入list集合，生成顺序和
     * generateMultipleSheet(Class... classes)一致
     *
     * @param lists
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NotFoundException
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws CannotCompileException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @See generateMultipleSheet
     */
    public void generateOrder(List... lists) throws NoSuchFieldException, IllegalAccessException, NotFoundException, IOException {
        if (lists == null || lists.length == 0) {
            throw new IllegalArgumentException("Generate excel list must not null and must not be empty");
        }
        if (lists.length != this.list.size()) {
            throw new IllegalArgumentException("Generate excel data list size must  equal class list size");
        }

        for (int i = 0; i < this.list.size(); i++) {
            this.generate
                    .setClassPool(this.list.get(i))
                    .generateHead(this.list.get(i).getName())//设置表头
                    .generateBody(lists[i])//设置主体
                    .generateFileName()//生成文件名字，测试用,文件路径默认是c://convenient.excel.export/convenient_export_id.xlsx
                    .generateFile();//文件写入，测试用，不用加后缀
        }
    }


    public void generateOne(List list) throws NoSuchFieldException, IllegalAccessException, NotFoundException, IOException {
        this.generate
                .setClassPool(this.list.get(startIndex.get()))
                .generateHead(this.list.get(startIndex.get()).getName())//设置表头
                .generateBody(list)//设置主体
                .generateFileName()//生成文件名字，测试用,文件路径默认是c://convenient.excel.export/convenient_export_id.xlsx
                .generateFile();//文件写入，测试用，不用加后缀
        startIndex.getAndIncrement();
    }


}
