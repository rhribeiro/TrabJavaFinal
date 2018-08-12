package src.main.java.com.javaee.Rodrigo.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageId {
	public MessageId(String id) {
		this.setId(id);
	}

	private String id;

}
