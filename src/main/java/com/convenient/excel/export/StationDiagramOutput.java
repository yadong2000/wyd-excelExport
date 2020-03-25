package com.convenient.excel.export;


import com.convenient.excel.export.annotation.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ExcelISheetFiled(name = "sheet1", headRow = 3)
public class StationDiagramOutput implements Serializable {

    private Long id;

    @ExcelImportFiled(startRow = 0, endRow = 2, startCell = 0, endCell = 0, title = "电站名称")
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    private String stationName;// String 电站名称

    @ExcelImportFiled(startRow = 0, endRow = 2, startCell = 1, endCell = 1, title = "统计时段")
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    private String date;// String 统计时段

    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 2, endCell = 2, title = "水平面辐射总量(MJ/㎡)")
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String resourceValue;// Decimal 水平面辐射总量(MJ/㎡)

    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 3, endCell = 3, title = "倾斜面辐射总量(MJ/㎡)")
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String slopeAccRadiationSum;// Decimal 倾斜面辐射总量(MJ/㎡)

    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 4, endCell = 4, title = "峰值日照时数(h)")
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String topSunshineHours;// Integer 峰值日照时数(h)

    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 5, endCell = 5, title = "理论发电量  (万kWh)")
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String theoryPower;// Decimal 理论发电量 (万kWh)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 6, endCell = 6, title = "逆变器发电量(万kWh)")
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String genInverter;// Decimal 逆变器发电量(万kWh)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 7, endCell = 7, title = "集电线路发电量 (万kWh)")
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String genIntegrated;// Decimal 集电线路发电量 (万kWh)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 8, endCell = 8, title = "上网电量(万kWh)")
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String genInternet;// Decimal 上网电量(万kWh)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 9, endCell = 9, title = "购网电量(万kWh)")
    private String buyPower;// Decimal 购网电量(万kWh)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 10, endCell = 10, title = "等效利用小时数(h)")
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String equivalentHours;// Integer 等效利用小时数(h)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 11, endCell = 11, title = "综合厂用电量(万kWh)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String comPlantPower;// Decimal

    // 综合厂用电量(万kWh)[逆变器发电量+主变高压侧下网发电量（反向有功）-主变高压侧下网发电量（正向有功）]

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 12, endCell = 12, title = "综合厂用电率(%)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String complantPowerRate;// Decimal 综合厂用电率 (%)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 13, endCell = 13, title = "厂用电量(万kWh)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String dayPlantUsePower;// Decimal 厂用电量(万kWh)

    /**
     * 自己算
     */
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 14, endCell = 14, title = "厂用电率(%)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String plantPowerRate;// Decimal 厂用电率 (%)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 15, endCell = 15, title = "站用变电量(万kWh)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String genPlantConsume;// Decimal 站用变电量(万kWh)

    /**
     * 厂损率(%) 自己算
     */
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 16, endCell = 16, title = "厂损率(%)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String plantLossRate;// Decimal 厂损率(%)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 17, endCell = 17, title = "光伏方阵吸收损耗等效时(h)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String pvMatrixLossHours;// Integer

    // 光伏方阵吸收损耗等效时(h)[[(理论发电量-逆变器发电量）/逆变器转换效率]/装机容量]

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 18, endCell = 18, title = "逆变器损耗等效时(h)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String inverterLostEH;// Integer 逆变器损耗等效时(h)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 19, endCell = 19, title = "集电线路及箱变损耗等效时(h)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String integratedLostEH;// Integer 集电线路及箱变损耗等效时(h)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 20, endCell = 20, title = "升压站损耗等效时(h)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String internetLostEH;// Integer 升压站损耗等效时(h)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 2, endRow = 2, startCell = 21, endCell = 21, title = "功率值（MW）", exclue = false)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String outputPowerMax;// Decimal 最大出力功率值(MW)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 2, endRow = 2, startCell = 22, endCell = 22, title = "对应时间", exclue = false)
    private String dayPowerMaxTime;// outputTimeMax;//String 最大出力对应时间(HH:MM:SS)

    /**
     * 自己算
     */
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 23, endCell = 23, title = "综合效率(%)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String comPR;// Decimal 综合效率（PR)(%)

    /**
     * 1-（∑逆变器故障小时数/装机台数*24）
     */
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 24, endCell = 24, title = "可利用率(%)", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String stationAvailability;// Decimal 可利用率(%)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 25, endCell = 25, title = "节省标准煤（吨）", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String markCoal;// Decimal 节省标准煤(吨)

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 26, endCell = 26, title = "减排二氧化碳（吨）", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String carbonDioxide;// Decimal 减排二氧化碳(吨)

    // 能耗指标 .汇总
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 0, endRow = 0, startCell = 5, endCell = 10, title = "电量指标", exclue = true,
            hasValue = false)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private transient String powerInditor;

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 0, endRow = 0, startCell = 2, endCell = 4, title = "资源指标", exclue = true,
            hasValue = false)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private transient String resourceInditor;

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 0, endRow = 0, startCell = 11, endCell = 16, title = "能耗指标", exclue = true,
            hasValue = false)
    private transient String ggg;

    // 损耗指标
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 0, endRow = 0, startCell = 17, endCell = 20, title = "损耗指标", exclue = true,
            hasValue = false)
    private transient String ttt;

    // 运行水平指标 16
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 0, endRow = 0, startCell = 21, endCell = 24, title = "运行水平指标", exclue = true,
            hasValue = false)
    private transient String yyy;

    // 减排量
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 0, endRow = 0, startCell = 25, endCell = 26, title = "减排量", exclue = true,
            hasValue = false)
    private transient String yyyyy;

    // //最大出力
    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 1, startCell = 21, endCell = 22, title = "最大出力", exclue = true)
    private transient String jjjj;

    /////////////////////////

    @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER,
            fontName = "", fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
    @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 27, endCell = 27, title = "总损失电量", exclue = true)
    @ExcelIDataFormatFiled(dataFormat = "0.00")
    private String total;

    // 拿到这个字段后 删除在正常列表
    @ExcelListField(startRow = 0, endRow = 1, cellDistance = 1, includeField = "total", beforeField = "markCoal",
            afterField = "stationAvailability", totalTitle = "损失电量", titleStartRow = 0, titleEndRow = 0)
    List<SubLostPower> lostList = new ArrayList<>();

    // @Data
    // public static class LostPower implements Serializable {
    // private BigDecimal total;
    // private List<SubLostPower> lostList = new ArrayList<>();
    // }


    public static class SubLostPower implements Serializable {

        // @ExcelIBodyStyle(horizontalAlignment = HorizontalAlignment.CENTER,
        // verticalAlignment = VerticalAlignment.CENTER,
        // fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
        // @ExcelExportHead(startRow = 0, endRow = 0, startCell = 25, endCell = 26, title
        // = "分项损失电量名称")
        @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER,
                verticalAlignment = VerticalAlignment.CENTER, fontName = "", fontHeightInPoints = 500, rowHight = 500,
                columnWidth = 10, wrapText = true)
        @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 21,
                endCell = 22, title = "分项损失电量名称", exclue = true, isKey = "power")
         @ExcelIDataFormatFiled(dataFormat = "0.00")
        private String name;

        // @ExcelIBodyStyle(horizontalAlignment = HorizontalAlignment.CENTER,
        // verticalAlignment = VerticalAlignment.CENTER,
        // fontHeightInPoints = 500, rowHight = 500, columnWidth = 10, wrapText = true)
        // @ExcelExportHead(startRow = 0, endRow = 0, startCell = 25, endCell = 26, title
        // = "损失电量")
        @ExcelIStyleFiled(horizontalAlignment = HorizontalAlignment.CENTER,
                verticalAlignment = VerticalAlignment.CENTER, fontName = "", fontHeightInPoints = 500, rowHight = 500,
                columnWidth = 10, wrapText = true)
        @ExcelImportFiled(startRow = 1, endRow = 2, startCell = 21, endCell = 22, title = "分项损失电量",
                exclue = true,hidden=true)
        @ExcelIDataFormatFiled(dataFormat = "0.00")
        private String power;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
    }

    public String getSlopeAccRadiationSum() {
        return slopeAccRadiationSum;
    }

    public void setSlopeAccRadiationSum(String slopeAccRadiationSum) {
        this.slopeAccRadiationSum = slopeAccRadiationSum;
    }

    public String getTopSunshineHours() {
        return topSunshineHours;
    }

    public void setTopSunshineHours(String topSunshineHours) {
        this.topSunshineHours = topSunshineHours;
    }

    public String getTheoryPower() {
        return theoryPower;
    }

    public void setTheoryPower(String theoryPower) {
        this.theoryPower = theoryPower;
    }

    public String getGenInverter() {
        return genInverter;
    }

    public void setGenInverter(String genInverter) {
        this.genInverter = genInverter;
    }

    public String getGenIntegrated() {
        return genIntegrated;
    }

    public void setGenIntegrated(String genIntegrated) {
        this.genIntegrated = genIntegrated;
    }

    public String getGenInternet() {
        return genInternet;
    }

    public void setGenInternet(String genInternet) {
        this.genInternet = genInternet;
    }

    public String getBuyPower() {
        return buyPower;
    }

    public void setBuyPower(String buyPower) {
        this.buyPower = buyPower;
    }

    public String getEquivalentHours() {
        return equivalentHours;
    }

    public void setEquivalentHours(String equivalentHours) {
        this.equivalentHours = equivalentHours;
    }

    public String getComPlantPower() {
        return comPlantPower;
    }

    public void setComPlantPower(String comPlantPower) {
        this.comPlantPower = comPlantPower;
    }

    public String getComplantPowerRate() {
        return complantPowerRate;
    }

    public void setComplantPowerRate(String complantPowerRate) {
        this.complantPowerRate = complantPowerRate;
    }

    public String getDayPlantUsePower() {
        return dayPlantUsePower;
    }

    public void setDayPlantUsePower(String dayPlantUsePower) {
        this.dayPlantUsePower = dayPlantUsePower;
    }

    public String getPlantPowerRate() {
        return plantPowerRate;
    }

    public void setPlantPowerRate(String plantPowerRate) {
        this.plantPowerRate = plantPowerRate;
    }

    public String getGenPlantConsume() {
        return genPlantConsume;
    }

    public void setGenPlantConsume(String genPlantConsume) {
        this.genPlantConsume = genPlantConsume;
    }

    public String getPlantLossRate() {
        return plantLossRate;
    }

    public void setPlantLossRate(String plantLossRate) {
        this.plantLossRate = plantLossRate;
    }

    public String getPvMatrixLossHours() {
        return pvMatrixLossHours;
    }

    public void setPvMatrixLossHours(String pvMatrixLossHours) {
        this.pvMatrixLossHours = pvMatrixLossHours;
    }

    public String getInverterLostEH() {
        return inverterLostEH;
    }

    public void setInverterLostEH(String inverterLostEH) {
        this.inverterLostEH = inverterLostEH;
    }

    public String getIntegratedLostEH() {
        return integratedLostEH;
    }

    public void setIntegratedLostEH(String integratedLostEH) {
        this.integratedLostEH = integratedLostEH;
    }

    public String getInternetLostEH() {
        return internetLostEH;
    }

    public void setInternetLostEH(String internetLostEH) {
        this.internetLostEH = internetLostEH;
    }

    public String getOutputPowerMax() {
        return outputPowerMax;
    }

    public void setOutputPowerMax(String outputPowerMax) {
        this.outputPowerMax = outputPowerMax;
    }

    public String getDayPowerMaxTime() {
        return dayPowerMaxTime;
    }

    public void setDayPowerMaxTime(String dayPowerMaxTime) {
        this.dayPowerMaxTime = dayPowerMaxTime;
    }

    public String getComPR() {
        return comPR;
    }

    public void setComPR(String comPR) {
        this.comPR = comPR;
    }

    public String getStationAvailability() {
        return stationAvailability;
    }

    public void setStationAvailability(String stationAvailability) {
        this.stationAvailability = stationAvailability;
    }

    public String getMarkCoal() {
        return markCoal;
    }

    public void setMarkCoal(String markCoal) {
        this.markCoal = markCoal;
    }

    public String getCarbonDioxide() {
        return carbonDioxide;
    }

    public void setCarbonDioxide(String carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    public String getPowerInditor() {
        return powerInditor;
    }

    public void setPowerInditor(String powerInditor) {
        this.powerInditor = powerInditor;
    }

    public String getResourceInditor() {
        return resourceInditor;
    }

    public void setResourceInditor(String resourceInditor) {
        this.resourceInditor = resourceInditor;
    }

    public String getGgg() {
        return ggg;
    }

    public void setGgg(String ggg) {
        this.ggg = ggg;
    }

    public String getTtt() {
        return ttt;
    }

    public void setTtt(String ttt) {
        this.ttt = ttt;
    }

    public String getYyy() {
        return yyy;
    }

    public void setYyy(String yyy) {
        this.yyy = yyy;
    }

    public String getYyyyy() {
        return yyyyy;
    }

    public void setYyyyy(String yyyyy) {
        this.yyyyy = yyyyy;
    }

    public String getJjjj() {
        return jjjj;
    }

    public void setJjjj(String jjjj) {
        this.jjjj = jjjj;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<SubLostPower> getLostList() {
        return lostList;
    }

    public void setLostList(List<SubLostPower> lostList) {
        this.lostList = lostList;
    }
}
