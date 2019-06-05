package org.reactome.web.fireworks.search.handlers;

import com.google.gwt.event.shared.EventHandler;
import org.reactome.web.fireworks.search.events.SearchPerformedEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface SearchPerformedHandler extends EventHandler {
    void onSearchPerformed(SearchPerformedEvent event);
}
