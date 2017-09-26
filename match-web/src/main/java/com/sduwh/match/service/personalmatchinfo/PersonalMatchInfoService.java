package com.sduwh.match.service.personalmatchinfo;

import com.sduwh.match.model.entity.PersonalMatchInfo;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */

public interface PersonalMatchInfoService extends BaseService<PersonalMatchInfo,Integer> {
    List<PersonalMatchInfo> selectByUserId(int id);

    /** 通过match_item_id删除对应的记录*/
    int deleteAllByMatchItemId(int matchItemId);
}
