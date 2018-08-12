package src.main.java.com.javaee.Rodrigo.mercadoacoes.services;

import java.util.Set;

import com.javaee.allan.mercadoacoes.domain.Company;
import com.javaee.allan.mercadoacoes.domain.Stock;

public interface CompanyService {
	Set<Company> getAll();

	Company getById(String id);

	Company createNew(Company company);

	Company save(String id, Company company);

	void deleteById(String id);

	Company addStock(String id, Stock stock);

	Set<Stock> getAllStocks(String companyId);

	Stock getStockById(String companyId, String stockId);
}
