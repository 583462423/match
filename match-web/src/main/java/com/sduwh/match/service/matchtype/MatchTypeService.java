package com.sduwh.match.service.matchtype;

import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.wrapper.MatchTypeWrapper;
import com.sduwh.match.service.BaseService;
import com.sun.xml.internal.rngom.parse.host.Base;

import java.util.List;

/**
 * Created by qxg on 17-6-30.
 */
public interface MatchTypeService extends BaseService<MatchType,Integer> {
    List<MatchType> selectAll();
    List<MatchTypeWrapper> selectAllWrapper();
    MatchType selectByName(String name);
}
