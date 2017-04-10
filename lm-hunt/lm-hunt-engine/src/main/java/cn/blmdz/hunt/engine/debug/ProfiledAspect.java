package cn.blmdz.hunt.engine.debug;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;

@Aspect
public class ProfiledAspect {
	private static final Logger log = LoggerFactory.getLogger(ProfiledAspect.class);

	@Around("@annotation(cn.blmdz.hunt.engine.debug.PampasProfiled) && @annotation(profiled)")
	public Object doDebugInfoLog(ProceedingJoinPoint pjp, PampasProfiled profiled) throws Throwable {
		Throwable maybeThrowable = null;
		Object result = null;
		Stopwatch watch = Stopwatch.createStarted();

		try {
			result = pjp.proceed();
		} catch (Throwable var8) {
			maybeThrowable = var8;
		}

		watch.stop();
		if (log.isDebugEnabled()) {
			profiled = AnnotationUtils.getAnnotation(profiled, PampasProfiled.class);
			if (profiled.onlyWhenException() && maybeThrowable == null) {
				return result;
			}

			String functionKey = MoreObjects.firstNonNull(Strings.emptyToNull(profiled.key()),
					pjp.getSignature().toString());
			StringBuilder sb = new StringBuilder();
			sb.append("PampasProfiled[").append(functionKey).append("]").append(" time[").append(watch).append("]");
			if (profiled.withParams()) {
				sb.append(" params").append(Arrays.toString(pjp.getArgs()));
			}

			if (maybeThrowable != null) {
				sb.append(" throw[").append(maybeThrowable).append("]");
			} else if (profiled.withOutput()) {
				sb.append(" return[").append(result).append("]");
			}

			log.debug(sb.toString());
		}

		if (maybeThrowable != null) {
			throw maybeThrowable;
		} else {
			return result;
		}
	}
}
