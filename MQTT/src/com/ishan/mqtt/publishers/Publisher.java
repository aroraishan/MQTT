package com.ishan.mqtt.publishers;

import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class Publisher {
		
		
		MqttClient client;
		
		
	public static void main(String[] args) {
		
		final String brokerUrl = "tcp://127.0.0.1:1883";
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Topic name: ");
		String topic = scan.nextLine();
		System.out.println("Enter Unique client id :");
		//String topic = "iot/practice1";
		//String client_id = "1";
		String client_id = scan.nextLine();
		Publisher publisher = new Publisher();
		publisher.setup(brokerUrl, client_id);
		publisher.addOptions();
		publisher.sendMessage(topic);
		
	}
	
	public void setup(String url, String client_id)
	{
		try {
			
			client = new MqttClient(url,client_id);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addOptions()
	{
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		try {
			client.connect(options);
		} catch (MqttException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void sendMessage(String topic)
	{
		Scanner scan = new Scanner(System.in);
		String message = "";
		int qos;
		while(true)
		{
			MqttTopic getTopic = client.getTopic(topic);
			System.out.println("Enter message:");
			message = scan.next();
			System.out.println("Enter QoS");
			qos = scan.nextInt();
			try {
				MqttMessage ourMessage = new MqttMessage(message.getBytes());
				ourMessage.setQos(qos);
				getTopic.publish(ourMessage);
				System.out.println("Message Published " + message + " with QoS " + qos);
				Thread.sleep(1000);
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
