package xavi.tech.springfood.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
public class SpringFoodCfg {
	
	@Column @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cfgId;
	@Column
	private int minutesTimeSlot;
	@Column 
	private String sfLocation;
	@Column
	private LocalTime firstDeliveryAvailable;
	@Column
	private LocalTime lastDeliveryAvailable;
	@Column
	private int dashboardHoursSpan;
	@Column
	private LocalDateTime initDate;
	@Transient
	private Set<LocalTime> availableDeliveries;
	
	public SpringFoodCfg() {
		availableDeliveries = new HashSet<LocalTime>();
	}
	
	public void generateDeliveryTimes() {
		LocalTime now = firstDeliveryAvailable;
		
		availableDeliveries.add(now);
		
		while (now.isBefore(lastDeliveryAvailable)) {
			now = now.plusMinutes(minutesTimeSlot);
			availableDeliveries.add(now);
		}
	}

}
