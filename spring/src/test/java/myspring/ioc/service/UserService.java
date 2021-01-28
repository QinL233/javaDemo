package myspring.ioc.service;

import myspring.ioc.model.User;

public class UserService {
    private User user;

    public String selectById(Integer id){
        if (id == user.getId()) {
            return "user:" + user.getUsername();
        }

        return "no user of " + id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
