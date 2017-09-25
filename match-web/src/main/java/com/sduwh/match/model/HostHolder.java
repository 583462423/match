package com.sduwh.match.model;

import com.sduwh.match.model.entity.TmpRater;
import com.sduwh.match.model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by qxg on 17-8-29.
 */
@Service
public class HostHolder {

    public static ThreadLocal<User> users = new ThreadLocal<>();
    public static ThreadLocal<TmpRater> raters = new ThreadLocal<>();

    public void setRater(TmpRater rater){
        raters.set(rater);
    }

    public TmpRater getRater(){
        return raters.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
        raters.remove();
    }

}
