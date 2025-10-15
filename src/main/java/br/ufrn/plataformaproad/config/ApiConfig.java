package br.ufrn.plataformaproad.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class ApiConfig {

    @Value("${api.url.autenticacao:autenticacao.info.ufrn.br}")
    private String urlBaseAutenticacao;

    @Value("${api.client.id:dados-compras-FbRUEj2IB0RikqvY}")
    private String clientId;

    @Value("${api.client.secret:YPY0am0UO8Rjbw4h0NXMk4qcuThng0jI}")
    private String clientSecret;

    @Value("${api.url.base:api.info.ufrn.br}")
    private String urlBase;

    @Value("${api.versao:v1}")
    private String versao;

    @Value("${api.x-api-key:mr4U8K78RL54yHbvzSHEizLqzT3TqW7F}")
    private String xApiKey;

    public String getUrlBaseAutenticacao() {
        return "https://" + urlBaseAutenticacao;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getUrlBase() {
        return "https://" + urlBase;
    }

    public String getVersao() {
        return versao;
    }

    public String getXApiKey() {
        return xApiKey;
    }
}
