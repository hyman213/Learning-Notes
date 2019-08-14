package io.hyman.spring.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/08/04 21:23
 * @version： 1.0.0
 */
public class RiceCondition implements Condition {
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("people").equals("南方人");
    }
}
