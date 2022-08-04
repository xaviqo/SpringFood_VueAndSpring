package xavi.tech.springfood.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Worker;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T17:54:11+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.15 (Private Build)"
)
@Component
public class WorkerMapperImpl implements WorkerMapper {

    @Override
    public Worker dtoToWorker(AccountCredentialsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Worker worker = new Worker();

        worker.setEmail( dto.getEmail() );
        worker.setPassword( dto.getPassword() );
        worker.setRole( dto.getRole() );

        return worker;
    }

    @Override
    public AccountCredentialsDTO workerToDto(Worker worker) {
        if ( worker == null ) {
            return null;
        }

        AccountCredentialsDTO accountCredentialsDTO = new AccountCredentialsDTO();

        accountCredentialsDTO.setEmail( worker.getEmail() );
        accountCredentialsDTO.setPassword( worker.getPassword() );
        accountCredentialsDTO.setRole( worker.getRole() );

        return accountCredentialsDTO;
    }
}
