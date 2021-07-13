package com.example.demo.qleexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: zhaoyu
 * @date: 2021/6/21
 * @description:
 */
public class TypicalDemo {

    private ExpressRunner runner = new ExpressRunner();

    public void initial() throws Exception {
        // 添加方法
        addClassFunction();

        // 创建宏
        addMacro();
    }

    public void addMacro() throws Exception {
        runner.addMacro("新用户", "checkIsNewUser(userInfo)");
        runner.addMacro("绑定手机号", "checkBindMobile(userInfo)");
    }

    public void addClassFunction() throws Exception {
        runner.addFunctionOfClassMethod("checkIsNewUser", TypicalDemo.class.getName(), "checkIsNewUser",
                new String[]{UserInfo.class.getName()}, "不是新用户");
        runner.addFunctionOfClassMethod("checkBindMobile", TypicalDemo.class.getName(), "checkBindMobile",
                new String[]{UserInfo.class.getName()}, "未绑定手机号");
    }

    /**
     * 是否是新用户
     *
     * @param userInfo
     * @return true 是， false 不是
     */
    public boolean checkIsNewUser(UserInfo userInfo) {
        return CollectionUtils.isEmpty(userInfo.getOrderInfoList());
    }

    /**
     * 校验是否绑定了手机号
     *
     * @param userInfo
     * @return true 是 false 否
     */
    public boolean checkBindMobile(UserInfo userInfo) {
        return StringUtils.isNotBlank(userInfo.getMobile());
    }

    @Test
    public void testTypicalDemo() throws Exception {
        TypicalDemo typicalDemo = new TypicalDemo();
        initial();
        canDiscount(new UserInfo("A", "", new ArrayList<>()), "新用户 and 绑定手机号");
        canDiscount(new UserInfo("B", "12344441", Arrays.asList(new OrderInfo("23222"))), "新用户 and 绑定手机号");
        canDiscount(new UserInfo("C", "12344441", new ArrayList<>()), "新用户 and 绑定手机号");
    }

    public boolean canDiscount(UserInfo userInfo, String express) throws Exception {
        IExpressContext<String, Object> expressContext = new DefaultContext<String, Object>();
        expressContext.put("userInfo", userInfo);
        List<String> errorInfo = new ArrayList<>();
        Boolean result = (Boolean) runner.execute(express, expressContext, errorInfo, true, false);
        if (result) {
            System.out.println("用户:" + userInfo.getUserName() + "可以打折");
        } else {
            String resultStr = "";
            for (int i = 0; i < errorInfo.size(); i++) {
                if (i > 0) {
                    resultStr = resultStr + ",";
                }
                resultStr = resultStr + errorInfo.get(i);
            }
            System.out.println("用户：" + userInfo.getUserName() + "由于" + resultStr + "，不可以打折");
        }
        return result;
    }


    @Getter
    @Setter
    class UserInfo {
        private String userName;
        private String mobile;
        private List<OrderInfo> orderInfoList;

        public UserInfo(String userName, String mobile, List<OrderInfo> orderInfoList) {
            this.userName = userName;
            this.mobile = mobile;
            this.orderInfoList = orderInfoList;
        }
    }


    @Getter
    @Setter
    class OrderInfo {
        private String orderId;

        public OrderInfo(String orderId) {
            this.orderId = orderId;
        }
    }

}
