package src.main.java.com.javaee.Rodrigo.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRest {
	public StockRest(Stock stock) {
		this.setId(stock.getId());
		this.setName(stock.getName());
		this.setQuantity(stock.getQuantity());
		this.setQuantityCompany(stock.getQuantityCompany());
		this.setInitialPrice(stock.getInitialPrice());
		this.setTimestamp(stock.getTimestamp());
	}

	private String id;
	private String name;
	private int quantity;
	private int quantityCompany;
	private float initialPrice;
	private String timestamp;

}