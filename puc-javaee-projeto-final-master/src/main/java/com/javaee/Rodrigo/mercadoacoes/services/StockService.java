package src.main.java.com.javaee.Rodrigo.mercadoacoes.services;

import java.util.Set;

import com.javaee.allan.mercadoacoes.domain.MessageId;
import com.javaee.allan.mercadoacoes.domain.Stock;
import com.javaee.allan.mercadoacoes.domain.StockDemand;
import com.javaee.allan.mercadoacoes.domain.StockMarket;
import com.javaee.allan.mercadoacoes.domain.StockOffer;

public interface StockService {
	Set<Stock> getAll();

	Stock getById(String id);

	Stock createNew(Stock stock);

	Stock emitNew(String companyId, Stock stock);

	Stock save(String id, Stock stock);
		
	void deleteById(String id);
	
	Set<StockDemand> getAllDemands();

	Set<StockOffer> getAllOffers();

	MessageId sendMessage(String source, StockMarket stockMarket);
}
