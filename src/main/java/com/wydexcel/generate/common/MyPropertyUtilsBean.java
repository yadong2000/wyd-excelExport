//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wydexcel.generate.common;

import org.apache.commons.beanutils.*;
import org.apache.commons.beanutils.expression.DefaultResolver;
import org.apache.commons.beanutils.expression.Resolver;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyPropertyUtilsBean {
    private Resolver resolver = new DefaultResolver();
    private WeakFastHashMap<Class<?>, BeanIntrospectionData> descriptorsCache = null;
    private WeakFastHashMap<Class<?>, FastHashMap> mappedDescriptorsCache = null;
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private final Log log = LogFactory.getLog(PropertyUtils.class);
    private final List<BeanIntrospector> introspectors;

    protected static MyPropertyUtilsBean getInstance() {
        return BeanUtilsBean.getInstance().getPropertyUtils();
    }

    public MyPropertyUtilsBean() {
        this.descriptorsCache = new WeakFastHashMap();
        this.descriptorsCache.setFast(true);
        this.mappedDescriptorsCache = new WeakFastHashMap();
        this.mappedDescriptorsCache.setFast(true);
        this.introspectors = new CopyOnWriteArrayList();
        this.resetBeanIntrospectors();
    }

    public Resolver getResolver() {
        return this.resolver;
    }

    public void setResolver(Resolver resolver) {
        if (resolver == null) {
            this.resolver = new DefaultResolver();
        } else {
            this.resolver = resolver;
        }

    }

    public final void resetBeanIntrospectors() {
        this.introspectors.clear();
        this.introspectors.add(DefaultBeanIntrospector.INSTANCE);
    }

    public void addBeanIntrospector(BeanIntrospector introspector) {
        if (introspector == null) {
            throw new IllegalArgumentException("BeanIntrospector must not be null!");
        } else {
            this.introspectors.add(introspector);
        }
    }

    public boolean removeBeanIntrospector(BeanIntrospector introspector) {
        return this.introspectors.remove(introspector);
    }

    public void clearDescriptors() {
        this.descriptorsCache.clear();
        this.mappedDescriptorsCache.clear();
        Introspector.flushCaches();
    }

    public void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        } else if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        } else {
            int var5;
            int var6;
            String name;
            Object value;
            if (orig instanceof DynaBean) {
                DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
                DynaProperty[] var4 = origDescriptors;
                var5 = origDescriptors.length;

                for (var6 = 0; var6 < var5; ++var6) {
                    DynaProperty origDescriptor = var4[var6];
                    name = origDescriptor.getName();
                    if (this.isReadable(orig, name) && this.isWriteable(dest, name)) {
                        try {
                            value = ((DynaBean) orig).get(name);
                            if (dest instanceof DynaBean) {
                                ((DynaBean) dest).set(name, value);
                            } else {
                                this.setSimpleProperty(dest, name, value);
                            }
                        } catch (NoSuchMethodException var12) {
                            if (this.log.isDebugEnabled()) {
                                this.log.debug("Error writing to '" + name + "' on class '" + dest.getClass() + "'", var12);
                            }
                        }
                    }
                }
            } else if (orig instanceof Map) {
                Iterator entries = ((Map) orig).entrySet().iterator();

                while (true) {
                    Entry entry;
                    String Entryname;
                    do {
                        if (!entries.hasNext()) {
                            return;
                        }

                        entry = (Entry) entries.next();
                        Entryname = (String) entry.getKey();
                    } while (!this.isWriteable(dest, Entryname));

                    try {
                        if (dest instanceof DynaBean) {
                            ((DynaBean) dest).set(Entryname, entry.getValue());
                        } else {
                            this.setSimpleProperty(dest, Entryname, entry.getValue());
                        }
                    } catch (NoSuchMethodException var11) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Error writing to '" + Entryname + "' on class '" + dest.getClass() + "'", var11);
                        }
                    }
                }
            } else {
                PropertyDescriptor[] origDescriptors = this.getPropertyDescriptors(orig);
                PropertyDescriptor[] var16 = origDescriptors;
                var5 = origDescriptors.length;

                for (var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor origDescriptor = var16[var6];
                    name = origDescriptor.getName();
                    if (this.isReadable(orig, name) && this.isWriteable(dest, name)) {
                        try {
                            value = this.getSimpleProperty(orig, name);
                            if (dest instanceof DynaBean) {
                                ((DynaBean) dest).set(name, value);
                            } else {
                                this.setSimpleProperty(dest, name, value);
                            }
                        } catch (NoSuchMethodException var10) {
                            if (this.log.isDebugEnabled()) {
                                this.log.debug("Error writing to '" + name + "' on class '" + dest.getClass() + "'", var10);
                            }
                        }
                    }
                }
            }

        }
    }

    public Map<String, Object> describe(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else {
            Map<String, Object> description = new HashMap();
            int var5;
            int var6;
            String name;
            if (bean instanceof DynaBean) {
                DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass().getDynaProperties();
                DynaProperty[] var4 = descriptors;
                var5 = descriptors.length;

                for (var6 = 0; var6 < var5; ++var6) {
                    DynaProperty descriptor = var4[var6];
                    name = descriptor.getName();
                    description.put(name, this.getProperty(bean, name));
                }
            } else {
                PropertyDescriptor[] descriptors = this.getPropertyDescriptors(bean);
                PropertyDescriptor[] var10 = descriptors;
                var5 = descriptors.length;

                for (var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor descriptor = var10[var6];
                    name = descriptor.getName();
                    if (descriptor.getReadMethod() != null) {
                        description.put(name, this.getProperty(bean, name));
                    }
                }
            }

            return description;
        }
    }

    public Object getIndexedProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            boolean var3 = true;

            int index;
            try {
                index = this.resolver.getIndex(name);
            } catch (IllegalArgumentException var5) {
                throw new IllegalArgumentException("Invalid indexed property '" + name + "' on bean class '" + bean.getClass() + "' " + var5.getMessage());
            }

            if (index < 0) {
                throw new IllegalArgumentException("Invalid indexed property '" + name + "' on bean class '" + bean.getClass() + "'");
            } else {
                name = this.resolver.getProperty(name);
                return this.getIndexedProperty(bean, name, index);
            }
        }
    }

    public Object getIndexedProperty(Object bean, String name, int index) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else {
            if (name == null || name.length() == 0) {
                if (bean.getClass().isArray()) {
                    return Array.get(bean, index);
                }

                if (bean instanceof List) {
                    return ((List) bean).get(index);
                }
            }

            if (name == null) {
                throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
            } else if (bean instanceof DynaBean) {
                DynaProperty descriptor = ((DynaBean) bean).getDynaClass().getDynaProperty(name);
                if (descriptor == null) {
                    throw new NoSuchMethodException("Unknown property '" + name + "' on bean class '" + bean.getClass() + "'");
                } else {
                    return ((DynaBean) bean).get(name, index);
                }
            } else {
                PropertyDescriptor descriptor = this.getPropertyDescriptor(bean, name);
                if (descriptor == null) {
                    throw new NoSuchMethodException("Unknown property '" + name + "' on bean class '" + bean.getClass() + "'");
                } else {
                    Method readMethod;
                    if (descriptor instanceof IndexedPropertyDescriptor) {
                        readMethod = ((IndexedPropertyDescriptor) descriptor).getIndexedReadMethod();
                        readMethod = MethodUtils.getAccessibleMethod(bean.getClass(), readMethod);
                        if (readMethod != null) {
                            Object[] subscript = new Object[]{new Integer(index)};

                            try {
                                return this.invokeMethod(readMethod, bean, subscript);
                            } catch (InvocationTargetException var9) {
                                if (var9.getTargetException() instanceof IndexOutOfBoundsException) {
                                    throw (IndexOutOfBoundsException) var9.getTargetException();
                                }

                                throw var9;
                            } catch (IllegalArgumentException var9) {

                            }
                        }
                    }

                    readMethod = this.getReadMethod(bean.getClass(), descriptor);
                    if (readMethod == null) {
                        throw new NoSuchMethodException("Property '" + name + "' has no " + "getter method on bean class '" + bean.getClass() + "'");
                    } else {
                        Object value = this.invokeMethod(readMethod, bean, EMPTY_OBJECT_ARRAY);
                        if (!value.getClass().isArray()) {
                            if (!(value instanceof List)) {
                                throw new IllegalArgumentException("Property '" + name + "' is not indexed on bean class '" + bean.getClass() + "'");
                            } else {
                                return ((List) value).get(index);
                            }
                        } else {
                            try {
                                return Array.get(value, index);
                            } catch (ArrayIndexOutOfBoundsException var8) {
                                throw new ArrayIndexOutOfBoundsException("Index: " + index + ", Size: " + Array.getLength(value) + " for property '" + name + "'");
                            }
                        }
                    }
                }
            }
        }
    }

    public Object getMappedProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            String key = null;

            try {
                key = this.resolver.getKey(name);
            } catch (IllegalArgumentException var5) {
                throw new IllegalArgumentException("Invalid mapped property '" + name + "' on bean class '" + bean.getClass() + "' " + var5.getMessage());
            }

            if (key == null) {
                throw new IllegalArgumentException("Invalid mapped property '" + name + "' on bean class '" + bean.getClass() + "'");
            } else {
                name = this.resolver.getProperty(name);
                return this.getMappedProperty(bean, name, key);
            }
        }
    }

    public Object getMappedProperty(Object bean, String name, String key) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else if (key == null) {
            throw new IllegalArgumentException("No key specified for property '" + name + "' on bean class " + bean.getClass() + "'");
        } else if (bean instanceof DynaBean) {
            DynaProperty descriptor = ((DynaBean) bean).getDynaClass().getDynaProperty(name);
            if (descriptor == null) {
                throw new NoSuchMethodException("Unknown property '" + name + "'+ on bean class '" + bean.getClass() + "'");
            } else {
                return ((DynaBean) bean).get(name, key);
            }
        } else {
            Object result = null;
            PropertyDescriptor descriptor = this.getPropertyDescriptor(bean, name);
            if (descriptor == null) {
                throw new NoSuchMethodException("Unknown property '" + name + "'+ on bean class '" + bean.getClass() + "'");
            } else {
                Method readMethod;
                if (descriptor instanceof MappedPropertyDescriptor) {
                    readMethod = ((MappedPropertyDescriptor) descriptor).getMappedReadMethod();
                    readMethod = MethodUtils.getAccessibleMethod(bean.getClass(), readMethod);
                    if (readMethod == null) {
                        throw new NoSuchMethodException("Property '" + name + "' has no mapped getter method on bean class '" + bean.getClass() + "'");
                    }

                    Object[] keyArray = new Object[]{key};
                    result = this.invokeMethod(readMethod, bean, keyArray);
                } else {
                    readMethod = this.getReadMethod(bean.getClass(), descriptor);
                    if (readMethod == null) {
                        throw new NoSuchMethodException("Property '" + name + "' has no mapped getter method on bean class '" + bean.getClass() + "'");
                    }

                    Object invokeResult = this.invokeMethod(readMethod, bean, EMPTY_OBJECT_ARRAY);
                    if (invokeResult instanceof Map) {
                        result = ((Map) invokeResult).get(key);
                    }
                }

                return result;
            }
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public FastHashMap getMappedPropertyDescriptors(Class<?> beanClass) {
        return beanClass == null ? null : (FastHashMap) this.mappedDescriptorsCache.get(beanClass);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public FastHashMap getMappedPropertyDescriptors(Object bean) {
        return bean == null ? null : this.getMappedPropertyDescriptors(bean.getClass());
    }

    public Object getNestedProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            while (this.resolver.hasNested(name)) {
                String next = this.resolver.next(name);
                Object nestedBean = null;
                if (bean instanceof Map) {
                    nestedBean = this.getPropertyOfMapBean((Map) bean, next);
                } else if (this.resolver.isMapped(next)) {
                    nestedBean = this.getMappedProperty(bean, next);
                } else if (this.resolver.isIndexed(next)) {
                    nestedBean = this.getIndexedProperty(bean, next);
                } else {
                    nestedBean = this.getSimpleProperty(bean, next);
                }

                if (nestedBean == null) {
                    throw new NestedNullException("Null property value for '" + name + "' on bean class '" + bean.getClass() + "'");
                }

                bean = nestedBean;
                name = this.resolver.remove(name);
            }

            if (bean instanceof Map) {
                bean = this.getPropertyOfMapBean((Map) bean, name);
            } else if (this.resolver.isMapped(name)) {
                bean = this.getMappedProperty(bean, name);
            } else if (this.resolver.isIndexed(name)) {
                bean = this.getIndexedProperty(bean, name);
            } else {
                bean = this.getSimpleProperty(bean, name);
            }

            return bean;
        }
    }

    protected Object getPropertyOfMapBean(Map<?, ?> bean, String propertyName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (this.resolver.isMapped(propertyName)) {
            String name = this.resolver.getProperty(propertyName);
            if (name == null || name.length() == 0) {
                propertyName = this.resolver.getKey(propertyName);
            }
        }

        if (!this.resolver.isIndexed(propertyName) && !this.resolver.isMapped(propertyName)) {
            return bean.get(propertyName);
        } else {
            throw new IllegalArgumentException("Indexed or mapped properties are not supported on objects of type Map: " + propertyName);
        }
    }

    public Object getProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return this.getNestedProperty(bean, name);
    }

    public PropertyDescriptor getPropertyDescriptor(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            Object result;
            while (this.resolver.hasNested(name)) {
                String next = this.resolver.next(name);
                result = this.getProperty(bean, next);
                if (result == null) {
                    throw new NestedNullException("Null property value for '" + next + "' on bean class '" + bean.getClass() + "'");
                }

                bean = result;
                name = this.resolver.remove(name);
            }

            name = this.resolver.getProperty(name);
            if (name == null) {
                return null;
            } else {
                BeanIntrospectionData data = this.getIntrospectionData(bean.getClass());
                PropertyDescriptor descriptor = data.getDescriptor(name);
                if (descriptor != null) {
                    return descriptor;
                } else {
                    FastHashMap mappedDescriptors = this.getMappedPropertyDescriptors(bean);
                    if (mappedDescriptors == null) {
                        mappedDescriptors = new FastHashMap();
                        mappedDescriptors.setFast(true);
                        this.mappedDescriptorsCache.put(bean.getClass(), mappedDescriptors);
                    }

                    descriptor = (PropertyDescriptor) mappedDescriptors.get(name);
                    if (descriptor == null) {
                        try {
                            descriptor = new MappedPropertyDescriptor(name, bean.getClass());
                        } catch (IntrospectionException var7) {
                        }

                        if (descriptor != null) {
                            mappedDescriptors.put(name, descriptor);
                        }
                    }

                    return (PropertyDescriptor) descriptor;
                }
            }
        }
    }

    public PropertyDescriptor[] getPropertyDescriptors(Class<?> beanClass) {
        return this.getIntrospectionData(beanClass).getDescriptors();
    }

    public PropertyDescriptor[] getPropertyDescriptors(Object bean) {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else {
            return this.getPropertyDescriptors(bean.getClass());
        }
    }

    public Class<?> getPropertyEditorClass(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            PropertyDescriptor descriptor = this.getPropertyDescriptor(bean, name);
            return descriptor != null ? descriptor.getPropertyEditorClass() : null;
        }
    }

    public Class<?> getPropertyType(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            while (this.resolver.hasNested(name)) {
                String next = this.resolver.next(name);
                Object nestedBean = this.getProperty(bean, next);
                if (nestedBean == null) {
                    throw new NestedNullException("Null property value for '" + next + "' on bean class '" + bean.getClass() + "'");
                }

                bean = nestedBean;
                name = this.resolver.remove(name);
            }

            name = this.resolver.getProperty(name);
            if (bean instanceof DynaBean) {
                DynaProperty descriptor = ((DynaBean) bean).getDynaClass().getDynaProperty(name);
                if (descriptor == null) {
                    return null;
                } else {
                    Class<?> type = descriptor.getType();
                    if (type == null) {
                        return null;
                    } else if (type.isArray()) {
                        return type.getComponentType();
                    } else {
                        return type;
                    }
                }
            } else {
                PropertyDescriptor descriptor = this.getPropertyDescriptor(bean, name);
                if (descriptor == null) {
                    return null;
                } else if (descriptor instanceof IndexedPropertyDescriptor) {
                    return ((IndexedPropertyDescriptor) descriptor).getIndexedPropertyType();
                } else if (descriptor instanceof MappedPropertyDescriptor) {
                    return ((MappedPropertyDescriptor) descriptor).getMappedPropertyType();
                } else {
                    return descriptor.getPropertyType();
                }
            }
        }
    }

    public Method getReadMethod(PropertyDescriptor descriptor) {
        return MethodUtils.getAccessibleMethod(descriptor.getReadMethod());
    }

    Method getReadMethod(Class<?> clazz, PropertyDescriptor descriptor) {
        return MethodUtils.getAccessibleMethod(clazz, descriptor.getReadMethod());
    }

    public Object getSimpleProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else if (this.resolver.hasNested(name)) {
            throw new IllegalArgumentException("Nested property names are not allowed: Property '" + name + "' on bean class '" + bean.getClass() + "'");
        } else if (this.resolver.isIndexed(name)) {
            throw new IllegalArgumentException("Indexed property names are not allowed: Property '" + name + "' on bean class '" + bean.getClass() + "'");
        } else if (this.resolver.isMapped(name)) {
            throw new IllegalArgumentException("Mapped property names are not allowed: Property '" + name + "' on bean class '" + bean.getClass() + "'");
        } else if (bean instanceof DynaBean) {
            DynaProperty descriptor = ((DynaBean) bean).getDynaClass().getDynaProperty(name);
            if (descriptor == null) {
                throw new NoSuchMethodException("Unknown property '" + name + "' on dynaclass '" + ((DynaBean) bean).getDynaClass() + "'");
            } else {
                return ((DynaBean) bean).get(name);
            }
        } else {
            PropertyDescriptor descriptor = this.getPropertyDescriptor(bean, name);
            if (descriptor == null) {
                throw new NoSuchMethodException("Unknown property '" + name + "' on class '" + bean.getClass() + "'");
            } else {
                Method readMethod = this.getReadMethod(bean.getClass(), descriptor);
                if (readMethod == null) {
                    throw new NoSuchMethodException("Property '" + name + "' has no getter method in class '" + bean.getClass() + "'");
                } else {
                    Object value = this.invokeMethod(readMethod, bean, EMPTY_OBJECT_ARRAY);
                    return value;
                }
            }
        }
    }

    public Method getWriteMethod(PropertyDescriptor descriptor) {
        return MethodUtils.getAccessibleMethod(descriptor.getWriteMethod());
    }

    public Method getWriteMethod(Class<?> clazz, PropertyDescriptor descriptor) {
        BeanIntrospectionData data = this.getIntrospectionData(clazz);
        return MethodUtils.getAccessibleMethod(clazz, data.getWriteMethod(clazz, descriptor));
    }

    public boolean isReadable(Object bean, String name) {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            while (this.resolver.hasNested(name)) {
                String next = this.resolver.next(name);
                Object nestedBean = null;

                try {
                    nestedBean = this.getProperty(bean, next);
                } catch (IllegalAccessException var9) {
                    return false;
                } catch (InvocationTargetException var10) {
                    return false;
                } catch (NoSuchMethodException var11) {
                    return false;
                }

                if (nestedBean == null) {
                    throw new NestedNullException("Null property value for '" + next + "' on bean class '" + bean.getClass() + "'");
                }

                bean = nestedBean;
                name = this.resolver.remove(name);
            }

            name = this.resolver.getProperty(name);
            if (bean instanceof WrapDynaBean) {
                bean = ((WrapDynaBean) bean).getInstance();
            }

            if (bean instanceof DynaBean) {
                return ((DynaBean) bean).getDynaClass().getDynaProperty(name) != null;
            } else {
                try {
                    PropertyDescriptor desc = this.getPropertyDescriptor(bean, name);
                    if (desc != null) {
                        Method readMethod = this.getReadMethod(bean.getClass(), desc);
                        if (readMethod == null) {
                            if (desc instanceof IndexedPropertyDescriptor) {
                                readMethod = ((IndexedPropertyDescriptor) desc).getIndexedReadMethod();
                            } else if (desc instanceof MappedPropertyDescriptor) {
                                readMethod = ((MappedPropertyDescriptor) desc).getMappedReadMethod();
                            }

                            readMethod = MethodUtils.getAccessibleMethod(bean.getClass(), readMethod);
                        }

                        return readMethod != null;
                    } else {
                        return false;
                    }
                } catch (IllegalAccessException var6) {
                    return false;
                } catch (InvocationTargetException var7) {
                    return false;
                } catch (NoSuchMethodException var8) {
                    return false;
                }
            }
        }
    }

    public boolean isWriteable(Object bean, String name) {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            while (this.resolver.hasNested(name)) {
                String next = this.resolver.next(name);
                Object nestedBean = null;

                try {
                    nestedBean = this.getProperty(bean, next);
                } catch (IllegalAccessException var9) {
                    return false;
                } catch (InvocationTargetException var10) {
                    return false;
                } catch (NoSuchMethodException var11) {
                    return false;
                }

                if (nestedBean == null) {
                    throw new NestedNullException("Null property value for '" + next + "' on bean class '" + bean.getClass() + "'");
                }

                bean = nestedBean;
                name = this.resolver.remove(name);
            }

            name = this.resolver.getProperty(name);
            if (bean instanceof WrapDynaBean) {
                bean = ((WrapDynaBean) bean).getInstance();
            }

            if (bean instanceof DynaBean) {
                return ((DynaBean) bean).getDynaClass().getDynaProperty(name) != null;
            } else {
                try {
                    PropertyDescriptor desc = this.getPropertyDescriptor(bean, name);
                    if (desc != null) {
                        Method writeMethod = this.getWriteMethod(bean.getClass(), desc);
                        if (writeMethod == null) {
                            if (desc instanceof IndexedPropertyDescriptor) {
                                writeMethod = ((IndexedPropertyDescriptor) desc).getIndexedWriteMethod();
                            } else if (desc instanceof MappedPropertyDescriptor) {
                                writeMethod = ((MappedPropertyDescriptor) desc).getMappedWriteMethod();
                            }

                            writeMethod = MethodUtils.getAccessibleMethod(bean.getClass(), writeMethod);
                        }

                        return writeMethod != null;
                    } else {
                        return false;
                    }
                } catch (IllegalAccessException var6) {
                    return false;
                } catch (InvocationTargetException var7) {
                    return false;
                } catch (NoSuchMethodException var8) {
                    return false;
                }
            }
        }
    }

    public void setIndexedProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            boolean var4 = true;

            int index;
            try {
                index = this.resolver.getIndex(name);
            } catch (IllegalArgumentException var6) {
                throw new IllegalArgumentException("Invalid indexed property '" + name + "' on bean class '" + bean.getClass() + "'");
            }

            if (index < 0) {
                throw new IllegalArgumentException("Invalid indexed property '" + name + "' on bean class '" + bean.getClass() + "'");
            } else {
                name = this.resolver.getProperty(name);
                this.setIndexedProperty(bean, name, index, value);
            }
        }
    }

    public void setIndexedProperty(Object bean, String name, int index, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else {
            if (name == null || name.length() == 0) {
                if (bean.getClass().isArray()) {
                    Array.set(bean, index, value);
                    return;
                }

                if (bean instanceof List) {
                    List<Object> list = toObjectList(bean);
                    list.set(index, value);
                    return;
                }
            }

            if (name == null) {
                throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
            } else if (bean instanceof DynaBean) {
                DynaProperty descriptor = ((DynaBean) bean).getDynaClass().getDynaProperty(name);
                if (descriptor == null) {
                    throw new NoSuchMethodException("Unknown property '" + name + "' on bean class '" + bean.getClass() + "'");
                } else {
                    ((DynaBean) bean).set(name, index, value);
                }
            } else {
                PropertyDescriptor descriptor = this.getPropertyDescriptor(bean, name);
                if (descriptor == null) {
                    throw new NoSuchMethodException("Unknown property '" + name + "' on bean class '" + bean.getClass() + "'");
                } else {
                    Method writeMethod;
                    if (descriptor instanceof IndexedPropertyDescriptor) {
                        writeMethod = ((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod();
                        writeMethod = MethodUtils.getAccessibleMethod(bean.getClass(), writeMethod);
                        if (writeMethod != null) {
                            Object[] subscript = new Object[]{new Integer(index), value};

                            try {
                                if (this.log.isTraceEnabled()) {
                                    String valueClassName = value == null ? "<null>" : value.getClass().getName();
                                    this.log.trace("setSimpleProperty: Invoking method " + writeMethod + " with index=" + index + ", value=" + value + " (class " + valueClassName + ")");
                                }

                                this.invokeMethod(writeMethod, bean, subscript);
                                return;
                            } catch (InvocationTargetException var9) {
                                if (var9.getTargetException() instanceof IndexOutOfBoundsException) {
                                    throw (IndexOutOfBoundsException) var9.getTargetException();
                                }

                                throw var9;
                            }
                        }
                    }

                    writeMethod = this.getReadMethod(bean.getClass(), descriptor);
                    if (writeMethod == null) {
                        throw new NoSuchMethodException("Property '" + name + "' has no getter method on bean class '" + bean.getClass() + "'");
                    } else {
                        Object array = this.invokeMethod(writeMethod, bean, EMPTY_OBJECT_ARRAY);
                        if (!array.getClass().isArray()) {
                            if (!(array instanceof List)) {
                                throw new IllegalArgumentException("Property '" + name + "' is not indexed on bean class '" + bean.getClass() + "'");
                            }

                            List<Object> list = toObjectList(array);
                            list.set(index, value);
                        } else {
                            Array.set(array, index, value);
                        }

                    }
                }
            }
        }
    }

    public void setMappedProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            String key = null;

            try {
                key = this.resolver.getKey(name);
            } catch (IllegalArgumentException var6) {
                throw new IllegalArgumentException("Invalid mapped property '" + name + "' on bean class '" + bean.getClass() + "'");
            }

            if (key == null) {
                throw new IllegalArgumentException("Invalid mapped property '" + name + "' on bean class '" + bean.getClass() + "'");
            } else {
                name = this.resolver.getProperty(name);
                this.setMappedProperty(bean, name, key, value);
            }
        }
    }

    public void setMappedProperty(Object bean, String name, String key, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else if (key == null) {
            throw new IllegalArgumentException("No key specified for property '" + name + "' on bean class '" + bean.getClass() + "'");
        } else if (bean instanceof DynaBean) {
            DynaProperty descriptor = ((DynaBean) bean).getDynaClass().getDynaProperty(name);
            if (descriptor == null) {
                throw new NoSuchMethodException("Unknown property '" + name + "' on bean class '" + bean.getClass() + "'");
            } else {
                ((DynaBean) bean).set(name, key, value);
            }
        } else {
            PropertyDescriptor descriptor = this.getPropertyDescriptor(bean, name);
            if (descriptor == null) {
                throw new NoSuchMethodException("Unknown property '" + name + "' on bean class '" + bean.getClass() + "'");
            } else {
                Method mappedWriteMethod;
                if (descriptor instanceof MappedPropertyDescriptor) {
                    mappedWriteMethod = ((MappedPropertyDescriptor) descriptor).getMappedWriteMethod();
                    mappedWriteMethod = MethodUtils.getAccessibleMethod(bean.getClass(), mappedWriteMethod);
                    if (mappedWriteMethod == null) {
                        throw new NoSuchMethodException("Property '" + name + "' has no mapped setter method" + "on bean class '" + bean.getClass() + "'");
                    }

                    Object[] params = new Object[]{key, value};
                    if (this.log.isTraceEnabled()) {
                        String valueClassName = value == null ? "<null>" : value.getClass().getName();
                        this.log.trace("setSimpleProperty: Invoking method " + mappedWriteMethod + " with key=" + key + ", value=" + value + " (class " + valueClassName + ")");
                    }

                    this.invokeMethod(mappedWriteMethod, bean, params);
                } else {
                    mappedWriteMethod = this.getReadMethod(bean.getClass(), descriptor);
                    if (mappedWriteMethod == null) {
                        throw new NoSuchMethodException("Property '" + name + "' has no mapped getter method on bean class '" + bean.getClass() + "'");
                    }

                    Object invokeResult = this.invokeMethod(mappedWriteMethod, bean, EMPTY_OBJECT_ARRAY);
                    if (invokeResult instanceof Map) {
                        Map<String, Object> map = toPropertyMap(invokeResult);
                        map.put(key, value);
                    }
                }

            }
        }
    }

    public void setNestedProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else {
            while (this.resolver.hasNested(name)) {
                String next = this.resolver.next(name);
                Object nestedBean = null;
                if (bean instanceof Map) {
                    nestedBean = this.getPropertyOfMapBean((Map) bean, next);
                } else if (this.resolver.isMapped(next)) {
                    nestedBean = this.getMappedProperty(bean, next);
                } else if (this.resolver.isIndexed(next)) {
                    nestedBean = this.getIndexedProperty(bean, next);
                } else {
                    nestedBean = this.getSimpleProperty(bean, next);
                }

                if (nestedBean == null) {
                    throw new NestedNullException("Null property value for '" + name + "' on bean class '" + bean.getClass() + "'");
                }

                bean = nestedBean;
                name = this.resolver.remove(name);
            }

            if (bean instanceof Map) {
                this.setPropertyOfMapBean(toPropertyMap(bean), name, value);
            } else if (this.resolver.isMapped(name)) {
                this.setMappedProperty(bean, name, value);
            } else if (this.resolver.isIndexed(name)) {
                this.setIndexedProperty(bean, name, value);
            } else {
                this.setSimpleProperty(bean, name, value);
            }

        }
    }

    protected void setPropertyOfMapBean(Map<String, Object> bean, String propertyName, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (this.resolver.isMapped(propertyName)) {
            String name = this.resolver.getProperty(propertyName);
            if (name == null || name.length() == 0) {
                propertyName = this.resolver.getKey(propertyName);
            }
        }

        if (!this.resolver.isIndexed(propertyName) && !this.resolver.isMapped(propertyName)) {
            bean.put(propertyName, value);
        } else {
            throw new IllegalArgumentException("Indexed or mapped properties are not supported on objects of type Map: " + propertyName);
        }
    }

    public void setProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        this.setNestedProperty(bean, name, value);
    }

    public void setSimpleProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        } else if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        } else if (this.resolver.hasNested(name)) {
            throw new IllegalArgumentException("Nested property names are not allowed: Property '" + name + "' on bean class '" + bean.getClass() + "'");
        } else if (this.resolver.isIndexed(name)) {
            throw new IllegalArgumentException("Indexed property names are not allowed: Property '" + name + "' on bean class '" + bean.getClass() + "'");
        } else if (this.resolver.isMapped(name)) {
            throw new IllegalArgumentException("Mapped property names are not allowed: Property '" + name + "' on bean class '" + bean.getClass() + "'");
        } else if (bean instanceof DynaBean) {
            DynaProperty descriptor = ((DynaBean) bean).getDynaClass().getDynaProperty(name);
            if (descriptor == null) {
                throw new NoSuchMethodException("Unknown property '" + name + "' on dynaclass '" + ((DynaBean) bean).getDynaClass() + "'");
            } else {
                ((DynaBean) bean).set(name, value);
            }
        } else {
            PropertyDescriptor descriptor = this.getPropertyDescriptor(bean, name);
            if (descriptor == null) {
                throw new NoSuchMethodException("Unknown property '" + name + "' on class '" + bean.getClass() + "'");
            } else {
                Method writeMethod = this.getWriteMethod(bean.getClass(), descriptor);
                if (writeMethod == null) {
                    throw new NoSuchMethodException("Property '" + name + "' has no setter method in class '" + bean.getClass() + "'");
                } else {
                    Object[] values = new Object[]{value};
                    if (this.log.isTraceEnabled()) {
                        String valueClassName = value == null ? "<null>" : value.getClass().getName();
                        this.log.trace("setSimpleProperty: Invoking method " + writeMethod + " with value " + value + " (class " + valueClassName + ")");
                    }
                    try {
                        this.invokeMethod(writeMethod, bean, values);
                    } catch (IllegalArgumentException var10) {
//                        log.error(var10);
                    }

                }
            }
        }
    }

    private Object invokeMethod(Method method, Object bean, Object[] values) throws IllegalAccessException, InvocationTargetException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified - this should have been checked before reaching this method");
        } else {
            String valueString;
            int i;
            Class[] parTypes;
            String expectedString;
            IllegalArgumentException e;
            try {
                return method.invoke(bean, values);
            } catch (NullPointerException var9) {
                valueString = "";
                if (values != null) {
                    for (i = 0; i < values.length; ++i) {
                        if (i > 0) {
                            valueString = valueString + ", ";
                        }

                        if (values[i] == null) {
                            valueString = valueString + "<null>";
                        } else {
                            valueString = valueString + values[i].getClass().getName();
                        }
                    }
                }

                expectedString = "";
                parTypes = method.getParameterTypes();
                if (parTypes != null) {
                    for (i = 0; i < parTypes.length; ++i) {
                        if (i > 0) {
                            expectedString = expectedString + ", ";
                        }

                        expectedString = expectedString + parTypes[i].getName();
                    }
                }

                e = new IllegalArgumentException("Cannot invoke " + method.getDeclaringClass().getName() + "." + method.getName() + " on bean class '" + bean.getClass() + "' - " + var9.getMessage() + " - had objects of type \"" + valueString + "\" but expected signature \"" + expectedString + "\"");
                if (!MyBeanUtils.initCause(e, var9)) {
                    this.log.error("Method invocation failed", var9);
                }

                throw e;
            } catch (IllegalArgumentException var10) {
                valueString = "";
                if (values != null) {
                    for (i = 0; i < values.length; ++i) {
                        if (i > 0) {
                            valueString = valueString + ", ";
                        }

                        if (values[i] == null) {
                            valueString = valueString + "<null>";
                        } else {
                            valueString = valueString + values[i].getClass().getName();
                        }
                    }
                }

                expectedString = "";
                parTypes = method.getParameterTypes();
                if (parTypes != null) {
                    for (i = 0; i < parTypes.length; ++i) {
                        if (i > 0) {
                            expectedString = expectedString + ", ";
                        }

                        expectedString = expectedString + parTypes[i].getName();
                    }
                }

                e = new IllegalArgumentException("Cannot invoke " + method.getDeclaringClass().getName() + "." + method.getName() + " on bean class '" + bean.getClass() + "' - " + var10.getMessage() + " - had objects of type \"" + valueString + "\" but expected signature \"" + expectedString + "\"");
                if (!MyBeanUtils.initCause(e, var10)) {
                    this.log.error("Method invocation failed", var10);
                }

                throw e;
            }
        }
    }

    private BeanIntrospectionData getIntrospectionData(Class<?> beanClass) {
        if (beanClass == null) {
            throw new IllegalArgumentException("No bean class specified");
        } else {
            BeanIntrospectionData data = (BeanIntrospectionData) this.descriptorsCache.get(beanClass);
            if (data == null) {
                data = this.fetchIntrospectionData(beanClass);
                this.descriptorsCache.put(beanClass, data);
            }

            return data;
        }
    }

    private BeanIntrospectionData fetchIntrospectionData(Class<?> beanClass) {
        DefaultIntrospectionContext ictx = new DefaultIntrospectionContext(beanClass);
        Iterator var3 = this.introspectors.iterator();

        while (var3.hasNext()) {
            BeanIntrospector bi = (BeanIntrospector) var3.next();

            try {
                bi.introspect(ictx);
            } catch (IntrospectionException var6) {
                this.log.error("Exception during introspection", var6);
            }
        }

        return new BeanIntrospectionData(ictx.getPropertyDescriptors());
    }

    private static List<Object> toObjectList(Object obj) {
        List<Object> list = (List) obj;
        return list;
    }

    private static Map<String, Object> toPropertyMap(Object obj) {
        Map<String, Object> map = (Map) obj;
        return map;
    }
}
