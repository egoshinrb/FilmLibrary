package com.example.filmlibrary.repository;

import com.example.filmlibrary.model.Film;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface FilmRepository  extends GenericRepository<Film> {
    @Query(nativeQuery = true,
            value = """
                       select distinct f.*
                       from films f
                                left join films_directors fd on f.id = fd.film_id
                                left join directors d on fd.director_id = d.id
                       where f.title ilike '%' || coalesce(:title, '%')  || '%'
                         and cast(f.genre as char) like coalesce(:genre, '%')
                         and coalesce(d.directors_fio, '%') ilike '%' ||  coalesce(:directors_fio, '%')  || '%'
                         and f.is_deleted = false
                    """)
    Page<Film> searchFilms(@Param(value = "title") String bookTitle,
                           @Param(value = "genre") String genre,
                           @Param(value = "directors_fio") String directorFIO,
                           Pageable pageRequest);


    @Query("""
          select case when count(f) > 0 then false else true end
          from Film f join Order o on f.id = o.film_id
          where f.id = :id and o.purchase = false
          """)
    boolean isFilmCanBeDeleted(final Long id);
}