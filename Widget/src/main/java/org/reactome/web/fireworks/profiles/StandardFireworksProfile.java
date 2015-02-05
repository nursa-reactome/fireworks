package org.reactome.web.fireworks.profiles;

import org.reactome.web.fireworks.util.gradient.TwoColorGradient;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class StandardFireworksProfile extends FireworksProfile {

    private static final Double THRESHOLD = 0.05;

    TwoColorGradient nodeEnrichmentGradient;
    TwoColorGradient edgeEnrichmentGradient;

    TwoColorGradient nodeExpressionGradient;
    TwoColorGradient edgeExpressionGradient;

    public StandardFireworksProfile() {
        try {
            this.nodeEnrichmentGradient = new TwoColorGradient("#934A00", "#FFAD33");
            this.edgeEnrichmentGradient = new TwoColorGradient("#A96E33", "#FFB547");

            this.nodeExpressionGradient = new TwoColorGradient("#934A00", "#FFAD33");
            this.edgeExpressionGradient = new TwoColorGradient("#A96E33", "#FFB547");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNodeEnrichmentColour(double p) {
        if(p<=THRESHOLD) {
            return this.nodeEnrichmentGradient.getColor(p/0.05);
        }else{
            return this.getNodeEnrichmentColour();
        }
    }

    @Override
    public String getNodeEnrichmentColour() {
        return "#FFB2B2";
    }

    @Override
    public String getEdgeEnrichmentColour(double p) {
        if(p<=THRESHOLD) {
            return this.edgeEnrichmentGradient.getColor(p/0.05);
        }else{
            return getEdgeEnrichmentColour();
        }
    }

    @Override
    public String getEdgeEnrichmentColour() {
        return "#FFBABA";
    }

    @Override
    public String getNodeExpressionColour(double p, double expression, double min, double max) {
        if(p<=THRESHOLD) {
            return this.nodeExpressionGradient.getColor(expression, min, max);
        }else{
            return this.getNodeEnrichmentColour();
        }
    }

    @Override
    public String getEdgeExpressionColour(double p, double expression, double min, double max) {
        if(p<=THRESHOLD) {
            return this.edgeExpressionGradient.getColor(expression, min, max);
        }else{
            return getEdgeEnrichmentColour();
        }
    }

    @Override
    public String getNodeHighlightColour() {
        return "#E1ED55";
    }

    @Override
    public String getEdgeHighlightColour() {
        return "#E1ED55";
    }

    @Override
    public String getNodeSelectionColour() {
        return "#BBBBFF";
    }

    @Override
    public String getEdgeSelectionColour() {
        return "#EEEEFF";
    }

    @Override
    public String getNodeStandardColour() {
        return "#FF0000";
    }

    @Override
    public String getEdgeStandardColour() {
        return "#FFCCCC";
    }

    @Override
    public String getNodeFadeoutColour() {
        return "#FFE4E1";
    }

    @Override
    public String getEdgeFadeoutColour() {
        return "#FFECEC";
    }

    @Override
    public String getThumbnailHighlightColour() {
        return "#0A0A0A";
    }

    @Override
    public String getThumbnailSelectionColour() {
        return "#0000FF";
    }

    @Override
    public String getThumbnailStandardColour() {
        return "#FFCCCC";
    }

}
