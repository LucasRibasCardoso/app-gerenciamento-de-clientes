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

    Client client5 = new Client(
        null,
        "Ana Souza",
        "987.654.321-00", LocalDate.of(1990, 3, 12),
        "(11) 98765-4321",
        "ana.souza@example.com",
        new Passport("OP789012", LocalDate.of(2022, 5, 10), LocalDate.of(2032, 5, 10)),
        new Address("12345-678", "Brasil", "SP", "São Paulo", "Moema", "Avenida Ibirapuera", "Apto 501", "123")
    );

    Client client6 = new Client(
        null,
        "Carlos Mendes",
        "654.321.987-00", LocalDate.of(1988, 9, 18),
        "(31) 99876-5432",
        "carlos.mendes@example.com",
        new Passport("QR123456", LocalDate.of(2020, 7, 20), LocalDate.of(2030, 7, 20)),
        new Address("54321-876", "Brasil", "MG", "Belo Horizonte", "Savassi", "Rua da Bahia", "Sala 202", "202")
    );

    Client client7 = new Client(
        null,
        "Juliana Almeida",
        "321.654.987-00", LocalDate.of(1995, 11, 30),
        "(51) 98765-4321",
        "juliana.almeida@example.com",
        new Passport("ST789012", LocalDate.of(2021, 9, 15), LocalDate.of(2031, 9, 15)),
        new Address("98765-432", "Brasil", "RS", "Porto Alegre", "Moinhos de Vento", "Rua Padre Chagas", "Casa 5", "505")
    );

    Client client8 = new Client(
        null,
        "Ricardo Nunes",
        "456.789.123-00", LocalDate.of(1983, 5, 22),
        "(81) 99876-5432",
        "ricardo.nunes@example.com",
        new Passport("UV123456", LocalDate.of(2018, 12, 1), LocalDate.of(2028, 12, 1)),
        new Address("23456-789", "Brasil", "PE", "Recife", "Boa Viagem", "Avenida Boa Viagem", "Apto 1001", "1001")
    );

    Client client9 = new Client(
        null,
        "Patrícia Lima",
        "789.123.456-00", LocalDate.of(1991, 8, 14),
        "(48) 98765-4321",
        "patricia.lima@example.com",
        new Passport("WX789012", LocalDate.of(2023, 2, 28), LocalDate.of(2033, 2, 28)),
        new Address("87654-321", "Brasil", "SC", "Florianópolis", "Centro", "Rua Felipe Schmidt", "Sala 303", "303")
    );

    Client client10 = new Client(
        null,
        "Marcos Silva",
        "123.789.456-00", LocalDate.of(1989, 12, 3),
        "(85) 99876-5432",
        "marcos.silva@example.com",
        new Passport("YZ123456", LocalDate.of(2019, 6, 10), LocalDate.of(2029, 6, 10)),
        new Address("34567-890", "Brasil", "CE", "Fortaleza", "Meireles", "Avenida Beira Mar", "Apto 2002", "2002")
    );

    Client client11 = new Client(
        null,
        "Camila Rocha",
        "987.123.654-00", LocalDate.of(1993, 4, 17),
        "(61) 98765-4321",
        "camila.rocha@example.com",
        new Passport("AB789012", LocalDate.of(2022, 8, 5), LocalDate.of(2032, 8, 5)),
        new Address("65432-109", "Brasil", "DF", "Brasília", "Asa Sul", "Quadra 303", "Bloco B", "303")
    );

    Client client12 = new Client(
        null,
        "Gustavo Santos",
        "654.987.321-00", LocalDate.of(1986, 7, 9),
        "(62) 99876-5432",
        "gustavo.santos@example.com",
        new Passport("CD123456", LocalDate.of(2020, 4, 20), LocalDate.of(2030, 4, 20)),
        new Address("76543-210", "Brasil", "GO", "Goiânia", "Setor Marista", "Rua 9", "Casa 15", "15")
    );

    Client client13 = new Client(
        null,
        "Larissa Fernandes",
        "321.987.654-00", LocalDate.of(1996, 10, 25),
        "(71) 98765-4321",
        "larissa.fernandes@example.com",
        new Passport("EF789012", LocalDate.of(2021, 11, 30), LocalDate.of(2031, 11, 30)),
        new Address("87654-321", "Brasil", "BA", "Salvador", "Pituba", "Avenida Manoel Dias da Silva", "Apto 404", "404")
    );

    Client client14 = new Client(
        null,
        "Roberto Alves",
        "456.123.789-00", LocalDate.of(1984, 1, 8),
        "(31) 99876-5432",
        "roberto.alves@example.com",
        new Passport("GH123456", LocalDate.of(2018, 3, 15), LocalDate.of(2028, 3, 15)),
        new Address("98765-432", "Brasil", "MG", "Belo Horizonte", "Lourdes", "Rua da Bahia", "Sala 101", "101")
    );


    clientRepository.save(client1);
    clientRepository.save(client2);
    clientRepository.save(client3);
    clientRepository.save(client4);
    clientRepository.save(client5);
    clientRepository.save(client6);
    clientRepository.save(client7);
    clientRepository.save(client8);
    clientRepository.save(client9);
    clientRepository.save(client10);
    clientRepository.save(client11);
    clientRepository.save(client12);
    clientRepository.save(client13);
    clientRepository.save(client14);

  }

}
