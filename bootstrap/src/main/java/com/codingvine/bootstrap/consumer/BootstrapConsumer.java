/**
 * 
 */
package com.codingvine.bootstrap.consumer;

import java.util.Objects;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.codingvine.core.kafka.consumer.BaseConsumer;

import lombok.extern.log4j.Log4j2;

/**
 *
 **/
@Log4j2
@Service
@Lazy(true)
public class BootstrapConsumer<T> extends BaseConsumer<T> {

	@KafkaListener(topics = { "${kafka.topic.bootstrap}" }, idIsGroup = false, id = "bootstrap-consumer", autoStartup = "${kafka.consumer.autostart}")
	public void receive(ConsumerRecords<String, String> records) {

		if (records != null && !records.isEmpty()) {

			log.debug("BootstrapConsumer:: Received " + records.count() + " record(s) to process");

			try {

				for (ConsumerRecord<String, String> record : records) {

					T data = getDto(record);

					if (Objects.nonNull(data)) {

						// TODO Your Actual business logic start here

					} else {
						log.warn("BootstrapConsumer:: DTO is null after parsing Kafka record");
					}
				}
			} catch (Exception e) {
				log.error("BootstrapConsumer:: Error while Processing Kafka Dto : ", e);
			}

		}

	}
}