package io.luciomarcel.catalog.admin.infrastructure.genre.persistence;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GenreCategoryID implements Serializable {

    @Column(name = "genre_id", nullable = false)
    private String genreId;

    @Column(name = "category_id", nullable = false)
    private String categoryId;

    public GenreCategoryID() {}


    private GenreCategoryID(final String aGenreId, final String aCategoryId) {
        this.genreId = aGenreId;
        this.categoryId = aCategoryId;
    }

    public static GenreCategoryID from(final String aGenreId, final String aCategoryId) {
        return new GenreCategoryID(aGenreId,aCategoryId);
    }

    public String getGenreId() {
        return this.genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GenreCategoryID)) {
            return false;
        }
        GenreCategoryID genreCategoryID = (GenreCategoryID) o;
        return Objects.equals(genreId, genreCategoryID.genreId) && Objects.equals(categoryId, genreCategoryID.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, categoryId);
    }

}
