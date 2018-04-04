package cherkas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@Column(name="email", length=100)
	private String email;
	
	@Column(name="password", length=100, nullable=false)
	private String password;
	
	public Account() {
		
	}

	public Account(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
