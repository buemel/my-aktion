package de.dpunkt.myaktion.scheduler;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import de.dpunkt.myaktion.services.SpendeService;

@Singleton
public class SchedulerBean {
	@Inject
	private SpendeService spendeService;

	@Schedule(hour = "*", minute = "*/5", persistent = false)
	/**
	 * * Ruft alle 5 Minuten die Methode transferSpende des SpendeService auf
	 */
	public void doTransferSpende() {
		spendeService.transferSpende();
	}
}
