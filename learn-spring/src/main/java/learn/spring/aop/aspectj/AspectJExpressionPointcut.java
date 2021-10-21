package learn.spring.aop.aspectj;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import learn.spring.aop.ClassFilter;
import learn.spring.aop.MethodMatcher;
import learn.spring.aop.Pointcut;

public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher{

	private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();
	
	static {
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
	}
	
	private final PointcutExpression pointcutExpression;
	
	public AspectJExpressionPointcut(String expression) {
		PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
		pointcutExpression = pointcutParser.parsePointcutExpression(expression);
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
	}

	@Override
	public boolean matcher(Class<?> clazz) {
		return pointcutExpression.couldMatchJoinPointsInType(clazz);
	}

	@Override
	public ClassFilter getClassFilter() {
		return this;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return this;
	}

}
