package src.main.java.com.javaee.Rodrigo.mercadoacoes.controllers.v1;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.allan.mercadoacoes.domain.Company;
import com.javaee.allan.mercadoacoes.domain.CompanyRest;
import com.javaee.allan.mercadoacoes.services.CompanyService;

@RestController
@RequestMapping(CompanyController.BASE_URL)
public class CompanyController {

	public static final String BASE_URL = "/api/v1/companies";

	@Autowired
	private CompanyService companyService;

	// GET
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<CompanyRest> getAll() {
		Set<CompanyRest> companiesRest = new HashSet<>();
		companyService.getAll().forEach((Company company) -> {
			companiesRest.add(new CompanyRest(company));
		});
		return companiesRest;
	}

	@GetMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public CompanyRest getById(@PathVariable String id) {
		return new CompanyRest(companyService.getById(id));
	}

	/*
	@GetMapping({ "/{id}/stocks" })
	@ResponseStatus(HttpStatus.OK)
	public Set<Stock> getAll(@PathVariable String id) {
		return companyService.getAllStocks(id);
	}

	@GetMapping({ "/{id}/stocks/{stockId}" })
	@ResponseStatus(HttpStatus.OK)
	public Stock getStock(@PathVariable String id, @PathVariable String stockId) {
		return companyService.getStockById(id, stockId);
	}
	*/
	
	// POST
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CompanyRest createNew(@RequestBody Company company) {
		return new CompanyRest(companyService.createNew(company));
	}

	/*
	@PostMapping({ "/{id}/stocks" })
	@ResponseStatus(HttpStatus.OK)
	public Company createNewStock(@PathVariable String id, @RequestBody Stock stock) {
		return companyService.addStock(id, stock);
	}
	*/

	// PUT
	@PutMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public CompanyRest update(@PathVariable String id, @RequestBody Company company) {
		Company comp = companyService.getById(id);
		comp.setName(company.getName());
		comp.setEmail(company.getEmail());
		return new CompanyRest(companyService.save(id, comp));
	}

	// DELETE
	@DeleteMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id) {
		companyService.deleteById(id);
	}
}
