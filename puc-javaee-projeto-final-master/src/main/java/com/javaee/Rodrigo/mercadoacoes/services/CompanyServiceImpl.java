package src.main.java.com.javaee.Rodrigo.mercadoacoes.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.allan.mercadoacoes.domain.Company;
import com.javaee.allan.mercadoacoes.domain.Stock;
import com.javaee.allan.mercadoacoes.repositories.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private StockService stockService;
	//@Autowired
	//private StockRepository stockRepository;

	@Override
	public Set<Company> getAll() {
		Set<Company> companies = new HashSet<>();
		companyRepository.findAll().iterator().forEachRemaining(companies::add);
		return companies;
	}

	@Override
	public Company getById(String id) {
		return getCompanyById(id);
	}

	private Company getCompanyById(String id) {
		Optional<Company> companyOptional = companyRepository.findById(id);

		if (!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Company not found for ID value: " + id.toString());
		}

		return companyOptional.get();
	}

	@Override
	public Company createNew(Company company) {
		Company companyInd0;
		try {
			companyInd0 = companyRepository.findByEmail(company.getEmail()).get(0);
		} catch (Exception e) {
			return companyRepository.save(company);			
		}
		throw new IllegalArgumentException("Company already exists with ID: " + companyInd0.getId());
	}

	@Override
	public Company save(String id, Company company) {
		company.setId(id);
		return companyRepository.save(company);
	}

	@Override
	public void deleteById(String id) {
		companyRepository.deleteById(id);
	}

	@Override
	public Company addStock(String companyId, Stock stock) {
		Company company = getCompanyById(companyId);
		Set<Stock> stocks = company.getStocks();
		stocks.remove(null);
		stocks.add(stockService.createNew(stock));
		company.setStocks(stocks);
		return save(companyId, company);
	}

	@Override
	public Set<Stock> getAllStocks(String companyId) {
		return getCompanyById(companyId).getStocks();
	}

	@Override
	public Stock getStockById(String companyId, String stockId) {
		for (Stock stock : getCompanyById(companyId).getStocks()) {
			if (stock.getId().equals(stockId)) {
				return stock;
			}
		}
		throw new IllegalArgumentException("Stock not found for ID value: " + stockId.toString() + " for Company " + companyId.toString());
	}

}
