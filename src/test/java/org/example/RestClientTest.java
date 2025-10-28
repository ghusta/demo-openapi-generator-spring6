package org.example;

import com.example.client.api.CityApi;
import com.example.client.api.CountryApi;
import com.example.client.model.City;
import com.example.client.model.Country;
import com.example.client.model.PagedModelCity;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

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
    @SuppressWarnings("all")
    void api_call_getCitiesByPage() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        CityApi cityApi = factory.createClient(CityApi.class);

        PagedModelCity citiesPage5 = cityApi.getAllCitiesPaginated(5, 10, null);
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
