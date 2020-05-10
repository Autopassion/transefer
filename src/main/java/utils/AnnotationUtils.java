package utils;

import annotation.Component;
import annotation.Repository;
import annotation.Service;
import annotation.Transactional;

import java.lang.annotation.Annotation;

public class AnnotationUtils {

    /**
     * judge the class annoation is a component annotation or not
     * @param clazz
     * @return
     */
    public static Boolean isComponentAnnotation(Class<?> clazz) {
//         if(clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Repository.class) || clazz.isAnnotationPresent(Component.class)){
//             return true;
//         }else{
//             return false;
//         }

         return clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Repository.class) || clazz.isAnnotationPresent(Component.class);
    }

    /**
     * judge the class annoation is a transactional annotation or not
     * @param clazz
     * @return
     */
    public static Boolean isTansactionalAnnotaion(Class<?> clazz){
        return clazz.isAnnotationPresent(Transactional.class);
    }

}
