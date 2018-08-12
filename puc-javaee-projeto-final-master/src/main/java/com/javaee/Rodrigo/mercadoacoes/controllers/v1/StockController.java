package src.main.java.com.javaee.Rodrigo.mercadoacoes.controllers.v1;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.allan.mercadoacoes.domain.MessageId;
import com.javaee.allan.mercadoacoes.domain.Stock;
import com.javaee.allan.mercadoacoes.domain.StockDemand;
import com.javaee.allan.mercadoacoes.domain.StockDemandRest;
import com.javaee.allan.mercadoacoes.domain.StockMarket;
import com.javaee.allan.mercadoacoes.domain.StockOffer;
import com.javaee.allan.mercadoacoes.domain.StockOfferRest;
import com.javaee.allan.mercadoacoes.domain.StockRest;
import com.javaee.allan.mercadoacoes.services.CompanyService;
import com.javaee.allan.mercadoacoes.services.StockService;

@RestController
@RequestMapping(StockController.BASE_URL)
public class StockController {

	public static final String BASE_URL = "/api/v1/stocks";

	@Autowired
	private StockService stockService;
	@Autowired
	private CompanyService companyService;

	/* Listar ações - I */
	// GET
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<StockRest> getAll() {
		Set<StockRest> stockRest = new HashSet<>();
		stockService.getAll().forEach((Stock stock) -> {
			stockRest.add(new StockRest(stock));
		});
		return stockRest;
	}

	@GetMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public StockRest getById(@PathVariable String id) {
		return new StockRest(stockService.getById(id));
	}
	/* Listar ações - F */

	/* Emissão de Ações - I */	
	// GET
	@GetMapping({ "/emit/{companyId}" })
	@ResponseStatus(HttpStatus.OK)
	public Set<StockRest> getAllCompany(@PathVariable String companyId) {
		Set<StockRest> stockRest = new HashSet<>();
		companyService.getAllStocks(companyId).forEach((Stock stock) -> {
			stockRest.add(new StockRest(stock));
		});
		return stockRest;
	}

	@GetMapping({ "/emit/{companyId}/{stockId}" })
	@ResponseStatus(HttpStatus.OK)
	public StockRest getById(@PathVariable String companyId, @PathVariable String stockId) {
		return new StockRest (companyService.getStockById(companyId, stockId));
	}

	// POST
	@PostMapping({ "/emit/{companyId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public StockRest createNew(@PathVariable String companyId, @RequestBody Stock stock) {
		return new StockRest(stockService.emitNew(companyId, stock));
	}
	/* Emissão de Ações - F */

	/* Comprar ações - I */
	// GET
	@GetMapping({ "/buy" })
	@ResponseStatus(HttpStatus.OK)
	public Set<StockDemandRest> getDemands() {
		Set<StockDemandRest> demandsRest = new HashSet<>();
		stockService.getAll().forEach((Stock stock) -> {
			stock.getDemands().forEach((StockDemand demand) -> {
				if (demand.getQuantity() - demand.getQuantityBought() > 0) {
					demandsRest.add(new StockDemandRest(demand));
				}
			});
		});
		return demandsRest;
	}

	// POST
	@PostMapping({ "/buy" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId buy(@RequestBody StockMarket stockMarket) {
		return stockService.sendMessage("demand", stockMarket);
	}

	@PostMapping({ "/buy/{stockId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId buyStock(@PathVariable String stockId, @RequestBody StockMarket stockMarket) {
		stockMarket.setStockId(stockId);
		return stockService.sendMessage("demand", stockMarket);
	}

	@PostMapping({ "/buy/{stockId}/{investorId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId buyStockInvestor(@PathVariable String stockId, @PathVariable String investorId, @RequestBody StockMarket stockMarket) {
		stockMarket.setStockId(stockId);
		stockMarket.setInvestorId(investorId);
		return stockService.sendMessage("demand", stockMarket);
	}
	/* Comprar ações - F */

	/* Vender ações - I */
	// GET
	@GetMapping({ "/sell" })
	@ResponseStatus(HttpStatus.OK)
	public Set<StockOfferRest> getOffers() {
		Set<StockOfferRest> offersRest = new HashSet<>();
		stockService.getAll().forEach((Stock stock) -> {
			stock.getOffers().forEach((StockOffer offer) -> {
				if (offer.getQuantity() - offer.getQuantitySold() > 0) {
					offersRest.add(new StockOfferRest(offer));
				}
			});
		});
		return offersRest;
	}

	// POST
	@PostMapping({ "/sell" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId sell(@RequestBody StockMarket stockMarket) {
		return stockService.sendMessage("offer", stockMarket);
	}

	@PostMapping({ "/sell/{stockId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId sellStock(@PathVariable String stockId, @RequestBody StockMarket stockMarket) {
		stockMarket.setStockId(stockId);
		return stockService.sendMessage("offer", stockMarket);
	}

	@PostMapping({ "/sell/{stockId}/{investorId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId sellStockInvestor(@PathVariable String stockId, @PathVariable String investorId, @RequestBody StockMarket stockMarket) {
		stockMarket.setStockId(stockId);
		stockMarket.setInvestorId(investorId);
		return stockService.sendMessage("offer", stockMarket);
	}
	/* Vender ações - F */

	/*
	// PUT
	@PutMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public Stock update(@PathVariable String id, @RequestBody Stock stock) {
		return stockService.save(id, stock);
	}

	// DELETE
	@DeleteMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id) {
		stockService.deleteById(id);
	}
	*/
}
