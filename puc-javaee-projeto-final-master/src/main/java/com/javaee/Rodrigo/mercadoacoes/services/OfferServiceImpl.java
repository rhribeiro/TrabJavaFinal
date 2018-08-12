package src.main.java.com.javaee.Rodrigo.mercadoacoes.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.allan.mercadoacoes.domain.Investor;
import com.javaee.allan.mercadoacoes.domain.Market;
import com.javaee.allan.mercadoacoes.domain.Stock;
import com.javaee.allan.mercadoacoes.domain.StockDemand;
import com.javaee.allan.mercadoacoes.domain.StockMarket;
import com.javaee.allan.mercadoacoes.domain.StockOffer;
import com.javaee.allan.mercadoacoes.emailsender.EmailSender;
import com.javaee.allan.mercadoacoes.repositories.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService {
	
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	InvestorService investorService;
	@Autowired
	DemandService demandService;
	@Autowired
	StockService stockService;
	@Autowired
	MarketService marketService;

	@Override
	public StockOffer createNew(StockMarket stockMarket) {
		EmailSender emailSender = new EmailSender();
		Investor investor = null;
		if (!stockMarket.isCompanyOffer()) {
			investor = investorService.getById(stockMarket.getInvestorId());
		}
		Stock stock = stockService.getById(stockMarket.getStockId());

		StockOffer stockOffer = new StockOffer();
		stockOffer.setQuantity(stockMarket.getQuantity());
		stockOffer.setQuantitySold(0);
		stockOffer.setPrice(stockMarket.getPrice());
		if (stockMarket.isCompanyOffer()) {
			stockOffer.setCompanyOffer(true);
		} else {
			stockOffer.setInvestor(investor);
		}
		stockOffer.setStock(stock);
		
		// Verifica se possui alguma oferta no mesmo valor
		Set<StockDemand> stockDemands = stock.getDemands();
		stockDemands.stream().forEach((stockDemand) -> {
			int quant = 0;
			if (stockDemand.getPrice() == stockOffer.getPrice() && stockDemand.getQuantity() - stockDemand.getQuantityBought() > 0) {
				if (stockOffer.getQuantity() - stockOffer.getQuantitySold() > stockDemand.getQuantity() - stockDemand.getQuantityBought()) {
					quant = stockDemand.getQuantity() - stockDemand.getQuantityBought();
				} else {
					quant = stockOffer.getQuantity() - stockOffer.getQuantitySold();
				}

				// Remove quantidade em ambos as ofertas
				if (quant > 0) {
					stockDemand.setQuantityBought(stockDemand.getQuantityBought() + quant);
					stockOffer.setQuantitySold(stockOffer.getQuantitySold() + quant);
					demandService.save(stockDemand);
					
					Market market = new Market();
					market.setQuantity(quant);
					market.setPrice(stockDemand.getPrice());
					market.setOffer(stockOffer);
					market.setDemand(stockDemand);
					marketService.save(market);
					
					if (stockOffer.isCompanyOffer()) {
						stock.setQuantityCompany(stock.getQuantityCompany() - quant);
					}

					if (stockOffer.isCompanyOffer()) {
						emailSender.SendEmail(stockOffer.getStock().getCompany().getEmail(), 
								"Notificação de venda ação " + stockOffer.getStock().getId(), 
								Integer.toString(quant) + " ações foram vendidas com sucesso no valor de " + stockDemand.getPrice() + " (preço unitário)."
						);
					} else {
						emailSender.SendEmail(stockOffer.getInvestor().getEmail(), 
								"Notificação de venda ação " + stockOffer.getStock().getId(), 
								Integer.toString(quant) + " ações foram vendidas com sucesso no valor de " + stockDemand.getPrice() + " (preço unitário)."
						);
					}
					emailSender.SendEmail(stockDemand.getInvestor().getEmail(), 
							"Notificação de compra ação " + stockOffer.getStock().getId(), 
							Integer.toString(quant) + " ações foram compradas com sucesso no valor de " + stockDemand.getPrice() + " (preço unitário)."
					);
				}
			}
		});
		offerRepository.save(stockOffer);
		
		Set<StockOffer> sOffers = stock.getOffers();
		sOffers.add(stockOffer);
		stock.setOffers(sOffers);
		stockService.save(stock.getId(), stock);

		if (!stockMarket.isCompanyOffer()) {
			Set<StockOffer> iDemands = investor.getOffers();
			iDemands.add(stockOffer);
			investor.setOffers(iDemands);
			investorService.save(investor.getId(), investor);
		}

		return stockOffer;
	}

	@Override
	public StockOffer save(StockOffer offer) {
		offer.setId(offer.getId());
		return offerRepository.save(offer);
	}

}
