package com.convenient.excel.export.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileAlreadyExistsException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class ExcelConvenientFileUtil {

    public final static String CONVENIENT_EXPORT_NAME = "convenient_export";
    public final static String CONVENIENT_EXPORT_PATH = "C:\\convenient.excel.export\\";
    public final static String CONVENIENT_EXPORT_LOCK_PATH = CONVENIENT_EXPORT_PATH + "lock\\";
    private final static AtomicLong atomicLong = new AtomicLong(1);

    public static File fileNoExsitsMake(File file, String filepath) {
        if (file == null) {
            file = new File(filepath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }


    public static File fileNoExsitsMake(String filepath) throws IOException {
        if (filepath == null && filepath.length() == 0) {
            throw new IllegalArgumentException("路径不能为控股");
        }
        File file = new File(filepath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static void fileExsits(File file) throws FileAlreadyExistsException {
        if (file != null) {
            throw new FileAlreadyExistsException("file alread exsits,plese check it ");
        }
    }


    public static synchronized Long addNum() throws IOException {
        fileNoExsitsMake(null, CONVENIENT_EXPORT_LOCK_PATH);
        File file = fileNoExsitsMake(CONVENIENT_EXPORT_LOCK_PATH + "lock.lock");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[1024];
            int end = 0;
            while (end != -1) {
                end = fileInputStream.read(bytes);
            }
            String stringConut = new String(bytes);
            if (stringConut == null || stringConut.length() == 0 || stringConut.trim().equals("")) {
                stringConut = "0";
            }
            Long aLong = Long.parseLong(stringConut.trim());
            atomicLong.set(aLong);
            long increment = atomicLong.incrementAndGet();
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write((increment + "").getBytes());
            }
        }
        return atomicLong.get();
    }

}
