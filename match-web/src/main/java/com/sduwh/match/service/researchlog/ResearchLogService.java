package com.sduwh.match.service.researchlog;

import com.sduwh.match.model.entity.ResearchLog;
import com.sduwh.match.service.BaseService;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */
public interface ResearchLogService extends BaseService<ResearchLog,Integer>{
    List<ResearchLog> getAllLogByMatchItemId(int matchItemId);


    /** 通过match_item_id删除对应的记录*/
    int deleteAllByMatchItemId(int matchItemId);
}
