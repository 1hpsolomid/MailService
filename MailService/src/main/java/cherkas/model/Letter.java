package cherkas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "letters")
public class Letter {
	
	@Id
	@GeneratedValue
	@Column(name = "letter_id")
	private Integer letterId;
	
	@Column(name = "sender", nullable = false)
	private String sender;
	
	@Column(name = "receiver", nullable = false)
	private String receiver;
	
	@Column(name = "theme", nullable = false)
	private String theme;
	
	@Column(name = "text", length = 300, nullable = false)
	private String text;
	
	public Letter() {
		
	}

	public Letter(String sender, String receiver, String theme, String text) {
		this.sender = sender;
		this.receiver = receiver;
		this.theme = theme;
		this.text = text;
	}
}
