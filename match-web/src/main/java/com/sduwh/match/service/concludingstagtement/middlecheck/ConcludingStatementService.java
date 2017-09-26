package com.sduwh.match.service.concludingstagtement.middlecheck;

import com.sduwh.match.model.entity.ConcludingStatement;
import com.sduwh.match.model.entity.MiddleCheck;
import com.sduwh.match.service.BaseService;

/**
 * Created by qxg on 17-9-11.
 */
public interface ConcludingStatementService extends BaseService<ConcludingStatement,Integer> {
    ConcludingStatement selectByMatchItemId(int matchItemId);
    int updateByMatchItemId(ConcludingStatement concludingStatement);
    int updateByMatchItemIdSelective(ConcludingStatement concludingStatement);

    /** 通过match_item_id删除对应的grade表*/
    int deleteAllByMatchItemId(int matchItemId);
}
