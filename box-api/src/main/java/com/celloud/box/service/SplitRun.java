package com.celloud.box.service;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.model.DataFile;
import com.celloud.box.model.SplitFile;
import com.celloud.box.utils.UploadPath;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SplitRun {
    private static Logger logger = LoggerFactory.getLogger(SplitRun.class);
    @Resource
    private SplitService service;
    @Resource
    private BoxConfig config;
    @Resource
    private ApplicationContext context;
    @Resource
    private BoxService boxService;
    @Autowired
    private ApiService apiService;


    public void split(SplitFile splitFile) {
        splitFile.setRunning(Boolean.TRUE);
        splitFile.toFile();
        boolean result = service.run(splitFile);
        if (result) {
            // 标记运行完成
            splitFile.setRunning(Boolean.FALSE);
            splitFile.toFile();
            // 分别处理三个文件
            DataFile r1 = setSplited(splitFile.getR1Path());
            boxService.finish(r1);
            DataFile r2 = setSplited(splitFile.getR2Path());
            boxService.finish(r2);
            // 读取r1, r2, 通知celloud修改r1, r2的运行状态
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode r1Tree = objectMapper.readTree(splitFile.getR1Path());
                JsonNode r2Tree = objectMapper.readTree(splitFile.getR2Path());
                Integer r1Id = Integer.parseInt(String.valueOf(r1Tree.get("fileId")));
                Integer r2Id = Integer.parseInt(String.valueOf(r2Tree.get("fileId")));
                Boolean flag = apiService.fileRunOver(r1Id, r2Id);
                if (flag) {
                    logger.info("修改数据运行状态成功");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            DataFile txt = setSplited(splitFile.getTxtPath());
            boxService.finish(txt);
            // 读取split结果
            String temp = new File(r1.getPath()).getName().replaceAll(r1.getExt(), "");
            String resultPath = UploadPath.getSplitOutputPath(splitFile.getUserId(), splitFile.getBatch(),
                    splitFile.getName()) + File.separatorChar + temp + File.separatorChar + "result"
                    + File.separatorChar + "split" + File.separatorChar;
            File resultFile = new File(resultPath);
            File[] results = resultFile.listFiles();
            if (results == null || results.length == 0) {
                logger.error("split运行结束，但是没有找到结果文件：{}\n{}", resultPath, splitFile.toJSON());
                return;
            }
            // 分别处理结果文件
            String folder = UploadPath.getUploadingPath(splitFile.getUserId());
            for (File r : results) {
                if (r.isDirectory()) {
                    continue;
                }
                String filename = r.getName();
                String uniqueName = UploadPath.getUniqueName(splitFile.getUserId(), r.getName(), r.lastModified(),
                        r.length());
                String randomName = UploadPath.getRandomName(uniqueName);
                File newPath = new File(folder + randomName);
                try {
                    FileUtils.moveFile(r, newPath);// 文件移动到等待上传的目录下
                } catch (IOException e) {
                    logger.error("移动文件失败：{}", r.getAbsolutePath(), e);
                    continue;
                }
                boxService.splitRunOver(splitFile.getUserId(), filename, splitFile.getName(), splitFile.getTagId(),
                        splitFile.getBatch(), null, newPath);
            }
            // clean...
            File file = resultFile.getParentFile().getParentFile().getParentFile();
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                logger.error("删除目录失败：{}", file.getAbsolutePath(), e);
            }
            // 列举上级目录下的所有的文件夹
            File[] children = file.getParentFile().listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory();
                }
            });
            // 如果上级目录下不存在其他文件夹，则连同上级目录一起删除
            if (children == null || children.length == 0) {
                try {
                    FileUtils.deleteDirectory(file.getParentFile());
                } catch (IOException e) {
                    logger.error("删除目录失败：{}", file.getParentFile().getAbsolutePath(), e);
                }
            }
            String path = UploadPath.getSplitCheckingPath(splitFile.getUserId(), splitFile.getBatch(),
                    splitFile.getName());
            new File(splitFile.getListPath()).delete();// 删除生成的list文件
            new File(path).delete();// 删除split的校验文件
        } else {

        }
    }

    /**
     * 将上传的文件置为split已完成
     * 
     * @param path
     * @return
     */
    public DataFile setSplited(String path) {
        DataFile temp = DataFile.load(path + ".json");
        if (temp == null) {
            logger.info("没有加载到文件的信息:{}", path);
            return null;
        }
        temp.setSplited(Boolean.TRUE);
        temp.serialize();
        return temp;
    }
}
