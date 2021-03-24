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
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {Application.class, QueueConfiguration.class, TestConfiguration.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class GenericDockerContainerIntegrationTest {

    @Container
    static GenericContainer<?> rabbitMqContainer = new GenericContainer<>(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"))
        .withExposedPorts(5672)
        .waitingFor(Wait.forLogMessage(".*Server startup complete.*", 1)
                        .withStartupTimeout(Duration.ofSeconds(60)));


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BlockingQueue<Payload> integerList;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", () -> rabbitMqContainer.getHost());
        registry.add("spring.rabbitmq.port", () -> rabbitMqContainer.getMappedPort(5672));
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