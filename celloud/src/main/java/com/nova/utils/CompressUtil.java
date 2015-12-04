package com.nova.utils;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.action.ManyFileUploadAction;
import com.nova.constants.FileFormat;

public class CompressUtil implements Runnable {
    Logger log = Logger.getLogger(CompressUtil.class);
    private String sourceFile;
    private String destDir;
    public List<File> fileList;

    public CompressUtil(String sourceFile, String destDir) {
        this.sourceFile = sourceFile;
        this.destDir = destDir;
    }

    @Override
    public void run() {
        TargzUtil tarUtil = new TargzUtil();
        log.info("解压缩文件:" + sourceFile);
        fileList = tarUtil.unTargzFile(sourceFile, destDir);
        for (File f : fileList) {
            String fileName = f.getName();
            CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();
            int fileFormat = checkFileType.checkFileType(fileName);
            if (fileFormat != FileFormat.YASUO) {
                // 将解压缩后的文件添加到数据库
                ManyFileUploadAction fAction = new ManyFileUploadAction();
                int dataId = fAction.addFileInfo(fileName);
                String dataKey = DataUtil.getNewDataKey(dataId);
                String newName = dataKey + FileTools.getExtName(fileName);
                log.info("准备将解压出的的文件" + f + "添加到数据库");
                fAction.updateFileInfo(dataId, dataKey, newName);
            }
        }
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }
}
