package org.example;

import com.example.client.api.CityApi;
import com.example.client.api.CountryApi;
import com.example.client.model.City;
import com.example.client.model.Country;
import com.example.client.model.Pageable;
import com.example.client.model.PagedModelCity;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RestClientTest {

    @Test
    @SuppressWarnings("all")
    void api_call_getAllCities() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        CityApi cityApi = factory.createClient(CityApi.class);

        List<City> allCities = cityApi.getAllCities();
        assertThat(allCities).isNotEmpty();
        System.out.println(allCities.size());
    }

    @Test
    void serializePageData() throws IOException {
        Pageable pageRequest = new Pageable().page(5).size(10);

        JsonMapper jsonMapper = JsonMapper.builder().build();

        StringWriter stringWriter = new StringWriter();
        jsonMapper.writeValue(stringWriter, pageRequest);
        assertThat(stringWriter.toString())
                .isNotEmpty()
                .startsWith("{")
                .contains("\"page\":5")
                .contains("\"sort\":[]")
                .endsWith("}");
    }

    @Test
    @SuppressWarnings("all")
    void api_call_getCitiesByPage() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        CityApi cityApi = factory.createClient(CityApi.class);

        Pageable pageRequest = new Pageable().page(5).size(10);
        PagedModelCity citiesPage5 = cityApi.getAllCitiesPaginated(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getSort());
        assertThat(citiesPage5.getContent()).isNotEmpty();
        System.out.println(citiesPage5.getPage().getSize());
        System.out.println(citiesPage5.getContent().get(0).toString());
    }

    @Test
    @SuppressWarnings("all")
    void api_call_getAllCountries() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        CountryApi countryApi = factory.createClient(CountryApi.class);

        List<Country> allCountries = countryApi.getAllCountries();
        assertThat(allCountries).isNotEmpty();
        System.out.println(allCountries.size());
    }
}
