//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wydexcel.generate.common;

import org.apache.commons.beanutils.IntrospectionContext;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class DefaultIntrospectionContext implements IntrospectionContext {
    private static final PropertyDescriptor[] EMPTY_DESCRIPTORS = new PropertyDescriptor[0];
    private final Class<?> currentClass;
    private final Map<String, PropertyDescriptor> descriptors;

    public DefaultIntrospectionContext(Class<?> cls) {
        this.currentClass = cls;
        this.descriptors = new HashMap();
    }

    public Class<?> getTargetClass() {
        return this.currentClass;
    }

    public void addPropertyDescriptor(PropertyDescriptor desc) {
        if (desc == null) {
            throw new IllegalArgumentException("Property descriptor must not be null!");
        } else {
            this.descriptors.put(desc.getName(), desc);
        }
    }

    public void addPropertyDescriptors(PropertyDescriptor[] descs) {
        if (descs == null) {
            throw new IllegalArgumentException("Array with descriptors must not be null!");
        } else {
            PropertyDescriptor[] var2 = descs;
            int var3 = descs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                PropertyDescriptor desc = var2[var4];
                this.addPropertyDescriptor(desc);
            }

        }
    }

    public boolean hasProperty(String name) {
        return this.descriptors.containsKey(name);
    }

    public PropertyDescriptor getPropertyDescriptor(String name) {
        return (PropertyDescriptor)this.descriptors.get(name);
    }

    public void removePropertyDescriptor(String name) {
        this.descriptors.remove(name);
    }

    public Set<String> propertyNames() {
        return this.descriptors.keySet();
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        return (PropertyDescriptor[])this.descriptors.values().toArray(EMPTY_DESCRIPTORS);
    }
}
