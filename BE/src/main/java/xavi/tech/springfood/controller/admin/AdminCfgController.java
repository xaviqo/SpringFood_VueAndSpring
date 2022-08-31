package xavi.tech.springfood.controller.admin;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.service.SfConfigService;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(path="/api/admin/cfg")
public class AdminCfgController {
	
	private final SfConfigService cfgService;
	
	@GetMapping(path = "/getDbOpenClose")
	public ResponseEntity<?> getDashboardOpenClose() {
		return cfgService.getDashboardOpenClose();
	}

	@PostMapping(path = "/setDbOpenClose")
	public ResponseEntity<?> setDashboardOpenClose(@RequestBody Map<String,String> newCfg) {
		System.out.println("aló");
		return cfgService.setDashboardOpenClose(newCfg);
	}
	
	@PostMapping(path="/setOpenTime")
	public ResponseEntity<?> setOpenDeliveryAvailableTime(@RequestBody Map<String,Integer> hm) {
		return cfgService.setOpenDeliveryAvailableTime(hm.get("h"),hm.get("m"));
	}
	
	@PostMapping(path="/setCloseTime")
	public ResponseEntity<?> setCloseTime(@RequestBody Map<String,Integer> hm) {
		return cfgService.setCloseDeliveryAvailableTime(hm.get("h"),hm.get("m"));
	}
}
