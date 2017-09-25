package com.sduwh.match.controller.rater;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.LoginType;
import com.sduwh.match.enums.RaterLevel;
import com.sduwh.match.model.HostHolder;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.realm.UsernamePasswordWithLoginTypeToken;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.service.tmprater.TmpRaterService;
import com.sduwh.match.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-9-18.
 * 临时评委登陆的那个啥
 */
@Controller
@RequestMapping("/rater")
public class TmpRaterController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TmpRaterController.class);

    private static final String ACADEMY_INDEX = "/rater/academy_index";
    private static final String SUPER_INDEX = "/rater/super_index";
    private static final String ACADEMY_INDEX_URL = "/rater/academy/index";
    private static final String SUPER_INDEX_URL = "/rater/super/index";


    @Autowired
    TmpRaterService tmpRaterService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    MatchItemService matchItemService;


    @PostMapping("/login")
    @ResponseBody
    public String userLogin(TmpRater rater) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordWithLoginTypeToken token = new UsernamePasswordWithLoginTypeToken(rater.getUsername(), rater.getPassword(), LoginType.RATER);
        token.setRememberMe(false);   //不记住我
        try {
            currentUser.login(token);
        } catch (UnknownAccountException e) {
            logger.error("该账号不存在或者该账号未被激活!", e);
            return setJsonResult("error", "该账号不存在或者该账号未被激活!");
        } catch (DisabledAccountException e) {
            logger.error("该账号已被冻结!", e);
            return setJsonResult("error", "该账号已被冻结!");
        } catch (IncorrectCredentialsException e) {
            logger.error("密码错误", e);
            return setJsonResult("error", "密码错误");
        } catch (ExcessiveAttemptsException e) {
            logger.error("密码连续输入错误超过5次，锁定半小时!", e);
            return setJsonResult("error", "密码连续输入错误超过5次，锁定半小时!");
        } catch (RuntimeException e) {
            logger.error("未知错误,请联系管理员!", e);
            return setJsonResult("error", "未知错误,请联系管理员!");
        }

        //判断当前的TmpRater是属于院级还是校级,直接返回一个url
        TmpRater tmpRater = tmpRaterService.selectByUsername(rater.getUsername());
        if (tmpRater.getLevel() == RaterLevel.ACADEMY.getLevel()) {
            //院级
            return setJsonResult("url", ACADEMY_INDEX_URL);
        } else if (tmpRater.getLevel() == RaterLevel.SCHOOL.getLevel()) {
            return setJsonResult("url", SUPER_INDEX_URL);
        }
        return setJsonResult("error", "厉害了兄弟，怎么破解的？");
    }

    @GetMapping("/academy/index")
    public String getAcademyIndex(Model model) {
        //判断是否是学院临时评委
        TmpRater rater = hostHolder.getRater();
        if (rater == null || rater.getLevel() != RaterLevel.ACADEMY.getLevel()){
            throw new IllegalArgumentException("未授权");
        }
        //判断是否过期
        if (tmpRaterService.checkEnd(rater) || tmpRaterService.checkStart(rater)){
            throw new IllegalArgumentException("当前账户已过期或未到评分时间");
        }
        List<MatchItem> doneMatchItems = null;
        List<MatchItem> notDoneMatchItems = null;
        String doneCommentIds = rater.getDoneCommentIds();
        String notDoneCommentIds = rater.getCommentIds();
        if (!StringUtils.nullOrEmpty(doneCommentIds)) {
            doneMatchItems = Arrays.stream(doneCommentIds.split(","))
                    .map(Integer::parseInt)
                    .map(matchItemService::selectByPrimaryKey)
                    .collect(Collectors.toList());
        }
        if (!StringUtils.nullOrEmpty(notDoneCommentIds)) {
            notDoneMatchItems = Arrays.stream(notDoneCommentIds.split(","))
                    .map(Integer::parseInt)
                    .map(matchItemService::selectByPrimaryKey)
                    .collect(Collectors.toList());
        }
        model.addAttribute("notDoneMatchItems",notDoneMatchItems);
        return ACADEMY_INDEX;
    }

    @GetMapping("/super/index")
    public String getSuperIndex(Model model) {
        //判断是否是学校临时评委
        TmpRater rater = hostHolder.getRater();
        if (rater == null || rater.getLevel() != RaterLevel.SCHOOL.getLevel()){
            throw new IllegalArgumentException("未授权");
        }
        //判断是否过期
        if (tmpRaterService.checkEnd(rater) || tmpRaterService.checkStart(rater)){
            throw new IllegalArgumentException("当前账户已过期或未到评分时间");
        }
        List<MatchItem> doneMatchItems = null;
        List<MatchItem> notDoneMatchItems = null;
        String doneCommentIds = rater.getDoneCommentIds();
        String notDoneCommentIds = rater.getCommentIds();
        if (!StringUtils.nullOrEmpty(doneCommentIds)) {
            doneMatchItems = Arrays.stream(doneCommentIds.split(","))
                    .map(Integer::parseInt)
                    .map(matchItemService::selectByPrimaryKey)
                    .collect(Collectors.toList());
        }
        if (!StringUtils.nullOrEmpty(notDoneCommentIds)) {
            notDoneMatchItems = Arrays.stream(notDoneCommentIds.split(","))
                    .map(Integer::parseInt)
                    .map(matchItemService::selectByPrimaryKey)
                    .collect(Collectors.toList());
        }
        model.addAttribute("notDoneMatchItems",notDoneMatchItems);
        return SUPER_INDEX;
    }
}
