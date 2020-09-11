package com.convenient.excel.config;

import com.convenient.excel.controller.ExcelExportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Config {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ExcelExportHandler excelExportHandler) {
        RouterFunction<ServerResponse> personRoute =
                route(POST("/select/excel/body").and(accept(APPLICATION_JSON)), excelExportHandler::selectExcelBody)
                        .andRoute(POST("/select/excel/main").and(contentType(APPLICATION_JSON)), excelExportHandler::insertMain)
                        .andRoute(POST("/insert/excel/sheet").and(contentType(APPLICATION_JSON)), excelExportHandler::insertSheet)
                        .andRoute(POST("/insert/excel/main").and(contentType(APPLICATION_JSON)), excelExportHandler::insertMain)
                        .andRoute(POST("/insert/excel/fifter").and(contentType(APPLICATION_JSON)), excelExportHandler::insertFifter)
                        .andRoute(POST("/insert/excel/cell").and(contentType(APPLICATION_JSON)), excelExportHandler::insertCell)
                        .andRoute(POST("/insert/excel/field").and(contentType(APPLICATION_JSON)), excelExportHandler::insertField)
                        .andRoute(PUT("/update/excel/sheet").and(contentType(APPLICATION_JSON)), excelExportHandler::updatetSheet)
                        .andRoute(PUT("/update/excel/main").and(contentType(APPLICATION_JSON)), excelExportHandler::updateMain)
                        .andRoute(PUT("/update/excel/fifter").and(contentType(APPLICATION_JSON)), excelExportHandler::updateExcelfifter)
                        .andRoute(PUT("/update/excel/cell").and(contentType(APPLICATION_JSON)), excelExportHandler::updateCell)
                        .andRoute(PUT("/update/excel/field").and(contentType(APPLICATION_JSON)), excelExportHandler::updateField)
                        .andRoute(DELETE("/delete/excel/sheet").and(contentType(APPLICATION_JSON)), excelExportHandler::deleteSheet)
                        .andRoute(DELETE("/delete/excel/main").and(contentType(APPLICATION_JSON)), excelExportHandler::deleteMain)
                        .andRoute(DELETE("/delete/excel/fifter").and(contentType(APPLICATION_JSON)), excelExportHandler::deleteExcelfifter)
                        .andRoute(DELETE("/delete/excel/cell").and(contentType(APPLICATION_JSON)), excelExportHandler::deleteCell)
                        .andRoute(DELETE("/delete/excel/field").and(contentType(APPLICATION_JSON)), excelExportHandler::deleteField)
                        .andRoute(DELETE("/export/excel").and(contentType(APPLICATION_JSON)), excelExportHandler::export);
        return personRoute;
    }
}