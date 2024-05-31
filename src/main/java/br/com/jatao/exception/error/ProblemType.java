package br.com.jatao.exception.error;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),

    SERVICO_JA_CADASTRADO("/servicos", " Serviço já cadastrado"),
    OBJETO_NAO_ENCONTRADO("/ordem", "objeto não encontrado"),
    ERRO_AO_CRIAR_ORDEM("/ordem", "Erro ao criar ordem"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível");


    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://localhost:8080" + path;
        this.title = title;
    }
}

