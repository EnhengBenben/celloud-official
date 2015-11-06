package com.nova.tools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

/**
 * @Description:文件操作工具类
 * @author lin
 * @date 2013-7-29 下午7:36:51
 */
public class FileTools {

    /**
     * 获取文件锁
     * 
     * @param file
     * @return
     */
    public static FileLock getFileLock(File file) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        FileChannel fc = raf.getChannel();
        FileLock fl = null;
        while (true) {
            try {
                fl = fc.tryLock();
                if (fl != null) {
                    break;
                }
                System.out.println("wait");
                Thread.sleep(1000);
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return fl;
    }

    /**
     * 对datakeylist进行排序
     * 
     * @param dataKeyList
     * @return
     */
    public static String dataListSort(String dataKeyList) {
        String[] dataKey = dataKeyList.split(";");
        Map<String, String> map = new HashMap<String, String>();
        String[] array = new String[dataKey.length];
        for (int i = 0; i < dataKey.length; i++) {
            String data = dataKey[i];
            String d[] = data.split(",");
            map.put(d[2] + d[0], data);
            array[i] = d[2] + d[0];
        }
        Arrays.sort(array);
        dataKeyList = "";
        for (String s : array) {
            dataKeyList += map.get(s) + ";";
        }
        return dataKeyList;
    }

    /**
     * HBVSNP图片排序
     * 
     * @param imgList
     * @return
     */
    public static String imgSort(String imgList) {
        String[] img = imgList.split(",");
        Map<String, String> map = new HashMap<String, String>();
        String[] array = new String[img.length];
        for (int i = 0; i < img.length; i++) {
            String data = img[i];
            map.put(data, data);
            array[i] = data;
        }
        Arrays.sort(array);
        imgList = "";
        for (String s : array) {
            imgList += map.get(s) + ",";
        }
        return imgList;
    }

    /**
     * 为NIPT流程而写的处理方法
     * 
     * @param path
     * @return
     */
    // TODO 可扩展
    public static String getColumns(String path) {
        StringBuffer four = new StringBuffer();
        FileReader in = null;
        try {
            in = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LineNumberReader reader = new LineNumberReader(in);
        String line = null;
        int i = 0;
        try {
            while ((line = reader.readLine()) != null) {
                i++;
                if (i > 1) {
                    String val[] = line.split("\t");
                    four.append(val[3]).append("(").append(val[1]).append(")")
                            .append("\t");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return four.toString();
    }

    public static int countFileLines(String path) {
        File test = new File(path);
        long fileLength = test.length();
        LineNumberReader rf = null;
        int lines = 0;
        try {
            rf = new LineNumberReader(new FileReader(test));
            if (rf != null) {
                rf.skip(fileLength);
                lines = rf.getLineNumber();
                rf.close();
            }
        } catch (IOException e) {
            if (rf != null) {
                try {
                    rf.close();
                } catch (IOException ee) {
                }
            }
        }
        return lines;
    }

    public static int countLines(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            return countLines(f);
        } else {
            return 0;
        }
    }

    public static int countLines(File file) {
        if (!file.exists()) {
            return 0;
        }
        BufferedReader br = null;
        int count = 0;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * 获取文件的最后一行
     * 
     * @note 该方法只适用小文件
     * @param filePath
     * @return
     */
    public static String getLastLine(String filePath) {
        FileReader in = null;
        try {
            in = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LineNumberReader reader = new LineNumberReader(in);
        String s = null;
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line.trim())) {
                    s = line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 获取文件的第一行
     * 
     * @param filePath
     * @return
     */
    public static String getFirstLine(String filePath) {
        return getLineByNum(filePath, 1);
    }

    /**
     * 获取文件内指定行的内容
     * 
     * @param filePath
     * @param num
     * @return
     */
    public static String getLineByNum(String filePath, int num) {
        if (!new File(filePath).exists()) {
            return null;
        }
        FileReader in = null;
        try {
            in = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LineNumberReader reader = new LineNumberReader(in);
        String line = null;
        int i = 0;
        try {
            while ((line = reader.readLine()) != null) {
                i++;
                if (i == num) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * 获取文件内指定行的内容
     * 
     * @param filePath
     * @param num
     * @return
     */
    public static List<String> getLineByNum(String filePath, int start, int end) {
        if (!new File(filePath).exists()) {
            return null;
        }
        FileReader in = null;
        try {
            in = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LineNumberReader reader = new LineNumberReader(in);
        String line = null;
        List<String> list = new ArrayList<String>();
        int i = 0;
        try {
            while ((line = reader.readLine()) != null) {
                i++;
                if (i >= start && i <= end) {
                    list.add(line);
                    if (i == end) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 样本名称去重复，过滤 sampleList 中样本名称
     * 
     * @param sampleList
     * @return ：HashSet<String>
     */
    public static HashSet<String> delRepeat(String sampleList) {
        String dataArray[] = sampleList.split(";");
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < dataArray.length; i++) {
            set.add(dataArray[i].split(":")[0]);
        }
        return set;
    }

    /**
     * 读取报告
     * 
     * @param sourceFile
     * @return
     * @throws IOException
     */
    public static String readAppoint(String sourceFile) {
        String context = null;
        try {
            context = FileUtils.readFileToString(new File(sourceFile), "GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return context == null ? "" : context.replaceAll("\n", "<br />");
    }

    /**
     * 文件下载方法
     * 
     * @param response
     *            :HttpServletResponse
     * @param filePath
     *            ：带有路径的文件名，如 path/fileName.zip
     * @throws IOException
     */
    @SuppressWarnings("static-access")
    public static void fileDownLoad(HttpServletResponse response,
            String filePath) throws IOException {
        int endIndex = 0;
        if (filePath.indexOf("/") != -1) {
            endIndex = filePath.lastIndexOf("/");
        } else {
            endIndex = filePath.lastIndexOf("\\");
        }
        String newFileName = filePath.substring(endIndex + 1);
        File file = new File(filePath);
        response.addHeader("Content-Disposition", "attachment;filename="
                + newFileName);
        response.setContentType("application/octet-stream");
        FileInputStream is = new FileInputStream(file);
        int length = is.available();
        byte[] content = new byte[length];
        is.read(content);
        ServletOutputStream out = response.getOutputStream();
        out.write(content);
        is.close();
        out.flush();
        out.close();
        response.setStatus(response.SC_OK);
        response.flushBuffer();
    }

    /**
     * 创建目录，若目录已存在，则清空
     * 
     * @param path
     *            ：要创建的目录路径
     */
    public static void createDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            String[] fileName = file.list();
            for (int i = 0; i < fileName.length; i++) {
                String name = path + "/" + fileName[i];
                new File(name).delete();
            }
        }
        file.mkdirs();
    }

    /**
     * 创建文件
     * 
     * @param path
     *            ： 路径格式若为：/home/down/test.txt，
     *            若路径不存在，则生成home/down文件夹后生成test.txt文件
     */
    public static void createFile(String path) {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("文件创建异常：" + e);
        }
    }

    /**
     * 向文件中追加内容
     * 
     * @param filePath
     *            ：要写入的文件
     * @param writeContext
     *            ：要追加的内容
     * @throws IOException
     */
    public static void appendWrite(String filePath, String writeContext) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath, true);
            fw.write(writeContext);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断文件夹下是否有符合规则的文件，有则返回文件名，无则返回 ""
     * 
     * @param folderPath
     *            ：要查询的文件夹
     * @param regulation
     *            ：要匹配的字符串
     * @param mate
     *            ：匹配方式，支持 endsWith , startsWith , contains 三种，默认 contains
     * @return
     */
    public static String fileExist(String folderPath, String regulation,
            String mate) {
        File dir = new File(folderPath);
        File file[] = dir.listFiles();
        if (file != null) {
            for (int i = 0; i < file.length; i++) {
                if ("endsWith".equals(mate)) {
                    if (file[i].getName().toLowerCase()
                            .endsWith(regulation.toLowerCase())) {
                        return file[i].getName();
                    }
                } else if ("startsWith".equals(mate)) {
                    if (file[i].getName().toLowerCase()
                            .startsWith(regulation.toLowerCase())) {
                        return file[i].getName();
                    }
                } else {
                    if (file[i].getName().toLowerCase()
                            .contains(regulation.toLowerCase())) {
                        return file[i].getName();
                    }
                }
            }
        }
        return "";
    }

    /**
     * 检索文件夹下符合规则的文件名
     * 
     * @param folderPath
     *            ：要检索的目标文件夹
     * @param regulation
     *            ：要匹配的字符串
     * @param mate
     *            ：匹配方式，支持 endWith , startWith , contains 三种，默认 contains
     * @return
     */
    public static List<String> fileSearch(String folderPath, String regulation,
            String mate) {
        File dir = new File(folderPath);
        File file[] = dir.listFiles();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < file.length; i++) {
            if ("endWith".equals(mate)) {
                if (file[i].getName().endsWith(regulation)) {
                    list.add(file[i].getName());
                }
            } else if ("startWith".equals(mate)) {
                if (file[i].getName().startsWith(regulation)) {
                    list.add(file[i].getName());
                }
            } else {
                if (file[i].getName().contains(regulation)) {
                    list.add(file[i].getName());
                }
            }
        }
        return list;
    }

    /**
     * 遍历文件夹，返回其下所有文件列表（不包含子文件夹）
     * 
     * @param folderPath
     * @return
     */
    public static HashSet<String> getFiles(String folderPath) {
        if (!folderPath.endsWith("/")) {
            folderPath = folderPath + "/";
        }
        File dir = new File(folderPath);
        File file[] = dir.listFiles();
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < file.length; i++) {
            if (new File(folderPath + file[i].getName()).isFile()) {
                set.add(file[i].getName());
            }
        }
        return set;
    }

    /**
     * 遍历文件夹，返回其下所有子文件夹
     * 
     * @param folderPath
     * @return
     */
    public static HashSet<String> getFolders(String folderPath) {
        if (!folderPath.endsWith("/")) {
            folderPath = folderPath + "/";
        }
        File dir = new File(folderPath);
        File file[] = dir.listFiles();
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < file.length; i++) {
            if (new File(folderPath + file[i].getName()).isDirectory()) {
                set.add(file[i].getName());
            }
        }
        return set;
    }

    /**
     * 检验该路径是否存在：适用于文件和文件夹
     * 
     * @param path
     * @return： true，存在；false，不存在
     */
    public static boolean checkPath(String path) {
        return new File(path).exists();
    }

    /**
     * 覆盖写入
     * 
     * @param path
     * @param context
     */
    public static void coverWrite(String path, String context) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(context.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
            }
        }
    }

    /**
     * 按行读取文件
     */
    public static List<String> readLinestoString(String path) {
        List<String> list = new ArrayList<String>();
        try {
            list = FileUtils.readLines(new File(path), "GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String listIsNull(List<String> list, int num) {
        String list_num = "";
        if (list != null && list.size() > num) {
            list_num = list.get(num);
        }
        return list_num;
    }

    public static String getExt(String fileName) {
        String extName = fileName.substring(fileName.lastIndexOf("."));
        return extName;
    }
}