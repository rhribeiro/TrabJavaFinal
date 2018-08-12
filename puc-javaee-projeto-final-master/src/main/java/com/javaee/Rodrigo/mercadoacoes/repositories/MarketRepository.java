package src.main.java.com.javaee.Rodrigo.mercadoacoes.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.allan.mercadoacoes.domain.Market;

@Repository
public interface MarketRepository extends MongoRepository<Market, String>{
}
