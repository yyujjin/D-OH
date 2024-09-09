package com.DOH.DOH.controller.contest;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;

public class BeanUtilsHelper {
    public static void copyNonNullProperties(Object src, Object target) {
        if (src == null || target == null) {
            return;
        }

        BeanWrapper srcWrap = new BeanWrapperImpl(src);
        BeanWrapper tgtWrap = new BeanWrapperImpl(target);

        PropertyDescriptor[] pds = srcWrap.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            // 'class' 속성을 무시
            if ("class".equals(pd.getName())) {
                continue;
            }

            Object srcValue = srcWrap.getPropertyValue(pd.getName());
            if (srcValue != null) {
                tgtWrap.setPropertyValue(pd.getName(), srcValue);
            }
        }
    }
}
