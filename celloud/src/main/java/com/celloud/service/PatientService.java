package com.celloud.service;

import com.celloud.model.mysql.Patient;

public interface PatientService {
    /**
     *
     * @description 增加患者信息
     * @author miaoqi
     * @date 2017年2月13日 上午10:38:59
     * @param patient
     * @return 主键
     */
    Integer save(Patient patient);
}
