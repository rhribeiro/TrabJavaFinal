package src.main.java.com.javaee.Rodrigo.mercadoacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.allan.mercadoacoes.domain.Investor;

@Repository
public interface InvestorRepository extends  MongoRepository<Investor, String>{
	List<Investor> findByName(String name);
	List<Investor> findByEmail(String email);
}
