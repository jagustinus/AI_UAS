package com.example.app.ai_uas.model;

import java.util.List;

public class Book {

    private String title, isbn, shortDescription, longDescription, thumbnailUrl;
    private List<String> authors;
    private int pageCount;
    private int drawableResources;

    public Book(String title, String isbn, String shortDescription, String longDescription, String thumbnailUrl, List<String> authors, int pageCount, int drawableResources) {
        this.title = title;
        this.isbn = isbn;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.thumbnailUrl = thumbnailUrl;
        this.authors = authors;
        this.pageCount = pageCount;
        this.drawableResources = drawableResources;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getDrawableResources() {
        return drawableResources;
    }

    public void setDrawableResources(int drawableResources) {
        this.drawableResources = drawableResources;
    }
}