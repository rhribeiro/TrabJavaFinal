package src.main.java.com.javaee.Rodrigo.mercadoacoes.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.allan.mercadoacoes.domain.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>{
}
