package src.main.java.com.javaee.Rodrigo.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDemandRest { // Consumer
	
	public StockDemandRest(StockDemand stockDemand) {
		this.setId(stockDemand.getId());
		this.setQuantity(stockDemand.getQuantity());
		this.setQuantityBought(stockDemand.getQuantityBought());
		this.setPrice(stockDemand.getPrice());
		this.setTimestamp(stockDemand.getTimestamp());
		this.setInvestor(new InvestorRest(stockDemand.getInvestor()));
		this.setStock(new StockRest(stockDemand.getStock()));
	}
	public StockDemandRest() {
	}
	
	private String id;
	private int quantity;
	private int quantityBought;
	private float price;
	private String timestamp;

	private InvestorRest investor;
	private StockRest stock;
	//private Set<Market> markets = new HashSet<>();;

}
