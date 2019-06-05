package org.reactome.web.fireworks.util.storage.solutions;

/**
 * @author Kostas Sidiropoulos <ksidiro@ebi.ac.uk>
 */
public interface StorageSolution {

    String read(String key);

    void write(String key, String value);

    void delete(String key);
}
