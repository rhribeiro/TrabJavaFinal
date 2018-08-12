package src.main.java.com.javaee.Rodrigo.mercadoacoes.services;

import java.util.Set;

import com.javaee.allan.mercadoacoes.domain.Stock;
import com.javaee.allan.mercadoacoes.domain.StockDemand;
import com.javaee.allan.mercadoacoes.domain.StockMarket;

public interface DemandService {

	StockDemand createNew(StockMarket stockMarket);
	
	Set<StockDemand> getAll();

	Set<StockDemand> getAllByStock(Stock stock);

	StockDemand save(StockDemand demand);

}
