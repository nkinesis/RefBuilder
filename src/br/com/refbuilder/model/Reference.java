package br.com.refbuilder.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Gabriel Cavalheiro Ullmann
 * @version 1.0
 * @since 2016-10-31
 */

public class Reference {

    public String author;
    public String title;
    public String url;
    public Date lastAccess;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        //url validation
        String regex = "^(https?|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        if (url.matches(regex)) {
            this.url = url;
        } else {
            this.url = "";
        }
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    @Override
    public String toString() {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        String date = dt.format(this.lastAccess);
        String ref = this.author.toUpperCase() + ". \"" + this.title + "\". Disponível em: <" + this.url + ">. Acesso em: " + date;
        return ref;
    }
}
