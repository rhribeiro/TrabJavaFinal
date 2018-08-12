package src.main.java.com.javaee.Rodrigo.mercadoacoes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.allan.mercadoacoes.domain.Market;
import com.javaee.allan.mercadoacoes.repositories.MarketRepository;

@Service
public class MarketServiceImpl implements MarketService {
	
	@Autowired
	MarketRepository marketRepository;

	@Override
	public Market save(Market market) {
		market.setId(market.getId());
		return marketRepository.save(market);
	}

}
