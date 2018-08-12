package src.main.java.com.javaee.Rodrigo.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockMarket {
	
	//@Id
	//private String id = UUID.randomUUID().toString();
	//private String source;
	private boolean companyOffer = false;
	private String investorId;
	private String stockId;
	private int quantity;
	private float price;
	
}
