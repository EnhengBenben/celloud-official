package com.celloud.mapper;

import com.celloud.model.ActionLog;

public interface ActionLogMapper {
    int insert(ActionLog record);

    int insertSelective(ActionLog record);
}