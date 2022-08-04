package xavi.tech.springfood.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T17:54:11+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.15 (Private Build)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client dtoToClient(AccountCredentialsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Client client = new Client();

        client.setEmail( dto.getEmail() );
        client.setPassword( dto.getPassword() );
        client.setRole( dto.getRole() );

        return client;
    }

    @Override
    public AccountCredentialsDTO clientToDto(Client client) {
        if ( client == null ) {
            return null;
        }

        AccountCredentialsDTO accountCredentialsDTO = new AccountCredentialsDTO();

        accountCredentialsDTO.setEmail( client.getEmail() );
        accountCredentialsDTO.setPassword( client.getPassword() );
        accountCredentialsDTO.setRole( client.getRole() );

        return accountCredentialsDTO;
    }
}
