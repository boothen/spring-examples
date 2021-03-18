package uk.co.boothen.cloud.stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {Application.class, QueueConfiguration.class, TestConfiguration.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DockerComposeIntegrationTest {

    @Container
    static DockerComposeContainer<?> environment = new DockerComposeContainer<>(new File("src/test/resources/docker-compose.yaml"))
        .withExposedService("db_1", 5432)
        .withExposedService("rabbitmq_1", 5672);


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BlockingQueue<Payload> integerList;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", () -> environment.getServiceHost("rabbitmq_1", 5672));
        registry.add("spring.rabbitmq.port", () -> environment.getServicePort("rabbitmq_1", 5672));
    }

    @Autowired
    private ReceivingSource receivingSource;

    @Autowired
    private QueueMessageHandler queueMessageHandler;

    @Test
    void test() throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/post-message", HttpMethod.POST, httpEntity, Void.class);

        testRestTemplate.exchange("/post-message", HttpMethod.POST, httpEntity, Void.class);

        testRestTemplate.exchange("/post-message", HttpMethod.POST, httpEntity, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Payload payload = integerList.poll(10, TimeUnit.SECONDS);

        assertThat(payload).isNull();

        receivingSource.pollableMessageSource().poll(queueMessageHandler, new ParameterizedTypeReference<Payload>() {});

        payload = integerList.poll(10, TimeUnit.SECONDS);

        assertThat(payload).isEqualTo(new Payload(10));
    }
}