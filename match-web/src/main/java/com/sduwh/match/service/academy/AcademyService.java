package com.sduwh.match.service.academy;

import com.sduwh.match.model.entity.Academy;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */
public interface AcademyService extends BaseService<Academy,Integer>{
    List<Academy> selectAll();
}
