package factory;

import annotation.Autowired;
import annotation.Component;
import annotation.Repository;
import annotation.Service;
import utils.AnnotationUtils;
import utils.ScanClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * scann package and initialize bean according to some selfDefined annotation
 * put all beans into a container to used by web context
 */
public class AnnotationBeanFactory {
    private static Map<String, Object> beanMap = new HashMap<>();
    private final static String[] packages = {"service","dao","utils","factory"};

    static{
        List<Class<?>> classes = new ArrayList<>();
        for (String packageName : packages) {
            //scan some packeges and get the class information
            List<Class<?>> classList = ScanClassUtils.getClassesByPackageName(packageName);
            //deal @Service,@Repository annotation
            classList.forEach(aClass -> {
                if (AnnotationUtils.isComponentAnnotation(aClass)) {
                    try {
                        String beanName = aClass.getAnnotation(Service.class) != null
                                ? aClass.getAnnotation(Service.class).value()
                                : (aClass.getAnnotation(Repository.class) != null
                                ? aClass.getAnnotation(Repository.class).value()
                                : aClass.getAnnotation(Component.class).value());
                        Object o = aClass.newInstance();
                        beanMap.put(beanName != null ? beanName : aClass.getSimpleName(), o);
                        classes.add(aClass);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //deal with @Autowired Annotation
        classes.forEach(aClass -> {
                if (AnnotationUtils.isComponentAnnotation(aClass)) {
                    String beanName = aClass.getAnnotation(Service.class) != null
                            ? aClass.getAnnotation(Service.class).value()
                            : (aClass.getAnnotation(Repository.class) != null
                            ? aClass.getAnnotation(Repository.class).value()
                            : aClass.getAnnotation(Component.class).value());
                    Object bean = beanMap.get(beanName != null ? beanName : aClass.getSimpleName());
                    Field[] declaredFields = aClass.getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        String name = declaredField.getName();
                        Object propertyBean = beanMap.get(name);
                        if (declaredField.getAnnotation(Autowired.class) != null) {
                            Method[] declaredMethods = aClass.getDeclaredMethods();
                            for (Method method : declaredMethods) {
                                if (method.getName().equalsIgnoreCase("set" + name)) {
                                    try {
                                        method.invoke(bean, propertyBean);
                                        beanMap.put(beanName, bean);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
        });


        //deal with transaction annotation
        ProxyFactory proxyFactory = (ProxyFactory)beanMap.get("proxyFactory");
        classes.forEach(aClass -> {
            if (AnnotationUtils.isTansactionalAnnotaion(aClass)) {
                String beanName = aClass.getAnnotation(Service.class) != null
                        ? aClass.getAnnotation(Service.class).value()
                        : (aClass.getAnnotation(Repository.class) != null
                        ? aClass.getAnnotation(Repository.class).value()
                        : aClass.getAnnotation(Component.class).value());
                Object bean = beanMap.get(beanName != null ? beanName : aClass.getSimpleName());
                if(bean != null){
                    Object beanProxy = proxyFactory.getCglibProxy(bean);
                    beanMap.put(beanName, beanProxy);
                }
            }
        });

    }

    public static  Object getBean(String id) {
        return beanMap.get(id);
    }

}
