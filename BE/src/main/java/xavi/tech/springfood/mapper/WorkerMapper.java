package xavi.tech.springfood.mapper;

import org.mapstruct.Mapper;
import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Worker;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
	
	Worker dtoToWorker(AccountCredentialsDTO dto);
	AccountCredentialsDTO workerToDto(Worker worker);

}
