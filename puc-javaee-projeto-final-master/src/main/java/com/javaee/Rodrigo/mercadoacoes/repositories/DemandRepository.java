package src.main.java.com.javaee.Rodrigo.mercadoacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.allan.mercadoacoes.domain.Stock;
import com.javaee.allan.mercadoacoes.domain.StockDemand;

@Repository
public interface DemandRepository extends MongoRepository<StockDemand, String>{
	List<DemandRepository> findByStock(Stock stock);
}
