package com.example.app.ai_uas.model;

public class Book {

    private String title, isbn, shortDesc, longDesc, imgUrl;
    private String[] author;
    private int pages;
    private int drawableResources;

    public Book(){

    }

    public Book(int drawableResources) {
        this.drawableResources = drawableResources;
    }

    public Book(String title, String isbn, String shortDesc, String longDesc, String imgUrl, String[] author, int pages, int drawableResources) {
        this.title = title;
        this.isbn = isbn;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.imgUrl = imgUrl;
        this.author = author;
        this.pages = pages;
        this.drawableResources = drawableResources;
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

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getDrawableResources() {
        return drawableResources;
    }

    public void setDrawableResources(int drawableResources) {
        this.drawableResources = drawableResources;
    }
}
