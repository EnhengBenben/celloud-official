package com.celloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.mapper.PatientMapper;
import com.celloud.model.mysql.Patient;
import com.celloud.service.PatientService;

@Service
public class PatientServiceimpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public Integer save(Patient patient) {
        return patientMapper.insertSelective(patient);
    }

}
