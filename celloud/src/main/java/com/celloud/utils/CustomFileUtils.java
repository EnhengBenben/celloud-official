package com.celloud.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomFileUtils extends FileUtils {
    private static Logger logger = LoggerFactory.getLogger(CustomFileUtils.class);

    /**
     * 使用utf-8编码读取文件中符合条件的行
     * 
     * @param file
     *            要读取的文件
     * @param filter
     *            过滤条件,如filter为null，则读取所有的行
     * @return 如读取文件异常，则返回null，否则返回包含所有符合条件的行的list(list可能为empty)
     */
    public static List<String> readLines(File file, FileLineFilter filter) {
        return readLines(file, null, filter);
    }

    /**
     * 使用指定编码读取文件中符合条件的行
     * 
     * @param file
     *            要读取的文件
     * @param encoding
     *            指定的读取文件的编码，如为null，则默认使用utf-8
     * @param filter
     *            过滤条件,如filter为null，则读取所有的行
     * @return 如读取文件异常，则返回null，否则返回包含所有符合条件的行的list(list可能为empty)
     */
    public static List<String> readLines(File file, String encoding, FileLineFilter filter) {
        List<String> list = new ArrayList<>();
        try {
            if (filter == null) {
                return readLines(file, encoding);
            }
            BufferedReader reader = openBufferReader(file, encoding);
            if (reader == null) {
                return null;
            }
            String line = null;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                if (filter != null && filter.filter(lineNumber, line)) {
                    list.add(line);
                }
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            logger.error("读取文件异常：{}", file.getAbsolutePath(), e);
            return null;
        }
        return list;
    }

    /**
     * 使用utf-8编码读取文件中的指定行，行号从0开始
     * 
     * @param file
     *            要读取的文件
     * @param lineNumbersStartByZero
     *            要读取的行号，如为null，则返回null
     * @return 如读取文件异常，则返回null，否则返回包含所有符合条件的行的list(list可能为empty)
     */
    public static List<String> readLines(File file, Integer... lineNumbersStartByZero) {
        return readLines(file, null, lineNumbersStartByZero);
    }

    /**
     * 使用指定编码读取文件中的指定行，行号从0开始
     * 
     * @param file
     *            要读取的文件
     * @param encoding
     *            指定的读取文件的编码，如为null，则默认使用utf-8
     * @param lineNumbersStartByZero
     *            要读取的行号，如为null，则返回null
     * @return 如读取文件异常，则返回null，否则返回包含所有符合条件的行的list(list可能为empty)
     */
    public static List<String> readLines(File file, String encoding, Integer... lineNumbersStartByZero) {
        if (lineNumbersStartByZero == null) {
            return null;
        }
        Arrays.sort(lineNumbersStartByZero);
        int maxLineNumber = lineNumbersStartByZero[lineNumbersStartByZero.length - 1];
        List<Integer> lineNumbers = Arrays.asList(lineNumbersStartByZero);
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = openBufferReader(file, encoding);
            if (reader == null) {
                return null;
            }
            String line = null;
            Integer lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                if (lineNumbers.contains(lineNumber)) {
                    list.add(line);
                }
                if (lineNumber >= maxLineNumber) {
                    break;
                }
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            logger.error("读取文件异常：{}", file.getAbsolutePath(), e);
            return null;
        }
        return list;
    }

    /**
     * 使用utf-8编码读取文件的第一行
     * 
     * @param file
     * @return
     */
    public static String readFirstLine(File file, boolean excludeBlankLine) {
        return readFirstLine(file, excludeBlankLine, null);
    }

    /**
     * 使用指定编码读取文件的第一行
     * 
     * @param file
     * @param encoding
     * @return
     */
    public static String readFirstLine(File file, boolean excludeBlankLine, String encoding) {
       
        String firstLine = null;
        try {
            BufferedReader reader = openBufferReader(file, encoding);
            if (reader == null) {
                return null;
            }
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!excludeBlankLine || !line.trim().equals("")) {
                    firstLine = line;
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            logger.error("读取文件异常：{}", file.getAbsolutePath(), e);
            return null;
        }
        return firstLine;
    }

    /**
     * 使用utf-8编码读取文件的最后一行
     * 
     * @param file
     * @return
     */
    public static String readLastLine(File file, boolean excludeBlankLine) {
        return readLastLine(file, excludeBlankLine, null);
    }

    /**
     * 使用指定编码读取文件的最后一行
     * 
     * @param file
     * @param encoding
     * @return
     */
    public static String readLastLine(File file, boolean excludeBlankLine, String encoding) {
        String lastLine = null;
        try {
            BufferedReader reader = openBufferReader(file, encoding);
            if (reader == null) {
                return null;
            }
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!excludeBlankLine || !line.trim().equals("")) {
                    lastLine = line;
                }
            }
            reader.close();
        } catch (IOException e) {
            logger.error("读取文件异常：{}", file.getAbsolutePath(), e);
            return null;
        }
        return lastLine;
    }

    /**
     * 使用BufferedReader打开文件
     * 
     * @param file
     * @param encoding
     * @return
     */
    private static BufferedReader openBufferReader(File file, String encoding) {
        if (encoding == null) {
            encoding = "utf-8";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charsets.toCharset(encoding)));
        } catch (FileNotFoundException e) {
            logger.error("打开文件异常：{}", getFileAbsolutePath(file), e);
        }
        return reader;
    }

    /**
     * 获取文件的绝对路径
     * 
     * @param file
     * @return
     */
    private static String getFileAbsolutePath(File file) {
        return file == null ? null : file.getAbsolutePath();
    }

    public static interface FileLineFilter {
        /**
         * 读取文件的过滤器
         * 
         * @param lineNumberStartByZero
         *            当前行的行号，从0开始
         * @param line
         *            当前行的内容
         * @return 是否符合条件
         */
        public boolean filter(int lineNumberStartByZero, String line);
    }
}
