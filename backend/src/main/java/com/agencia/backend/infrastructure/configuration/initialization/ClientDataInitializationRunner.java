package com.agencia.backend.infrastructure.configuration.initialization;

import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.domain.repository.ClientRepository;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientDataInitializationRunner implements CommandLineRunner {

  private ClientRepository clientRepository;

  public ClientDataInitializationRunner(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    insertClients();
  }

  private void insertClients() {
    Client client1 = new Client(
        null,
        "Maria Oliveira",
        "123.456.789-00",
        LocalDate.of(1985, 7, 25),
        "(21) 99876-5432",
        "maria.oliveira@example.com",
        new Passport("XY987654", LocalDate.of(2019, 8, 15), LocalDate.of(2029, 8, 15)),
        new Address("23456-789", "Brasil", "RJ", "Rio de Janeiro", "Copacabana", "Avenida Atlântica", "Bloco B", "456")
    );

    Client client2 = new Client(
        null,
        "Eduardo Pereira",
        "567.890.123-45", LocalDate.of(1987, 6, 20),
        "(41) 99987-6543",
        "eduardo.pereira@example.com",
        new Passport("IJ456789", LocalDate.of(2020, 11, 12), LocalDate.of(2030, 11, 12)),
        new Address("67890-234", "Brasil", "PR", "Curitiba", "Centro", "Rua XV de Novembro", "Loja 10", "101")
    );

    Client client3 = new Client(
        null,
        "Fernanda Costa",
        "345.678.901-23", LocalDate.of(1992, 2, 15),
        "(62) 98877-6543",
        "fernanda.costa@example.com",
        new Passport("KL123456", LocalDate.of(2021, 3, 22), LocalDate.of(2031, 3, 22)),
        new Address("89012-345", "Brasil", "GO", "Goiânia", "Setor Bueno", "Rua 15", "Apto 203", "456")
    );

    Client client4 = new Client(
        null,
        "Felipe Martins",
        "234.567.890-12", LocalDate.of(1994, 4, 5),
        "(71) 99876-4321",
        "felipe.martins@example.com",
        new Passport("MN654321", LocalDate.of(2019, 1, 1), LocalDate.of(2029, 1, 1)),
        new Address("45678-912", "Brasil", "BA", "Salvador", "Barra", "Avenida Oceânica", "Casa 7", "789")
    );


    clientRepository.save(client1);
    clientRepository.save(client2);
    clientRepository.save(client3);
    clientRepository.save(client4);

  }

}
