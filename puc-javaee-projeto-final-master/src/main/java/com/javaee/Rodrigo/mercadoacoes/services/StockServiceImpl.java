package src.main.java.com.javaee.Rodrigo.mercadoacoes.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.allan.mercadoacoes.domain.Company;
import com.javaee.allan.mercadoacoes.domain.Message;
import com.javaee.allan.mercadoacoes.domain.MessageId;
import com.javaee.allan.mercadoacoes.domain.Stock;
import com.javaee.allan.mercadoacoes.domain.StockDemand;
import com.javaee.allan.mercadoacoes.domain.StockMarket;
import com.javaee.allan.mercadoacoes.domain.StockOffer;
import com.javaee.allan.mercadoacoes.repositories.StockRepository;
import com.javaee.allan.mercadoacoes.config.RabbitMQConfig;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private DemandService demandService;
	@Autowired
	private OfferService offerService;
	
	private final RabbitTemplate rabbitTemplate;

	public StockServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
	}
	
	@Override
	public MessageId sendMessage(String source, StockMarket stockMarket) {
		Message message = messageService.createNew(new Message(source, stockMarket));
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_MESSAGES, message);
		return new MessageId(message.getId());
	}

	@Override
	public Set<Stock> getAll() {
		Set<Stock> stocks = new HashSet<>();
		stockRepository.findAll().iterator().forEachRemaining(stocks::add);
		return stocks;
	}

	@Override
	public Stock getById(String id) {
		return getStockById(id);
	}

	private Stock getStockById(String id) {
		Optional<Stock> stockOptional = stockRepository.findById(id);

		if (!stockOptional.isPresent()) {
			throw new IllegalArgumentException("Stock not found for ID value: " + id.toString());
		}

		return stockOptional.get();
	}
	
	@Override
	public Stock createNew(Stock stock) {
		Stock stockInd0;
		try {
			stockInd0 = stockRepository.findByName(stock.getName()).get(0);
		} catch (Exception e) {
			return stockRepository.save(stock);
		}
		throw new IllegalArgumentException("stock already exists with ID: " + stockInd0.getId());
	}

	@Override
	public Stock emitNew(String companyId, Stock stock) {
		Company company = companyService.getById(companyId);
		stock.setCompany(company); //stock.setCompanyId(company.getId());
		stock.setQuantityCompany(stock.getQuantity());
		Set<Stock> stocks = company.getStocks();
		stocks.add(stock);
		company.setStocks(stocks);
		this.createNew(stock);
		companyService.save(companyId, company);

		StockMarket stockMarket = new StockMarket();
		stockMarket.setStockId(stock.getId());
		stockMarket.setQuantity(stock.getQuantity());
		stockMarket.setPrice(stock.getInitialPrice());
		stockMarket.setCompanyOffer(true);
		offerService.createNew(stockMarket);

		return stock;
	}

	@Override
	public Stock save(String id, Stock stock) {
		stock.setId(id);
		return stockRepository.save(stock);
	}

	@Override
	public void deleteById(String id) {
		stockRepository.deleteById(id);
	}

	@Override
	public Set<StockDemand> getAllDemands() {
		return demandService.getAll();
		
	    //Comparator<Person> comparator = Comparator.comparing(person -> person.name);
	    //comparator = comparator.thenComparing(Comparator.comparing(person -> person.age));
		//return null;
	}

	@Override
	public Set<StockOffer> getAllOffers() {
		
		return null;
	}

}
