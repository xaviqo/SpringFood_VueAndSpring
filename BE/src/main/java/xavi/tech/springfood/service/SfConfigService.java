package xavi.tech.springfood.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.model.SpringFoodCfg;
import xavi.tech.springfood.repository.SfCfgRepository;

@AllArgsConstructor
@Service
public class SfConfigService {

	private final SfCfgRepository cfgRepository;

	public ResponseEntity<?> setMinuteTimeSlot(int timeSlot){

		Optional<SpringFoodCfg> cfg = cfgRepository.findById(1);

		if (cfg.isPresent() &&  validMinute(timeSlot)) {
			cfg.get().setMinutesTimeSlot(timeSlot);
			cfgRepository.save(cfg.get());
			return ResponseEntity.ok(timeSlot);
		}
		throw new SpringFoodException(SpringFoodError.CfgInvalidTimeslot,"Error setting time minutes slot",HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> setOpenDeliveryAvailableTime(int h, int m){

		Optional<SpringFoodCfg> cfg = cfgRepository.findById(1);

		try {
			if (cfg.isPresent() ) {
				cfg.get().setFirstDeliveryAvailable(LocalTime.of(h, m));
				cfgRepository.save(cfg.get());
			}
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.CfgInvalidOpenTime,"Error setting opening time",HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(LocalTime.of(h, m));

	}

	public ResponseEntity<?> setCloseDeliveryAvailableTime(int h, int m){

		Optional<SpringFoodCfg> cfg = cfgRepository.findById(1);

		try {
			if (cfg.isPresent() && validHour(h) && validMinute(m)) {
				cfg.get().setLastDeliveryAvailable(null);
				cfgRepository.save(cfg.get());
			}
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.CfgInvalidOpenTime,"Error setting closing time",HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(LocalTime.of(h, m));
	}

	public ResponseEntity<?> getAllDeliveryTimes(){

		Optional<SpringFoodCfg> cfg = cfgRepository.findById(1);

		if (cfg.isPresent()) {
			cfg.get().generateDeliveryTimes();
			return ResponseEntity.ok(hourAndMinuteOnly(cfg.get().getAvailableDeliveries()));
		} else {
			throw new SpringFoodException(SpringFoodError.InternalError,"Error generating delivery times",HttpStatus.BAD_REQUEST);
		}

	}
	
	public ResponseEntity<?> getDashboardOpenClose(){

		Map<String,Object> dashboardOpenClose = new HashMap<>();
		Optional<SpringFoodCfg> cfg = cfgRepository.findById(1);

		if (cfg.isPresent()) {
			LocalDateTime from = cfg.get().getInitDate();
			LocalDateTime to = from.plusHours(cfg.get().getDashboardHoursSpan());
			dashboardOpenClose.put("dayTo", to);
			dashboardOpenClose.put("dayFrom", from);
			return ResponseEntity.ok(dashboardOpenClose);
		} else {
			throw new SpringFoodException(SpringFoodError.InternalError,"Error getting dashboard open and close times",HttpStatus.BAD_REQUEST);
		}

	}
	
	public ResponseEntity<?> setDashboardOpenClose(Map<String,String> newCfg){

		Optional<SpringFoodCfg> cfg = cfgRepository.findById(1);

		if (Objects.nonNull(newCfg) && cfg.isPresent()) {
						
			LocalDateTime newTime = LocalDateTime.of(
					Integer.parseInt(newCfg.get("year")),
					Integer.parseInt(newCfg.get("month")), 
					Integer.parseInt(newCfg.get("day")),
					Integer.parseInt(newCfg.get("hour")), 
					Integer.parseInt(newCfg.get("minutes"))
					);
			
			cfg.get().setInitDate(newTime);
			cfg.get().setDashboardHoursSpan(Integer.valueOf(newCfg.get("spanOfTime")));;
			cfgRepository.save(cfg.get());
			
			try {

			} catch (Exception e) {
				throw new SpringFoodException(SpringFoodError.InternalError,"Error saving dashboard open and close times",HttpStatus.BAD_REQUEST);
			}			
			return ResponseEntity.ok(newCfg);
		} else {
			throw new SpringFoodException(SpringFoodError.InternalError,"Error saving dashboard open and close times",HttpStatus.BAD_REQUEST);
		}

	}
	
	
	private Set<String> hourAndMinuteOnly(Set<LocalTime> times){
		
		Set<String> deliveryTimes = new TreeSet<String>();
		
		for (LocalTime time : times) {
			deliveryTimes.add(LocalTime.of(time.getHour(), time.getMinute()).toString());
		}
		
		return deliveryTimes;
	}

	private boolean validHour(int h) {
		return (h < 24 && h >0);
	}

	private boolean validMinute(int m) {
		return (m < 59 && m >0);
	}
}
