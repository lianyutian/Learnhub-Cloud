package com.learnhub.common.utils;

import com.alibaba.fastjson.annotation.JSONField;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/7 10:52
 */
public class DynamicClassGenerator {
    public static Class<?> generateClass(String className, String[] fieldNames, Class<?>[] fieldTypes) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass dynamicClass = pool.makeClass(className);

            // Add default constructor
            CtConstructor constructor = new CtConstructor(new CtClass[]{}, dynamicClass);
            constructor.setBody("{}");
            dynamicClass.addConstructor(constructor);

            for (int i = 0; i < fieldNames.length; i++) {
                CtField field = new CtField(pool.get(fieldTypes[i].getName()), fieldNames[i], dynamicClass);
                field.setModifiers(Modifier.PRIVATE);
                dynamicClass.addField(field);

                // Add JSONField annotation
                addJsonFieldAnnotation(dynamicClass, fieldNames[i], fieldNames[i], null); // Using field name as JSON field name
            }

            ClassLoader classLoader = DynamicClassGenerator.class.getClassLoader();
            return dynamicClass.toClass(classLoader, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addJsonFieldAnnotation(CtClass dynamicClass, String fieldName, String name, Class<?> deserializeUsing) throws Exception {
        ConstPool constPool = dynamicClass.getClassFile().getConstPool();

        // Add JSONField annotation
        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation jsonFieldAnnotation = new Annotation(JSONField.class.getName(), constPool);
        jsonFieldAnnotation.addMemberValue("name", new StringMemberValue(name, constPool));
        if (deserializeUsing != null) {
            jsonFieldAnnotation.addMemberValue("deserializeUsing", new ClassMemberValue(deserializeUsing.getName(), constPool));
        }
        attr.addAnnotation(jsonFieldAnnotation);

        CtField ctField = dynamicClass.getDeclaredField(fieldName);
        ctField.getFieldInfo().addAttribute(attr);
    }

    public static void main(String[] args) {
        String className = "MyDynamicClass";
        String[] fieldNames = {"field1", "field2", "field3"};
        Class<?>[] fieldTypes = {String.class, String.class, String.class};

        Class<?> dynamicClass = generateClass(className, fieldNames, fieldTypes);
        if (dynamicClass != null) {
            try {
                // Parse JSON
                String jsonString = "{\"field1\":\"1\",\"field2\":\"2\",\"field3\":\"3\"}";
                Object parsedObject = com.alibaba.fastjson.JSON.parseObject(jsonString, dynamicClass);
                System.out.println(parsedObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
