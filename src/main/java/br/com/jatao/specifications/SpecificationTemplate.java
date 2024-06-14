package br.com.jatao.specifications;

import br.com.jatao.model.Cliente;
import br.com.jatao.model.OrdemDeServico;
import br.com.jatao.model.Servico;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    /**
     * Especificação para filtragem de clientes com base no nome (usando LikeIgnoreCase)
     * ou no celular (usando Equal).
     */
    @Or({
            @Spec(path = "nome", spec = LikeIgnoreCase.class),
            @Spec(path = "celular", spec = Equal.class)
    })
    public interface ClienteSpec extends Specification<Cliente> {
    }

    /**
     * Especificação para filtragem de ordens de serviço com base no cliente (usando LikeIgnoreCase),
     * no serviço (usando LikeIgnoreCase) ou na data (usando Equal).
     */
    @Or({
            @Spec(path = "cliente", spec = LikeIgnoreCase.class),
            @Spec(path = "servico", spec = LikeIgnoreCase.class),
            @Spec(path = "data", spec = Equal.class)
    })
    public interface OrdemDeServicoSpec extends Specification<OrdemDeServico> {
    }

    /**
     * Especificação personalizada para consulta de ordens de serviço com base na placa do carro.
     *
     * @param placa Placa do carro a ser consultada.
     * @return Especificação para consulta.
     */
    public static Specification<OrdemDeServico> ConsultarOrdensPorPlaca(String placa) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("carro").get("placa")), "%" + placa.toLowerCase() + "%");
    }

    /**
     * Especificação para filtragem de serviços com base no nome do serviço (usando LikeIgnoreCase)
     * ou no valor (usando Equal).
     */
    @Or({
            @Spec(path = "nomeServico", spec = LikeIgnoreCase.class),
            @Spec(path = "valor", spec = Equal.class)
    })
    public interface ServicoSpec extends Specification<Servico> {
    }
}
