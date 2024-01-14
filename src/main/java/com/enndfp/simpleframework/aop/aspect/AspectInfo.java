package com.enndfp.simpleframework.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Enndfp
 */
@AllArgsConstructor
@Getter
public class AspectInfo {

    private int orderIndex;

    private DefaultAspect aspectObject;

    private PointcutLocator pointcutLocator;
}
