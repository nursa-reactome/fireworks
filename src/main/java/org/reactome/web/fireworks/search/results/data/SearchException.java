package org.reactome.web.fireworks.search.results.data;

/**
 * @author Kostas Sidiropoulos <ksidiro@ebi.ac.uk>
 */
public class SearchException extends Exception {

    public SearchException() {
    }

    public SearchException(String message, Throwable cause) {
        super(message, cause);
    }

}
