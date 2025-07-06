package com.agencia.backend.presentation.dto.dashboard;

import com.agencia.backend.presentation.dto.client.ClientResponseDTO;

import java.util.List;

public record DashboardResponseDTO(
        Long totalClients,
        Long newClientsLast30Days,
        Long clientsWithPassport,
        Long clientsWithoutPassport,
        List<ClientResponseDTO> clientsThatNeedToRenewPassport
) {
}
