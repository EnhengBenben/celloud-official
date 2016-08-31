package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.Tag;

public interface TagService {

    List<Tag> findTags(Integer userId);
}
