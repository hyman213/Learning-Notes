package io.hyman.spring.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/08/04 21:19
 * @version： 1.0.0
 */
public class NoddlesCondition implements Condition {
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("people").equals("北方人");
    }
}
