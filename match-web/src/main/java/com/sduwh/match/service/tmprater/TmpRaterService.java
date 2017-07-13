package com.sduwh.match.service.tmprater;

import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.model.wrapper.RaterWrapper;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */

public interface TmpRaterService extends BaseService<TmpRater,Integer> {
    List<RaterWrapper> selectAll();
}
