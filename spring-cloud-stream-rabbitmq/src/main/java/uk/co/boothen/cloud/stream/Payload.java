package uk.co.boothen.cloud.stream;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Payload {

    private final Integer value;

    public Payload(@JsonProperty("value") Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payload payload = (Payload) o;
        return Objects.equals(value, payload.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
