package io.luciomarcel.catalog.admin.infrastructure.genre.persistence;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import io.luciomarcel.catalog.admin.domain.category.CategoryID;

@Entity
@Table(name = "genre_categories")
public class GenreCategoryJpaEntity {

    @EmbeddedId
    private GenreCategoryID id;

    @ManyToOne
    @MapsId("genreId")
    private GenreJpaEntity genre;

    public GenreCategoryJpaEntity() {}

    private GenreCategoryJpaEntity(final GenreJpaEntity aGenre, final CategoryID aCetegoryId) {
        this.id = GenreCategoryID.from(aGenre.getId(), aCetegoryId.getValue());
        this.genre = aGenre;
    }

    public static GenreCategoryJpaEntity from(final GenreJpaEntity aGenre, final CategoryID aCetegoryId) {
        return new GenreCategoryJpaEntity(aGenre, aCetegoryId);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GenreCategoryJpaEntity)) {
            return false;
        }
        GenreCategoryJpaEntity genreCategoryJpaEntity = (GenreCategoryJpaEntity) o;
        return Objects.equals(id, genreCategoryJpaEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public GenreCategoryID getId() {
        return this.id;
    }

    public void setId(GenreCategoryID id) {
        this.id = id;
    }

    public GenreJpaEntity getGenre() {
        return this.genre;
    }

    public void setGenre(GenreJpaEntity genre) {
        this.genre = genre;
    }

    
}
