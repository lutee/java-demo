package learn.rabbitmq;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class demo1 {

	public static void main(String[] args) throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp:admin:chenyuxian.cn@124.71.109.143:5672/");
		Connection connection = factory.newConnection("demo");
		Channel channel = connection.createChannel();
		channel.exchangeDeclare("exchange", "direct", true);
		String queueName = channel.queueDeclare().getQueue();
		channel.close();
		connection.close();
	}
}
