package org.reactome.web.fireworks.search.results.global;

import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import org.reactome.web.fireworks.search.results.ResultItem;
import org.reactome.web.fireworks.search.SearchArguments;
import org.reactome.web.fireworks.search.SearchResultObject;
import org.reactome.web.fireworks.search.results.data.SearchException;
import org.reactome.web.fireworks.search.results.data.SearchResultFactory;
import org.reactome.web.fireworks.search.results.data.model.SearchError;
import org.reactome.web.fireworks.search.results.data.model.SearchResult;
import org.reactome.web.fireworks.search.results.data.model.TargetTerm;
import org.reactome.web.fireworks.util.Console;
import org.reactome.web.scroller.client.provider.AbstractListAsyncDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.reactome.web.scroller.client.util.Placeholder.ROWS;
import static org.reactome.web.scroller.client.util.Placeholder.START;

/**
 * @author Kostas Sidiropoulos <ksidiro@ebi.ac.uk>
 */
public class GlobalSearchProvider extends AbstractListAsyncDataProvider<SearchResultObject> {

    private SearchArguments args;
    private StringBuilder stringBuilder;

    private Consumer<SearchResult> resultConsumer;

    public GlobalSearchProvider() {
        stringBuilder = new StringBuilder();
    }

    /**
     * Use this constructor in case you need to consume
     * the result in another corner
     */
    public GlobalSearchProvider(Consumer<SearchResult> resultConsumer) {
        stringBuilder = new StringBuilder();
        this.resultConsumer = resultConsumer;
    }

    @Override
    protected List<SearchResultObject> processResult(String body) {
        List<SearchResultObject> rtn = new ArrayList<>();
        try {
            SearchResult result = SearchResultFactory.getSearchObject(SearchResult.class, body);
            rtn = result.getEntries().stream()
                    .map(ResultItem::new)
                    .peek(e -> e.setSearchDisplay(args))
                    .collect(Collectors.toList());

            if(resultConsumer != null) {
                resultConsumer.accept(result);
            }

        } catch (SearchException e) {
            //TODO deal with this error
            Console.error(e.getMessage());
        }
        return rtn;
    }

    @Override
    protected String processError(Response response) {
        String rtn = "An error occurred while searching for " + args.getQuery() + ". ";
        try {
            SearchError error = SearchResultFactory.getSearchObject(SearchError.class, response.getText());
            if (error != null) {
                if(error.getMessages() != null) {
                    rtn = error.getMessages().stream()
                                             .collect(joining(". "));
                }

                List<TargetTerm> targets = error.getTargets();
                if(targets != null) {
                    targets = targets.stream()
                                     .filter(TargetTerm::getTarget)
                                     .collect(toList());

                    if(!targets.isEmpty()) {
                        int size = targets.size();
                        String terms = targets.stream()
                                              .map(TargetTerm::getTerm)
                                              .collect(joining(", "));
                        rtn += " <br><span>However, " + terms + (size == 1 ? " is" : " are") + " within our identified curation targets.</span>";
                    }
                }
            }
        } catch (SearchException e) {
            e.printStackTrace();
        }
        return rtn;
    }

    public void setSearchArguments(SearchArguments args, Set<String> selectedFacets, String baseUrl) {
        this.args = args;
        String types = selectedFacets.stream()
                .map(facet -> facet = "&types=" + facet)
                .collect(joining());

        stringBuilder.setLength(0);
        super.setURL(
                stringBuilder
                .append(baseUrl)
                .append("?query=")
                .append(URL.encode(args.getQuery()))
                .append(types)
                .append("&species=")
                .append(args.getSpecies())
                .append("&")
                .append(START.getUrlValue())
                .append("&")
                .append(ROWS.getUrlValue())
                .toString()
        );
    }
}
