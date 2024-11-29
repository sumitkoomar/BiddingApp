

package com.ShopLive; // Ensure this matches your structure

import com.ShopLive.config.KafkaProducerConfig;
import com.ShopLive.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ShopLiveApplication.class, KafkaProducerConfig.class})
public class KafkaTemplateTest {

    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    @Test
    void kafkaTemplateBeanShouldBeLoaded() {
        assertNotNull(kafkaTemplate);
    }
}

