package com.github.tanhao1410.thesis.management.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.management.bean.ResultContentVO;
import com.github.tanhao1410.thesis.management.bean.UserBean;
import com.github.tanhao1410.thesis.management.bean.request.LoginOutRequest;
import com.github.tanhao1410.thesis.management.bean.response.LoginResponse;
import com.github.tanhao1410.thesis.management.constant.RedisConfConstant;
import com.github.tanhao1410.thesis.management.constant.ResultConstans;
import com.github.tanhao1410.thesis.management.domain.UserDO;
import com.github.tanhao1410.thesis.management.interceptor.ExpireMap;
import com.github.tanhao1410.thesis.management.mapper.UserDOMapper;
import com.github.tanhao1410.thesis.management.service.IUserBusinessService;
import com.github.tanhao1410.thesis.management.utils.PasswordUtil;
import com.github.tanhao1410.thesis.management.utils.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Component
@Slf4j
public class UserBusinessServiceImpl implements IUserBusinessService {

    @Resource
    private UserDOMapper userDOMapper;



    @Override
    public ResultContentVO<LoginResponse> login(String userName, String password) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(password)) {
            return ResultContentVO.Fail(ResultConstans.FIAL_CODE, ResultConstans.FIAL_APARAM);
        }

        try {
            LoginResponse response = new LoginResponse();

            UserBean userBean = new UserBean();
            //首先根据用户名信息查询
            UserDO queryUser = new UserDO();

            queryUser.setName(PasswordUtil.deCodePassword(userName, RSAUtils.privateKey));
            //queryUser.setUserName(userName);

            List<UserDO> userDOList = userDOMapper.selectPageSelective(queryUser, new PageRequest(0, 1));
            if (CollectionUtils.isEmpty(userDOList)) {
                return ResultContentVO.Fail(ResultConstans.FIAL_CODE, ResultConstans.NO_USER);
            }

            UserDO userDO = userDOList.get(0);
            //ras解密前端传过来的密码
            String passwordDec = PasswordUtil.deCodePassword(password, RSAUtils.privateKey);
            //对密码进行加密，然后与数据库用户密码进行对比
            String encodePassword = PasswordUtil.enCodePassword(passwordDec);
            if (!userDO.getPassword().equals(encodePassword)) {
                return ResultContentVO.Fail(ResultConstans.FIAL_CODE, ResultConstans.WRONG_PASSWORD);
            }

            //生成token
            String token = UUID.randomUUID().toString().replace("-", "");
            userBean.setUserName(userDO.getName());

            userBean.setId(userDO.getId());
            ///userBean.setRealName(userDO.getRealName());
            userBean.setToken(token);

            //存入redis中
            ExpireMap.set(RedisConfConstant.USER_LOGIN_TOKEN_PRE + token, JSON.toJSONString(userBean), 3600);


            response.setToken(token);


            return ResultContentVO.Su(response);
        } catch (Exception e) {
            log.error("LoginBusinessServiceImpl.login.error: userName:" + userName, e);
        }

        return ResultContentVO.Fail(ResultConstans.FIAL_CODE, ResultConstans.FIAL_API_FAIL);
    }

    @Override
    public ResultContentVO<Map<String, Object>> userInfo(UserBean user) {
        //用户信息
        Map<String, Object> res = new HashMap<String, Object>();
        final UserDO tbUserDO = userDOMapper.selectByPrimaryKey(user.getId());
        res.put("userName",user.getUserName());
        return ResultContentVO.Su(res);
    }

    @Override
    public ResultContentVO<String> loginOut(LoginOutRequest loginOutRequest) {
        if (loginOutRequest != null && !StringUtils.isEmpty(loginOutRequest.getToken())) {
            ExpireMap.deleteKey(RedisConfConstant.USER_LOGIN_TOKEN_PRE + loginOutRequest.getToken());
        }
        return ResultContentVO.Su(ResultConstans.SUCCESS_MSG);
    }

}
