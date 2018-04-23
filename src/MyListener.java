import javax.jms.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

public class MyListener implements MessageListener {

	Gson gson = new Gson();
	


	public void onMessage(Message m) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		try {
			TextMessage msg = (TextMessage) m;
			String message = msg.getText();	
			Car receivedCar = gson.fromJson(message, Car.class);
			System.out.println("Car to string!:" + receivedCar.toString());
			session.save(receivedCar);
			session.getTransaction().commit();
			session.close();
		} catch (JMSException e) {
			System.out.println(e);
			session.clear();
			session.getTransaction().commit();
			session.close();

		}
	}
}