package com.ishan.mqtt.subscribers;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Subscriber {
	
	MqttClient client;

	public static void main(String[] args) {
		final String brokerUrl = "tcp://127.0.0.1:1883";
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter all Topic names with comma: ");
		String allTopic = scan.nextLine();
		String[] topic_names = allTopic.split(",");
		System.out.println("Enter Unique client id :");
		//String topic = "iot/practice1";
		//String client_id = "1";
		String client_id = scan.nextLine();
		Subscriber subscriber = new Subscriber();
		subscriber.setup(brokerUrl, client_id);
		subscriber.addOptions();
		subscriber.receiveMessage(topic_names);
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
	
	public void receiveMessage(String[] topic)
	{
		client.setCallback(new MqttCallback() {
			
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Message arrived. Topic: " + topic + " Message: " + message.toString() + " with Qos " + message.getQos());
				
				
			}
			
			@Override
			public void deliveryComplete(IMqttDeliveryToken arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void connectionLost(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		try {
			for (int i=0; i< topic.length;i++)
			{
			client.subscribe(topic[i]);
			}
			
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Subscriber is now listening to "+topic);
		
	}

}
