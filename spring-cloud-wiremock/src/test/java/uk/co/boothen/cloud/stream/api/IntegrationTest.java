package uk.co.boothen.cloud.stream.api;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import uk.co.boothen.cloud.wiremock.server.model.Book;

import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("wiremock")
class IntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    void test() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        stubFor(get(urlPathEqualTo("/book"))
                    .willReturn(ResponseDefinitionBuilder.okForJson(Collections.singletonList(new Book().id(1L).author("tom").title("tit")))));

        ResponseEntity<List<Book>> responseEntity = testRestTemplate.exchange("/book", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Book>>() {
        });

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<LoggedRequest> loggedRequests = WireMock.findAll(getRequestedFor(urlPathEqualTo("/book")));

        assertThat(loggedRequests).hasSize(1);
    }
}
