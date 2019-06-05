package org.reactome.web.fireworks.util.popups.export;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

import java.util.Arrays;
import java.util.List;

/**
 * @author Kostas Sidiropoulos <ksidiro@ebi.ac.uk>
 */
public class DownloadButton<T extends DownloadType> extends FlowPanel {

    private boolean isExpanded;
    private static List<String> labels = Arrays.asList("Low", "Medium", "High");

    public DownloadButton(final T type, final List<String> urls) {
        setStyleName(RESOURCES.getCSS().downloadItem());
        setTitle("Download in " + type.getTooltip() + " format");

        FlowPanel container = new FlowPanel();
        container.add(new Image(type.getIcon()));

        if(urls.size() == 1) {
            String url = urls.get(0);
            SafeHtml html = SafeHtmlUtils.fromTrustedString(container.toString());
            Anchor anchor = new Anchor(html, url, "_blank");
            anchor.getElement().setAttribute("rel", "noindex,nofollow");
            add(anchor);
        } else if (urls.size() > 1){
            container.add(getQualityLinks(urls));
            add(container);
            addDomHandler(event -> expandCollapse(), ClickEvent.getType());
            addDomHandler(event -> {if (isExpanded) expandCollapse();}, MouseOutEvent.getType());
        }
    }

    private void expandCollapse() {
        if (isExpanded) {
            removeStyleName(RESOURCES.getCSS().downloadItemExpanded());
        } else {
            addStyleName(RESOURCES.getCSS().downloadItemExpanded());
        }
        isExpanded = !isExpanded;
    }

    private FlowPanel getQualityLinks(final List<String> urls) {
        FlowPanel linksPanel = new FlowPanel();
        linksPanel.setStyleName(RESOURCES.getCSS().linkPanel());
        for (int i = 0; i < urls.size() ; i++) {
            Anchor link = new Anchor(labels.get(i), urls.get(i), "_blank");
            link.getElement().setAttribute("rel", "noindex,nofollow");
            link.setTitle("Download in " + labels.get(i).toLowerCase() + " quality");
            link.setStyleName(RESOURCES.getCSS().linkItem());
            linksPanel.add(link);
        }
        return linksPanel;
    }


    public static Resources RESOURCES;
    static {
        RESOURCES = GWT.create(Resources.class);
        RESOURCES.getCSS().ensureInjected();
    }

    public interface Resources extends ClientBundle {
        @Source(ResourceCSS.CSS)
        ResourceCSS getCSS();
    }

    @CssResource.ImportedWithPrefix("diagram-DownloadButton")
    public interface ResourceCSS extends CssResource {
        /**
         * The path to the default CSS styles used by this resource.
         */
        String CSS = "org/reactome/web/fireworks/util/popups/export/DownloadButton.css";

        String downloadItem();

        String downloadItemExpanded();

        String linkPanel();

        String linkItem();
    }
}
