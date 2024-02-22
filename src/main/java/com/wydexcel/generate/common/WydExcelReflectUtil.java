package com.wydexcel.generate.common;

import com.wydexcel.generate.process.context.ColumnFunction;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.process.context.SetAccessibleAction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Locale;


public class WydExcelReflectUtil {
    public static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            if (!name.startsWith("get") && !name.startsWith("set")) {
                return "";
            }
            name = name.substring(3);
        }
        if (name.length() == 1 || name.length() > 1 && !Character.isUpperCase(name.charAt(1))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }
        return name;
    }


    public static String getFieldNameByLambda(ColumnFunction r) {
        try {
            Method method = r.getClass().getDeclaredMethod("writeReplace");
            Method o = (Method) AccessController.doPrivileged(new SetAccessibleAction(method));
            Object invoke = o.invoke(r);//
            SerializedLambda serializedLambda = (SerializedLambda) invoke;
            String instantiatedMethodType = serializedLambda.getInstantiatedMethodType();
            String instantiatedType = instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(";")).replace("/", ".");
            ExcelContextAllContext.getInstance().putClassMap(instantiatedType);
            return methodToProperty(serializedLambda.getImplMethodName());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }
}
