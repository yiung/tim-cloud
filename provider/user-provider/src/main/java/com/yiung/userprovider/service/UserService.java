package com.yiung.userprovider.service;

import com.yiung.api.entity.User;
import com.yiung.userprovider.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * checkUserNameAndPassword
     * */
    public boolean login(@RequestBody User user) {
        if (user == null || "".equals(user)){
            return false;
        }
        if (userMapper.getUserNumberByUsername(user) == 0) {
            //账户不存在，自动添加 todo 这里有漏洞，暂不处理
            return addUser(user) == 1 ? true : false;
        }else {
            User updateUser = userMapper.getUserByUsername(user.getUsername());
            updateUser.setWechatOpenId(user.getWechatOpenId());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setGender(user.getGender());
            updateUser.setLanguage(user.getLanguage());
            updateUser.setCountry(user.getCountry());
            return userMapper.updateByPrimaryKey(updateUser) == 1 ? true : false;
        }
    }

//    /**
//     * getUserAndRoom
//     * */
//    public List<User> getUserAndRoomList(@RequestBody User user) {
//
//            return userMapper.updateByPrimaryKey(updateUser) == 1 ? true : false;
//        }
//    }

    /**
     * checkUserNameAndPassword
     * */
//    public Integer login(@RequestBody User user){
////        if(userMapper.getUserNumberByUsername(user) == 0){
////            //账户不存在，自动添加 todo 这里有漏洞，暂不处理
////            addUser(user);
////
//////            return false;
////        }
//        user.setPassword(encryption(user.getPassword()));
//        if(StringUtils.isNullOrEmpty(user.getUsername()) || StringUtils.isNullOrEmpty(user.getPassword())){
//            return -1001;
//        }
//       List<User> userList =  userMapper.getUserByUsernameAndPassword(user);
//        if(null == userList || userList.isEmpty()){
//            return -1002;
//        }
//
//        if(userList.size()>1){
//                //todo 判断异常
//            }
//        return compareUsernameAndPassword(user,userList.get(0))?0:1;
//    }



    public Integer addUser(@RequestBody User user) {
        Integer result = userMapper.insert(user);
        return result;
    }

    private String encryption(String password){
        //todo 加密

        return password;
    }

    public boolean compareUsernameAndPassword(User user,User targetUser){
        if(targetUser.getUsername().equals(user.getUsername()) &&
                targetUser.getPassword().equals(user.getPassword())){
            return true;
        }
        return false;
    }

    public User getUserByOpenId(@RequestBody User user){
        User result = userMapper.getUserByOpenId(user.getWechatOpenId());
        return result;
    }
}
