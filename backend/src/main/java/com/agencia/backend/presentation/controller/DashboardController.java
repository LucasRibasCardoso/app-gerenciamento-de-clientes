package com.agencia.backend.presentation.controller;

import com.agencia.backend.application.useCase.client.FindDataForDashboardUseCase;
import com.agencia.backend.presentation.dto.client.ClientResponseDTO;
import com.agencia.backend.presentation.dto.dashboard.DashboardResponseDTO;
import com.agencia.backend.presentation.mapper.client.ClientMapper;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final FindDataForDashboardUseCase findDataForDashboardUseCase;
    private final ClientMapper clientMapper;

    public DashboardController(
            FindDataForDashboardUseCase findDataForDashboardUseCase, ClientMapper clientMapper
    ) {
        this.findDataForDashboardUseCase = findDataForDashboardUseCase;
        this.clientMapper = clientMapper;
    }

    @GetMapping("/data")
    public ResponseEntity<DashboardResponseDTO> getDashboardData() {

        Long totalClients = findDataForDashboardUseCase.getTotalClients();
        Long totalNewClientsLast30Days = findDataForDashboardUseCase.getTotalNewClientsLast30Days();
        Long totalClientsWithPassport = findDataForDashboardUseCase.getTotalClientsWithPassport();
        Long totalClientsWithoutPassport = findDataForDashboardUseCase.getTotalClientsWithoutPassport();
        List<ClientResponseDTO> clientsThatNeedToRenewPassport =
                findDataForDashboardUseCase.getClientsThatNeedToRenewPassport()
                        .stream()
                        .map(clientMapper::toDTO)
                        .toList();

        DashboardResponseDTO dashboardResponseDTO = new DashboardResponseDTO(
                totalClients,
                totalNewClientsLast30Days,
                totalClientsWithPassport,
                totalClientsWithoutPassport,
                clientsThatNeedToRenewPassport
        );

        return ResponseEntity.ok(dashboardResponseDTO);
    }

}
