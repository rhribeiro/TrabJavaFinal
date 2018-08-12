package src.main.java.com.javaee.Rodrigo.mercadoacoes.services;

import java.util.Set;

import com.javaee.allan.mercadoacoes.domain.Investor;

public interface InvestorService {
	Set<Investor> getAll();

	Investor getById(String id);

	Investor createNew(Investor buyer);

	Investor save(String id, Investor buyer);

	void deleteById(String id);
}
