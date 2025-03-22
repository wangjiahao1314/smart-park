package com.ruoyi.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.utils.PathUtil;
import com.ruoyi.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FieldHandler implements MetaObjectHandler {
    /**
     * 插入时的填充策略
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);


        if(!"/register".equals(PathUtil.getCurrentPath())) {
            this.setFieldValByName("createBy", SecurityUtils.getUsername(), metaObject);
        } else {
            this.setFieldValByName("createBy", "system",metaObject);
        }

    }

    /**
     * 更新时的填充策略
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", SecurityUtils.getUsername(), metaObject);
    }

}