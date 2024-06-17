package br.com.jatao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModalMapperConfig {
    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        // Configurar o ModelMapper para ignorar campos nulos e habilitar matching de campos
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setCollectionsMergeEnabled(true)
                .setDeepCopyEnabled(true);

        // Configuração recursiva
        modelMapper.getConfiguration()
                .setPropertyCondition(context -> context.getSource() != null);

        return modelMapper;
    }
}
