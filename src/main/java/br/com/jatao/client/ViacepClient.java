package br.com.jatao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep-api", url = "https://viacep.com.br/ws/")
public interface ViacepClient {

    @GetMapping("{cep}/json/")
    CepRequest getEndereco(@PathVariable String cep);

}
