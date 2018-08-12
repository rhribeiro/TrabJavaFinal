package src.main.java.com.javaee.Rodrigo.mercadoacoes.services;

import com.javaee.allan.mercadoacoes.domain.StockMarket;
import com.javaee.allan.mercadoacoes.domain.StockOffer;

public interface OfferService {

	StockOffer createNew(StockMarket stockMarket);
	
	StockOffer save(StockOffer offer);
	
}
