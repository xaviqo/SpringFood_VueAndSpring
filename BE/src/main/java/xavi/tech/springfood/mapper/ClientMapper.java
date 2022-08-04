package xavi.tech.springfood.mapper;

import org.mapstruct.Mapper;

import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
	
	Client dtoToClient(AccountCredentialsDTO dto);
	AccountCredentialsDTO clientToDto(Client client);
	
}
