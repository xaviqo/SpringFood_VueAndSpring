package xavi.tech.springfood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="dashboard")
public class DashboardCard {
	
	@Id @Column(length=20)
	private String cardId;
	@Column(nullable = false)
	private boolean active;

}
