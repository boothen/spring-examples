package uk.co.boothen.cloud.stream.client;

import org.springframework.cloud.openfeign.FeignClient;

import uk.co.boothen.cloud.wiremock.client.api.DefaultApi;

@FeignClient(name = "${feign.name}", url = "${feign.url}")
public interface BookClient extends DefaultApi {


}
