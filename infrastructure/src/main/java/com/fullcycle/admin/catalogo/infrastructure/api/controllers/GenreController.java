package com.fullcycle.admin.catalogo.infrastructure.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.infrastructure.api.GenreAPI;
import com.fullcycle.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import com.fullcycle.admin.catalogo.infrastructure.genre.models.GenreListResponse;
import com.fullcycle.admin.catalogo.infrastructure.genre.models.GenreResponse;
import com.fullcycle.admin.catalogo.infrastructure.genre.models.UpdateGenreRequest;

@RestController
public class GenreController implements GenreAPI{

    @Override
    public ResponseEntity<?> create(final CreateGenreRequest input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pagination<GenreListResponse> list(
        final String search, 
        final int page, 
        final int perPage,
        final  String sort,
        final  String direction
    ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GenreResponse getById(final String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<?> UpdateById(final String id, final UpdateGenreRequest input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(final String id) {
        // TODO Auto-generated method stub
        
    }
    
}
