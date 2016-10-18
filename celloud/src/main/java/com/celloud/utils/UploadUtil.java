package com.celloud.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class UploadUtil {

    /**
     * 
     * @description 通过userId, name, lastModifiedDate, size获取一个唯一的名称
     * @author miaoqi
     * @date 2016年10月17日下午4:58:55
     *
     * @param userId
     *            用户id
     * @param name
     *            文件名
     * @param lastModifiedDate
     *            文件最后修改时间
     * @param size
     *            文件大小
     *
     */
    public static String getUniqueName(Integer userId, String name, Date lastModifiedDate, long size) {
        return MD5Util.getMD5(userId + "--" + name + "--" + lastModifiedDate.getTime() + "--" + size) + "_" + name;
    }

    /**
     * 
     * @description
     * @author miaoqi
     * @date 2016年10月17日下午4:59:44
     *
     * @param dest
     *            目标文件路径
     * @param srcFolder
     *            块存储路径
     * @param total
     *            块文件数量
     *
     */
    public static Boolean mergeBlock(String dest, String srcFolder, Integer total) {
        File destFile = new File(dest);
        File srcFolderFile = new File(srcFolder);
        try {
            // 2. 循环读取块存储目录下的文件追加到目标文件
            for (int i = 0; i < total; i++) {
                FileUtils
                        .writeByteArrayToFile(destFile,
                                FileUtils.readFileToByteArray(
                                        new File(srcFolderFile.getAbsolutePath() + File.separator + i)),
                                i != 0);
            }
            // 3. 将存储每一块的文件夹删除
            FileUtils.forceDelete(new File(srcFolder));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
