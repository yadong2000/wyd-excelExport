//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wydexcel.generate.common;

import org.apache.commons.beanutils.*;
import org.apache.commons.beanutils.expression.Resolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class BeanUtilsBean {
    private static final ContextClassLoaderLocal<BeanUtilsBean> BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal<BeanUtilsBean>() {
        protected BeanUtilsBean initialValue() {
            return new BeanUtilsBean();
        }
    };
    private final Log log;
    private final ConvertUtilsBean convertUtilsBean;
    private final MyPropertyUtilsBean propertyUtilsBean;
    private static final Method INIT_CAUSE_METHOD = getInitCauseMethod();

    public static BeanUtilsBean getInstance() {
        return (BeanUtilsBean)BEANS_BY_CLASSLOADER.get();
    }

    public static void setInstance(BeanUtilsBean newInstance) {
        BEANS_BY_CLASSLOADER.set(newInstance);
    }

    public BeanUtilsBean() {
        this(new ConvertUtilsBean(), new MyPropertyUtilsBean());
    }

    public BeanUtilsBean(ConvertUtilsBean convertUtilsBean) {
        this(convertUtilsBean, new MyPropertyUtilsBean());
    }

    public BeanUtilsBean(ConvertUtilsBean convertUtilsBean, MyPropertyUtilsBean propertyUtilsBean) {
        this.log = LogFactory.getLog(MyBeanUtils.class);
        this.convertUtilsBean = convertUtilsBean;
        this.propertyUtilsBean = propertyUtilsBean;
    }

    public Object cloneBean(Object bean) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Cloning bean: " + bean.getClass().getName());
        }

        Object newBean = null;
        if (bean instanceof DynaBean) {
            newBean = ((DynaBean)bean).getDynaClass().newInstance();
        } else {
            newBean = bean.getClass().newInstance();
        }

        this.getPropertyUtils().copyProperties(newBean, bean);
        return newBean;
    }

    public void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        } else if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        } else {
            if (this.log.isDebugEnabled()) {
                this.log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
            }

            int var5;
            int var6;
            String name;
            Object value;
            if (orig instanceof DynaBean) {
                DynaProperty[] origDescriptors = ((DynaBean)orig).getDynaClass().getDynaProperties();
                DynaProperty[] var4 = origDescriptors;
                var5 = origDescriptors.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    DynaProperty origDescriptor = var4[var6];
                    name = origDescriptor.getName();
                    if (this.getPropertyUtils().isReadable(orig, name) && this.getPropertyUtils().isWriteable(dest, name)) {
                        value = ((DynaBean)orig).get(name);
                        this.copyProperty(dest, name, value);
                    }
                }
            } else if (orig instanceof Map) {
                Map<String, Object> propMap = (Map)orig;
                Iterator var13 = propMap.entrySet().iterator();

                while(var13.hasNext()) {
                    Entry<String, Object> entry = (Entry)var13.next();
                    String key = (String)entry.getKey();
                    if (this.getPropertyUtils().isWriteable(dest, key)) {
                        this.copyProperty(dest, key, entry.getValue());
                    }
                }
            } else {
                PropertyDescriptor[] origDescriptors = this.getPropertyUtils().getPropertyDescriptors(orig);
                PropertyDescriptor[] var14 = origDescriptors;
                var5 = origDescriptors.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor origDescriptor = var14[var6];
                    name = origDescriptor.getName();
                    if (!"class".equals(name) && this.getPropertyUtils().isReadable(orig, name) && this.getPropertyUtils().isWriteable(dest, name)) {
                        try {
                            value = this.getPropertyUtils().getSimpleProperty(orig, name);
                            this.copyProperty(dest, name, value);
                        } catch (NoSuchMethodException var10) {
                        }
                    }
                }
            }

        }
    }

    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        if (this.log.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder("  copyProperty(");
            sb.append(bean);
            sb.append(", ");
            sb.append(name);
            sb.append(", ");
            if (value == null) {
                sb.append("<NULL>");
            } else if (value instanceof String) {
                sb.append((String)value);
            } else if (!(value instanceof String[])) {
                sb.append(value.toString());
            } else {
                String[] values = (String[])((String[])value);
                sb.append('[');

                for(int i = 0; i < values.length; ++i) {
                    if (i > 0) {
                        sb.append(',');
                    }

                    sb.append(values[i]);
                }

                sb.append(']');
            }

            sb.append(')');
            this.log.trace(sb.toString());
        }

        Object target = bean;
        Resolver resolver = this.getPropertyUtils().getResolver();

        while(resolver.hasNested(name)) {
            try {
                target = this.getPropertyUtils().getProperty(target, resolver.next(name));
                name = resolver.remove(name);
            } catch (NoSuchMethodException var15) {
                return;
            }
        }

        if (this.log.isTraceEnabled()) {
            this.log.trace("    Target bean = " + target);
            this.log.trace("    Target name = " + name);
        }

        String propName = resolver.getProperty(name);
        Class<?> type = null;
        int index = resolver.getIndex(name);
        String key = resolver.getKey(name);
        if (target instanceof DynaBean) {
            DynaClass dynaClass = ((DynaBean)target).getDynaClass();
            DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
            if (dynaProperty == null) {
                return;
            }

            type = dynaPropertyType(dynaProperty, value);
        } else {
            PropertyDescriptor descriptor = null;

            try {
                descriptor = this.getPropertyUtils().getPropertyDescriptor(target, name);
                if (descriptor == null) {
                    return;
                }
            } catch (NoSuchMethodException var16) {
                return;
            }

            type = descriptor.getPropertyType();
            if (type == null) {
                if (this.log.isTraceEnabled()) {
                    this.log.trace("    target type for property '" + propName + "' is null, so skipping ths setter");
                }

                return;
            }
        }

        if (this.log.isTraceEnabled()) {
            this.log.trace("    target propName=" + propName + ", type=" + type + ", index=" + index + ", key=" + key);
        }

        if (index >= 0) {
            value = this.convertForCopy(value, type.getComponentType());

            try {
                this.getPropertyUtils().setIndexedProperty(target, propName, index, value);
            } catch (NoSuchMethodException var14) {
                throw new InvocationTargetException(var14, "Cannot set " + propName);
            }
        } else if (key != null) {
            try {
                this.getPropertyUtils().setMappedProperty(target, propName, key, value);
            } catch (NoSuchMethodException var13) {
                throw new InvocationTargetException(var13, "Cannot set " + propName);
            }
        } else {
            value = this.convertForCopy(value, type);

            try {
                this.getPropertyUtils().setSimpleProperty(target, propName, value);
            } catch (NoSuchMethodException var12) {
                throw new InvocationTargetException(var12, "Cannot set " + propName);
            }
        }

    }

    public Map<String, String> describe(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            return new HashMap();
        } else {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Describing bean: " + bean.getClass().getName());
            }

            Map<String, String> description = new HashMap();
            int var6;
            if (bean instanceof DynaBean) {
                DynaProperty[] descriptors = ((DynaBean)bean).getDynaClass().getDynaProperties();
                DynaProperty[] var4 = descriptors;
                int var5 = descriptors.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    DynaProperty descriptor = var4[var6];
                    String name = descriptor.getName();
                    description.put(name, this.getProperty(bean, name));
                }
            } else {
                PropertyDescriptor[] descriptors = this.getPropertyUtils().getPropertyDescriptors(bean);
                Class<?> clazz = bean.getClass();
                PropertyDescriptor[] var12 = descriptors;
                var6 = descriptors.length;

                for(int var13 = 0; var13 < var6; ++var13) {
                    PropertyDescriptor descriptor = var12[var13];
                    String name = descriptor.getName();
                    if (this.getPropertyUtils().getReadMethod(clazz, descriptor) != null) {
                        description.put(name, this.getProperty(bean, name));
                    }
                }
            }

            return description;
        }
    }

    public String[] getArrayProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = this.getPropertyUtils().getProperty(bean, name);
        if (value == null) {
            return null;
        } else if (value instanceof Collection) {
            ArrayList<String> values = new ArrayList();
            Iterator var10 = ((Collection)value).iterator();

            while(var10.hasNext()) {
                Object item = var10.next();
                if (item == null) {
                    values.add(null);
                } else {
                    values.add(this.getConvertUtils().convert(item));
                }
            }

            return (String[])values.toArray(new String[values.size()]);
        } else if (value.getClass().isArray()) {
            int n = Array.getLength(value);
            String[] results = new String[n];

            for(int i = 0; i < n; ++i) {
                Object item = Array.get(value, i);
                if (item == null) {
                    results[i] = null;
                } else {
                    results[i] = this.getConvertUtils().convert(item);
                }
            }

            return results;
        } else {
            String[] results = new String[]{this.getConvertUtils().convert(value)};
            return results;
        }
    }

    public String getIndexedProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = this.getPropertyUtils().getIndexedProperty(bean, name);
        return this.getConvertUtils().convert(value);
    }

    public String getIndexedProperty(Object bean, String name, int index) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = this.getPropertyUtils().getIndexedProperty(bean, name, index);
        return this.getConvertUtils().convert(value);
    }

    public String getMappedProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = this.getPropertyUtils().getMappedProperty(bean, name);
        return this.getConvertUtils().convert(value);
    }

    public String getMappedProperty(Object bean, String name, String key) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = this.getPropertyUtils().getMappedProperty(bean, name, key);
        return this.getConvertUtils().convert(value);
    }

    public String getNestedProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = this.getPropertyUtils().getNestedProperty(bean, name);
        return this.getConvertUtils().convert(value);
    }

    public String getProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return this.getNestedProperty(bean, name);
    }

    public String getSimpleProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = this.getPropertyUtils().getSimpleProperty(bean, name);
        return this.getConvertUtils().convert(value);
    }

    public void populate(Object bean, Map<String, ? extends Object> properties) throws IllegalAccessException, InvocationTargetException {
        if (bean != null && properties != null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("BeanUtils.populate(" + bean + ", " + properties + ")");
            }

            Iterator var3 = properties.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, ? extends Object> entry = (Entry)var3.next();
                String name = (String)entry.getKey();
                if (name != null) {
                    this.setProperty(bean, name, entry.getValue());
                }
            }

        }
    }

    public void setProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        if (this.log.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder("  setProperty(");
            sb.append(bean);
            sb.append(", ");
            sb.append(name);
            sb.append(", ");
            if (value == null) {
                sb.append("<NULL>");
            } else if (value instanceof String) {
                sb.append((String)value);
            } else if (!(value instanceof String[])) {
                sb.append(value.toString());
            } else {
                String[] values = (String[])((String[])value);
                sb.append('[');

                for(int i = 0; i < values.length; ++i) {
                    if (i > 0) {
                        sb.append(',');
                    }

                    sb.append(values[i]);
                }

                sb.append(']');
            }

            sb.append(')');
            this.log.trace(sb.toString());
        }

        Object target = bean;
        Resolver resolver = this.getPropertyUtils().getResolver();

        while(resolver.hasNested(name)) {
            try {
                target = this.getPropertyUtils().getProperty(target, resolver.next(name));
                if (target == null) {
                    return;
                }

                name = resolver.remove(name);
            } catch (NoSuchMethodException var14) {
                return;
            }
        }

        if (this.log.isTraceEnabled()) {
            this.log.trace("    Target bean = " + target);
            this.log.trace("    Target name = " + name);
        }

        String propName = resolver.getProperty(name);
        Class<?> type = null;
        int index = resolver.getIndex(name);
        String key = resolver.getKey(name);
        DynaClass newValue;
        if (target instanceof DynaBean) {
            newValue = ((DynaBean)target).getDynaClass();
            DynaProperty dynaProperty = newValue.getDynaProperty(propName);
            if (dynaProperty == null) {
                return;
            }

            type = dynaPropertyType(dynaProperty, value);
            if (index >= 0 && List.class.isAssignableFrom(type)) {
                type = Object.class;
            }
        } else if (target instanceof Map) {
            type = Object.class;
        } else if (target != null && target.getClass().isArray() && index >= 0) {
            type = Array.get(target, index).getClass();
        } else {
            newValue = null;

            PropertyDescriptor descriptor;
            try {
                descriptor = this.getPropertyUtils().getPropertyDescriptor(target, name);
                if (descriptor == null) {
                    return;
                }
            } catch (NoSuchMethodException var13) {
                return;
            }

            if (descriptor instanceof MappedPropertyDescriptor) {
                if (((MappedPropertyDescriptor)descriptor).getMappedWriteMethod() == null) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Skipping read-only property");
                    }

                    return;
                }

                type = ((MappedPropertyDescriptor)descriptor).getMappedPropertyType();
            } else if (index >= 0 && descriptor instanceof IndexedPropertyDescriptor) {
                if (((IndexedPropertyDescriptor)descriptor).getIndexedWriteMethod() == null) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Skipping read-only property");
                    }

                    return;
                }

                type = ((IndexedPropertyDescriptor)descriptor).getIndexedPropertyType();
            } else if (index >= 0 && List.class.isAssignableFrom(descriptor.getPropertyType())) {
                type = Object.class;
            } else if (key != null) {
                if (descriptor.getReadMethod() == null) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Skipping read-only property");
                    }

                    return;
                }

                type = value == null ? Object.class : value.getClass();
            } else {
                if (descriptor.getWriteMethod() == null) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Skipping read-only property");
                    }

                    return;
                }

                type = descriptor.getPropertyType();
            }
        }

        newValue = null;
        Object newValue1;
        if (type.isArray() && index < 0) {
            if (value == null) {
                String[] values = new String[]{null};
                newValue1 = this.getConvertUtils().convert(values, type);
            } else if (value instanceof String) {
                newValue1 = this.getConvertUtils().convert(value, type);
            } else if (value instanceof String[]) {
                newValue1 = this.getConvertUtils().convert((String[])((String[])value), type);
            } else {
                newValue1 = this.convert(value, type);
            }
        } else if (type.isArray()) {
            if (!(value instanceof String) && value != null) {
                if (value instanceof String[]) {
                    newValue1 = this.getConvertUtils().convert(((String[])((String[])value))[0], type.getComponentType());
                } else {
                    newValue1 = this.convert(value, type.getComponentType());
                }
            } else {
                newValue1 = this.getConvertUtils().convert((String)value, type.getComponentType());
            }
        } else if (value instanceof String) {
            newValue1 = this.getConvertUtils().convert((String)value, type);
        } else if (value instanceof String[]) {
            newValue1 = this.getConvertUtils().convert(((String[])((String[])value))[0], type);
        } else {
            newValue1 = this.convert(value, type);
        }

        try {
            this.getPropertyUtils().setProperty(target, name, newValue1);
        } catch (NoSuchMethodException var12) {
            throw new InvocationTargetException(var12, "Cannot set " + propName);
        }
    }

    public ConvertUtilsBean getConvertUtils() {
        return this.convertUtilsBean;
    }

    public MyPropertyUtilsBean getPropertyUtils() {
        return this.propertyUtilsBean;
    }

    public boolean initCause(Throwable throwable, Throwable cause) {
        if (INIT_CAUSE_METHOD != null && cause != null) {
            try {
                INIT_CAUSE_METHOD.invoke(throwable, cause);
                return true;
            } catch (Throwable var4) {
                return false;
            }
        } else {
            return false;
        }
    }

    protected Object convert(Object value, Class<?> type) {
        Converter converter = this.getConvertUtils().lookup(type);
        if (converter != null) {
            this.log.trace("        USING CONVERTER " + converter);
            return converter.convert(type, value);
        } else {
            return value;
        }
    }

    private Object convertForCopy(Object value, Class<?> type) {
        return value != null ? this.convert(value, type) : value;
    }

    private static Method getInitCauseMethod() {
        Log log;
        try {
            Class<?>[] paramsClasses = new Class[]{Throwable.class};
            return Throwable.class.getMethod("initCause", paramsClasses);
        } catch (NoSuchMethodException var2) {
            log = LogFactory.getLog(MyBeanUtils.class);
            if (log.isWarnEnabled()) {
                log.warn("Throwable does not have initCause() method in JDK 1.3");
            }

            return null;
        } catch (Throwable var3) {
            log = LogFactory.getLog(MyBeanUtils.class);
            if (log.isWarnEnabled()) {
                log.warn("Error getting the Throwable initCause() method", var3);
            }

            return null;
        }
    }

    private static Class<?> dynaPropertyType(DynaProperty dynaProperty, Object value) {
        if (!dynaProperty.isMapped()) {
            return dynaProperty.getType();
        } else {
            return value == null ? String.class : value.getClass();
        }
    }
}
