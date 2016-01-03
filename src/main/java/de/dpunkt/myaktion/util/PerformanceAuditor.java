package de.dpunkt.myaktion.util;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import de.dpunkt.myaktion.util.LogTypes.TecLog;

public class PerformanceAuditor {
	
	@Inject @TecLog
	private Logger logger;
	
	@AroundInvoke
	public Object durationOfMethod(InvocationContext ctx) throws Exception {
		long time = System.currentTimeMillis();
		Object ret = ctx.proceed();
		long duration = System.currentTimeMillis() - time;
		logger.info(String.format("%s hat %d Milli-Sekunden benötigt", ctx.getMethod().getName(), TimeUnit.MILLISECONDS.toSeconds(duration)));
		
		return ret;
	}

}
