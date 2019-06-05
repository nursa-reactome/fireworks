package org.reactome.web.fireworks.search.results;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.IsWidget;
import org.reactome.web.fireworks.search.SearchArguments;
import org.reactome.web.fireworks.search.SearchResultObject;
import org.reactome.web.fireworks.search.handlers.FacetsLoadedHandler;
import org.reactome.web.fireworks.search.handlers.ResultSelectedHandler;
import org.reactome.web.fireworks.search.results.data.model.FacetContainer;

import java.util.List;

/**
 * @author Kostas Sidiropoulos <ksidiro@ebi.ac.uk>
 */
public interface ResultsWidget extends IsWidget {

    HandlerRegistration addResultSelectedHandler(ResultSelectedHandler handler);

    HandlerRegistration addFacetsLoadedHandler(FacetsLoadedHandler handler);

    /**
     * Clears any selected list item
     */
    void clearSelection();

    /**
     * Updates the view with results based on the SearchArguments
     */
    void updateResults(SearchArguments searchArguments);

    /**
     * Sets the available facets
     *
     * @param facets the list of facets
     */
    void setFacets(List<FacetContainer> facets);

    /**
     * Temporarily suspends highlighting of the selection
     * without clearing the selected item
     */
    void suspendSelection();

}
