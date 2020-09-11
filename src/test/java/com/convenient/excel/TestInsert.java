package com.convenient.excel;


import com.convenient.excel.beans.entity.ExcelGetway;
import com.convenient.excel.util.UniqueIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.ReactiveInsertOperation;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;


@SpringBootTest(classes = WydExportApplication.class)
@RunWith(SpringRunner.class)
public class TestInsert {


    @Autowired
    ReactiveInsertOperation insertOperation;

    @Test
    public void testInsert() throws Exception {
        ExcelGetway excelGetway = new ExcelGetway();
        long uniqueID = UniqueIDUtil.getUniqueID();
        excelGetway.setGetwayId(uniqueID);
        ExcelGetway excelGetway1 = new ExcelGetway();
        long uniqueID1 = UniqueIDUtil.getUniqueID();
        excelGetway1.setGetwayId(uniqueID1);
        Mono<ExcelGetway> then = insertOperation.insert(ExcelGetway.class).using(excelGetway1)
                .then(insertOperation.insert(ExcelGetway.class).using(excelGetway));
        then.block();
    }

    public void main(String[] args) {
        Mono<ExcelGetway> then = insertOperation.insert(ExcelGetway.class).using(new ExcelGetway())
                .then(insertOperation.insert(ExcelGetway.class).using(new ExcelGetway()));
        then.block();

    }

}
