package org.reactome.web.fireworks.search.results;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.resources.client.ImageResource;
import org.reactome.web.fireworks.search.SearchArguments;
import org.reactome.web.fireworks.search.SearchResultObject;
import org.reactome.web.fireworks.search.results.data.model.Entry;
import org.reactome.web.fireworks.util.SearchResultImageMapper;
import org.reactome.web.pwp.model.client.factory.SchemaClass;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 * @author Kostas Sidiropoulos <ksidiro@ebi.ac.uk>
 */
public class ResultItem implements SearchResultObject, Entry {

    private String stId;
    private String dbId;
    private String name;
    private String exactType;
    private List<String> compartmentNames;
    private String compartments = "";
    private String referenceIdentifier;
    private String databaseName;
    private String referenceURL;

    private String primary;
    private String primaryTooltip;
    private String secondary;
    private String tertiary;

    private SearchResultImageMapper.ImageContainer imageContainer;

    public ResultItem(Entry entry) {
        stId = entry.getStId();
        dbId = entry.getDbId();
        name = entry.getName();
        exactType = entry.getExactType();
        compartmentNames = entry.getCompartmentNames();
        referenceIdentifier = entry.getReferenceIdentifier();
        databaseName = entry.getDatabaseName();
        referenceURL = entry.getReferenceURL();

        setCompartments(compartmentNames);

        imageContainer = SearchResultImageMapper.getImage(exactType);
    }

    @Override
    public String getStId() {
        return stId;
    }

    @Override
    public String getDbId() {
        return dbId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExactType() {
        return exactType;
    }

    @Override
    public List<String> getCompartmentNames() {
        return compartmentNames;
    }

    public String getCompartments() {
        return compartments;
    }

    @Override
    public ImageResource getImageResource() {
        return imageContainer.getImageResource();
    }

    @Override
    public String getReferenceIdentifier() {
        return referenceIdentifier;
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public String getReferenceURL() {
        return referenceURL;
    }

    @Override
    public String getPrimarySearchDisplay() {
        return primary;
    }

    @Override
    public String getPrimaryTooltip() {
        return primaryTooltip;
    }

    @Override
    public String getSecondarySearchDisplay() {
        return secondary;
    }

    @Override
    public String getTertiarySearchDisplay() {
        return tertiary;
    }

    @Override
    public void setSearchDisplay(SearchArguments arguments) {
        primary = name;
        primaryTooltip = name;
        secondary = stId != null ? stId : dbId;
        tertiary = compartments;

//        isDisplayed = (arguments.getDiagramStId().equalsIgnoreCase(stId));

        RegExp regExp = arguments.getHighlightingExpression();
        if (regExp != null) {
            primary = regExp.replace(primary, "<u><strong>$1</strong></u>");
            secondary = regExp.replace(secondary, "<u><strong>$1</strong></u>");
        }
    }

    @Override
    public SchemaClass getSchemaClass() {
        return SchemaClass.getSchemaClass(exactType);
    }

//    @Override
//    public boolean isDisplayed() {
//        return isDisplayed;
//    }

    private void setCompartments(List<String> compartmentNames) {
        if (compartmentNames != null && !compartmentNames.isEmpty()) {
            compartments = compartmentNames.stream()
                                    .map(ResultItem::capitalize)
                                    .collect(Collectors.joining(", "));
        }
    }

    private static String capitalize(final String input) {
        if (input == null || (input.length()) == 0) {
            return input;
        }

        final String firstChar = input.substring(0,1);
        return firstChar.toUpperCase() + input.substring(1);
    }
}
