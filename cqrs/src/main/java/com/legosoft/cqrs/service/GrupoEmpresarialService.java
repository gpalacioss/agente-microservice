package com.legosoft.cqrs.service;

import com.legosoft.cqrs.models.GrupoEmpresarial;

import java.util.concurrent.CompletableFuture;

public interface GrupoEmpresarialService {

    public CompletableFuture<String> createCommandGrupo(GrupoEmpresarial grupoEmpresarial);
}
