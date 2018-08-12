package src.main.java.com.javaee.Rodrigo.mercadoacoes.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaee.allan.mercadoacoes.config.RabbitMQConfig;
import com.javaee.allan.mercadoacoes.domain.Message;
import com.javaee.allan.mercadoacoes.services.DemandService;
import com.javaee.allan.mercadoacoes.services.OfferService;

@Component
public class MessageListener {

	static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

	@Autowired
	private DemandService demandService;
	@Autowired
	private OfferService offerService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_MESSAGES)
    public void processMessage(Message message) {
    		switch (message.getSource()) {
			case "demand":
				demandService.createNew(message.getData());
				break;

			case "offer":
				offerService.createNew(message.getData());
				break;

			default:
				logger.info("Invalid Source...");
				break;
		}
    }
	
}
