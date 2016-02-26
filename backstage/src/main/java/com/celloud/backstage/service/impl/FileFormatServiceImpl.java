package com.celloud.backstage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.FileFormatMapper;
import com.celloud.backstage.model.FileFormat;
import com.celloud.backstage.service.FileFormatService;

@Service("fileFormatService")
public class FileFormatServiceImpl implements FileFormatService{
    
    @Resource
    private FileFormatMapper fileFormatMapper;

    @Override
    public List<FileFormat> getFileFormatList() {
        return fileFormatMapper.getFileFormatList();
    }


}
