package br.com.jatao.specifications;

import br.com.jatao.model.Cliente;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    //@And // vai trazer se ambos parametros forem verdadeiros;
    @Or({

            @Spec(path = "nome", spec = LikeIgnoreCase.class, params = {"%?0%"}), //
            // Equal para trazer exatamente o que foi digitado
            @Spec(path = "celular", spec = Equal.class,params = {"%?0%"})
    })
    public interface ClienteSpec extends Specification<Cliente> {

    }
}
