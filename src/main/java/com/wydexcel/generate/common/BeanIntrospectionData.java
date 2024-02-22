//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wydexcel.generate.common;

import org.apache.commons.beanutils.MethodUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class BeanIntrospectionData {
    private final PropertyDescriptor[] descriptors;
    private final Map<String, String> writeMethodNames;

    public BeanIntrospectionData(PropertyDescriptor[] descs) {
        this(descs, setUpWriteMethodNames(descs));
    }

    BeanIntrospectionData(PropertyDescriptor[] descs, Map<String, String> writeMethNames) {
        this.descriptors = descs;
        this.writeMethodNames = writeMethNames;
    }

    public PropertyDescriptor[] getDescriptors() {
        return this.descriptors;
    }

    public PropertyDescriptor getDescriptor(String name) {
        PropertyDescriptor[] var2 = this.getDescriptors();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            PropertyDescriptor pd = var2[var4];
            if (name.equals(pd.getName())) {
                return pd;
            }
        }

        return null;
    }

    public Method getWriteMethod(Class<?> beanCls, PropertyDescriptor desc) {
        Method method = desc.getWriteMethod();
        if (method == null) {
            String methodName = (String)this.writeMethodNames.get(desc.getName());
            if (methodName != null) {
                method = MethodUtils.getAccessibleMethod(beanCls, methodName, desc.getPropertyType());
                if (method != null) {
                    try {
                        desc.setWriteMethod(method);
                    } catch (IntrospectionException var6) {
                    }
                }
            }
        }

        return method;
    }

    private static Map<String, String> setUpWriteMethodNames(PropertyDescriptor[] descs) {
        Map<String, String> methods = new HashMap();
        PropertyDescriptor[] var2 = descs;
        int var3 = descs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            PropertyDescriptor pd = var2[var4];
            Method method = pd.getWriteMethod();
            if (method != null) {
                methods.put(pd.getName(), method.getName());
            }
        }

        return methods;
    }
}
