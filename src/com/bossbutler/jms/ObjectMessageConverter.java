package com.bossbutler.jms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class ObjectMessageConverter implements MessageConverter {

	private static Logger logger = Logger.getLogger(ObjectMessageConverter.class);

	/**
	 * 从消息中取出对象
	 * 
	 * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(
	 *      javax.jms.Message)
	 */
	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {

		Object object = null;

		if (message instanceof ObjectMessage) {
			// 两次强转，获得消息中的主体对象字节数组流
			byte[] obj = (byte[]) ((ObjectMessage) message).getObject();
			// 读取字节数组中为字节数组流
			ByteArrayInputStream bis = new ByteArrayInputStream(obj);

			try {
				// 读字节数组流为对象输出流
				ObjectInputStream ois = new ObjectInputStream(bis);
				// 从对象输出流中取出对象 并强转
				object = ois.readObject();
			} catch (Exception e) {
				logger.error("从消息中取出对象出现错误", e);
			}
		}

		return object;
	}

	/**
	 * 将对象转换成消息
	 * 
	 * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java
	 *      .lang.Object, javax.jms.Session)
	 */
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {

		ObjectMessage objectMessage = session.createObjectMessage();
		// 字节数组输出流
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			// 对象输出流
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			// 写入对象
			oos.writeObject(object);
			// 字节数组输出流转成字节数组
			byte[] objMessage = bos.toByteArray();
			// 将字节数组填充到消息中作为消息主体
			objectMessage.setObject(objMessage);

		} catch (IOException e) {
			logger.error("将对象转换成消息出现错误", e);
		}

		return objectMessage;
	}

}
