package com.sduwh.match.service.matchtype2;

import com.sduwh.match.model.entity.MatchType2;
import com.sduwh.match.model.wrapper.MatchType2Wrapper;
import com.sduwh.match.service.BaseService;

import java.util.List;

/**
 * Created by qxg on 17-6-30.
 */
public interface MatchType2Service extends BaseService<MatchType2,Integer> {
    List<MatchType2> selectByType1Id(Integer id);
    List<MatchType2> selectByAll();
    public List<MatchType2Wrapper> selectALlType2Wrapper();
}
