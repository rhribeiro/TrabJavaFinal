package src.main.java.com.javaee.Rodrigo.mercadoacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.allan.mercadoacoes.domain.Stock;

@Repository
public interface StockRepository extends MongoRepository<Stock, String>{
	List<Stock> findByName(String name);
}
