package com.sduwh.match.controller.admin;

import com.sduwh.match.Enum.StageEnum;
import com.sduwh.match.base.BaseController;
import com.sduwh.match.common.ResponseResult;
import com.sduwh.match.dao.MatchInfoMapper;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.entity.MatchType2;
import com.sduwh.match.model.to.MatchInfoTO;
import com.sduwh.match.model.wrapper.MatchInfoWrapper;
import com.sduwh.match.model.wrapper.MatchType2Wrapper;
import com.sduwh.match.model.wrapper.MatchTypeWrapper;
import com.sduwh.match.model.wrapper.StageWrapper;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.matchtype.MatchTypeService;
import com.sduwh.match.service.matchtype2.MatchType2Service;
import com.sduwh.match.service.stage.StageService;
import org.apache.ibatis.type.JdbcType;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-29.
 * 超级管理员的界面，控制比赛的阶段等数据的产生，查看所有数据，控制SRTP进度安排，生成校级评委，发布通知等功能
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    private static final String INDEX = "/admin/index";
    private static final String MATCHS = "/admin/matchs";
    private static final String MATCH_CREATE = "/admin/matchCreate";
    private static final String MATCH_TYPES = "/admin/matchtypes";
    private static final String MATCH_TYPES2 = "/admin/matchtypes2";


    @Autowired
    MatchInfoService matchInfoService;
    @Autowired
    StageService stageService;
    @Autowired
    MatchTypeService matchTypeService;
    @Autowired
    MatchType2Service matchType2Service;


    /**
     * 访问主页，此处不需要任何判断，注意的是，需要在shiro过滤规则中添加只能admin访问
     */
    @GetMapping(value = {"/index", "/"})
    public String index() {
        return INDEX;
    }


    /** ====================================比赛管理start================================== **/
    /**
     * 比赛管理
     */
    @GetMapping("/matchs")
    public String matchs(Map<String, List<MatchInfo>> map) {
        //首先要获取所有比赛
        List<MatchInfo> matchInfoList = matchInfoService.selectAll();
        map.put("matchInfos", matchInfoList);
        return MATCHS;
    }

    /**
     * 获取MatchInfo的详细信息
     */
    @GetMapping("/match/{id}")
    @ResponseBody
    public MatchInfoWrapper getMatch(@PathVariable("id") Integer id) {
        MatchInfo matchInfo = matchInfoService.selectByPrimaryKey(id);
        MatchInfoWrapper wrapper = new MatchInfoWrapper();
        wrapper.setMatchInfo(matchInfo);
        wrapper.setAllStage(stageService.selectAllByString(matchInfo.getAllStage()));
        wrapper.setMatchType(matchTypeService.selectByPrimaryKey(matchInfo.getType1()));
        wrapper.setMatchType2(matchType2Service.selectByPrimaryKey(matchInfo.getType2()));
        return wrapper;
    }

    /**
     * 创建比赛
     */
    @GetMapping("/match/create")
    public String createMatch(Map<String, Object> map) {
        //首先要获取所有的阶段
        ArrayList<StageWrapper> list = new ArrayList<>();
        Arrays.stream(StageEnum.values())
                .map(e -> new StageWrapper(e.getDes(), e.getId(), false))
                .forEach(list::add);
        map.put("stages", list);
        return MATCH_CREATE;
    }

    /**
     * 创建比赛
     */
    @PostMapping("/match/create")
    @ResponseBody
    public ResponseResult startCreateMatch(ServletRequest servletRequest) {
        MatchInfoTO matchInfoTO = getMatchInfoTO(servletRequest);
        //获得的类型是这样的
        //MatchInfoTO{name='2014-2015创青春创业大赛', type1=1, type2=1, level=1, leaderNum=2, leaderInNum=3, memberNum=4, memberInNum=5, teacherNum=6, teacherInNum=7, isChoose=[10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], type=[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], startTime=[2017-01-01T01:01, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], endTime=[2018-01-01T01:01, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]}
        System.out.println(matchInfoTO);
        //创建matchInfo
        if (matchInfoService.createMatchInfoByTO(matchInfoTO) == 1) {
            //创建成功
            return success(true);
        }
        return fail(false,"创建失败");
    }

    /** 更新比赛*/
    @PostMapping("/match/update")
    @ResponseBody
    public ResponseResult updateMatchInfo(ServletRequest servletRequest) {
        MatchInfoTO matchInfoTO = getMatchInfoTO(servletRequest);
        //更新matchInfo
        if (matchInfoService.updateMatchInfoByTO(matchInfoTO) == 1) {
            //更新成功
            return success(true);
        }
        return fail(false,"更新失败");
    }

    /** 删除比赛*/
    @GetMapping("/match/delete/{id}")
    @ResponseBody
    public ResponseResult deleteMatchInfo(@PathVariable("id") Integer id) {
        if(matchInfoService.deleteByPrimaryKey(id) == 1){
            return success(true);
        }
        return fail(false,"删除失败");
    }

    public MatchInfoTO getMatchInfoTO(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        MatchInfoTO matchInfoTO = new MatchInfoTO();
        String id = request.getParameter("id");
        if (id != null) {
            matchInfoTO.setId(Integer.parseInt(id));
        }
        matchInfoTO.setName(request.getParameter("name"));
        matchInfoTO.setIsChoose(Arrays.stream(request.getParameterValues("isChoose[]")).map(Integer::parseInt).collect(Collectors.toList()));
        matchInfoTO.setLeaderInNum(Integer.parseInt(request.getParameter("leaderInNum")));
        matchInfoTO.setLevel(Integer.parseInt(request.getParameter("level")));
        matchInfoTO.setMemberInNum(Integer.parseInt(request.getParameter("memberInNum")));
        matchInfoTO.setLeaderNum(Integer.parseInt(request.getParameter("leaderNum")));
        matchInfoTO.setMemberNum(Integer.parseInt(request.getParameter("memberNum")));
        matchInfoTO.setTeacherNum(Integer.parseInt(request.getParameter("teacherNum")));
        matchInfoTO.setTeacherInNum(Integer.parseInt(request.getParameter("teacherInNum")));
        matchInfoTO.setStageName(Arrays.stream(request.getParameterValues("stageName[]")).collect(Collectors.toList()));
        matchInfoTO.setType1(Integer.parseInt(request.getParameter("type1")));
        if(!"".equals(request.getParameter("type2")))
            matchInfoTO.setType2(Integer.parseInt(request.getParameter("type2")));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        matchInfoTO.setEndTime(Arrays.stream(request.getParameterValues("endTime[]")).map(e -> {
            if ("0".equals(e)) return null;
            try {
                return new Timestamp(sdf.parse(e).getTime());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList()));

        matchInfoTO.setStartTime(Arrays.stream(request.getParameterValues("startTime[]")).map(e -> {
            if ("0".equals(e)) return null;
            try {
                return new Timestamp(sdf.parse(e).getTime());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList()));
        System.out.println(matchInfoTO);
        return matchInfoTO;
    }
    /** ====================================比赛管理end================================== **/


    /** ===================================比赛类别管理start============================== **/
    /** 获取比赛类别信息*/
    @GetMapping("/matchtype")
    public String getMatchType(Map<String,List<MatchType>> map){
        map.put("types",matchTypeService.selectAll());
        return MATCH_TYPES;
    }

    /** 删除比赛类型*/
    @GetMapping("/matchtype/delete/{id}")
    @ResponseBody
    public ResponseResult deleteType1(@PathVariable("id") Integer id){
        //TODO 在删除类别的时候，首先要判断一下是否有其他比赛依赖这个比赛项目，如果依赖，前端判断是否要删除
        if(matchTypeService.deleteByPrimaryKey(id) == 1){
            return success(true);
        }

        return fail(false,"未知错误");
    }

    /** 创建比赛类型*/
    @GetMapping("/matchtype/create/{name}")
    @ResponseBody
    public ResponseResult createType1(@PathVariable("name") String name){
        System.out.println(name);
        if (matchTypeService.selectByName(name) == null){
            MatchType matchType = new MatchType();
            matchType.setName(name);
            System.out.println(matchTypeService.insert(matchType));
            return success(true);
        }else{
            return fail(false,"该名称已经存在");
        }
    }

    /** 查看二级比赛类型*/
    @GetMapping("/matchtype2")
    public String getMatchType2(Map<String,List<MatchType2Wrapper>> map){
        map.put("types",matchType2Service.selectALlType2Wrapper());
        return MATCH_TYPES2;
    }

    /** 删除二级比赛类型*/
    @GetMapping("/matchtype2/delete/{id}")
    @ResponseBody
    public ResponseResult deleteType2(@PathVariable("id") Integer id){
        //TODO 在删除类别的时候，首先要判断一下是否有其他比赛依赖这个比赛项目，如果依赖，前端判断是否要删除
        if(matchType2Service.deleteByPrimaryKey(id) == 1){
            return success(true);
        }
        return fail(false,"未知错误");
    }

    /** 创建二级比赛类型*/
    @GetMapping("/matchtype2/create/{name}/{type1}")
    @ResponseBody
    public ResponseResult createType2(@PathVariable("name")String name,@PathVariable("type1")Integer type1){
        MatchType2 matchType2 = new MatchType2();
        matchType2.setMatchTypeId(type1);
        matchType2.setName(name);
        if(matchType2Service.insert(matchType2) == 1)return success(true);
        else return fail(false,"插入失败");
    }

    /** ===================================比赛类别管理end============================== **/

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'mm:ss");
        System.out.println(sdf.parse("2017-01-01T01:01").getTime());
    }
}
