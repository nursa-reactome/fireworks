package org.reactome.web.fireworks.profiles;

import org.reactome.web.fireworks.profiles.model.Profile;
import org.reactome.web.fireworks.util.Color;
import org.reactome.web.fireworks.util.ColorMap;
import org.reactome.web.fireworks.util.gradient.ThreeColorGradient;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public final class FireworksProfile {

    private static final Double THRESHOLD = 0.05;
    private final Profile profile;

    ThreeColorGradient nodeEnrichment;
    ThreeColorGradient nodeExpression;

    ThreeColorGradient edgeEnrichment;
    ThreeColorGradient edgeExpression;

    ThreeColorGradient nodeBaseGradient;
    ThreeColorGradient edgeBaseGradient;

    public ColorMap nodeRegulationColorMap;
    public ColorMap edgeRegulationColorMap;


    FireworksProfile(Profile profile) {
        this.profile = profile;
        try {
            this.nodeEnrichment = new ThreeColorGradient(profile.getNode().getEnrichment());
            this.nodeExpression = new ThreeColorGradient(profile.getNode().getExpression());

            this.edgeEnrichment = new ThreeColorGradient(profile.getEdge().getEnrichment());
            this.edgeExpression = new ThreeColorGradient(profile.getEdge().getExpression());

            Color darkerNodeGray = new Color(profile.getNode().getHit()).darker(0.5);
            Color darkerEdgeGray = new Color(profile.getEdge().getHit()).darker(0.5);
            this.nodeBaseGradient = new ThreeColorGradient("#" + darkerNodeGray.getHex(), null, profile.getNode().getHit());
            this.edgeBaseGradient = new ThreeColorGradient("#" + darkerEdgeGray.getHex(), null, profile.getEdge().getHit());

            nodeRegulationColorMap = ColorMap.fromGradient(nodeExpression);
            edgeRegulationColorMap = ColorMap.fromGradient(edgeExpression);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final String getName() {
        return profile.getName();
    }

    /*#################### NODE ####################*/
    public final String getNodeInitialColour(){
        return this.profile.getNode().getInitial();
    }
    public final String getNodeHighlightColour(){
        return this.profile.getNode().getHighlight();
    }
    public final String getNodeHitColour(){
        return this.profile.getNode().getHit();
    }
    public final String getNodeSelectionColour(){
        return this.profile.getNode().getSelection();
    }
    public final String getNodeFlagColour(){
        return this.profile.getNode().getFlag();
    }
    public final String getNodeFadeoutColour(){
        return this.profile.getNode().getFadeout();
    }
    public final String getNodeEnrichmentColour(double p) {
        if(p<=THRESHOLD) {
            return this.nodeEnrichment.getColor(p/THRESHOLD);
        }else{
            return this.profile.getNode().getHit();
        }
    }

    public final String getNodeEnrichmentColour(double p, double filter) {
        if (filter > THRESHOLD) {
            if (p <= THRESHOLD) {
                return this.nodeEnrichment.getColor(p/THRESHOLD);
            } else if (p <= filter && filter != 1.0) {
                return nodeBaseGradient.getColor(p/filter);
            } else {
                return this.profile.getNode().getHit();
            }
        } else {
            if (p <= filter) {
                return this.nodeEnrichment.getColor(p/THRESHOLD);
            } else {
                return this.profile.getNode().getHit();
            }
        }
    }

    public final String getNodeCoverageColour(double p) {
        return this.nodeEnrichment.getColor(p);
    }
    public final String getNodeExpressionColour(double p) {
        return this.nodeExpression.getColor(p);
    }
    public final String getNodeExpressionColour(double p, double expression, double min, double max, double filter) {
        if (filter > THRESHOLD) {
            if (p <= THRESHOLD) {
                return this.nodeExpression.getColor(expression, min, max);
            } else if (p <= filter && filter != 1.0) {
                return nodeBaseGradient.getColor(p/filter);
            } else{
                return this.profile.getNode().getHit();
            }
        } else {
            if (p <= filter) {
                return this.nodeExpression.getColor(expression, min, max);
            } else {
                return this.profile.getNode().getHit();
            }
        }
    }

    public final String getNodeRegulationColour(double p, double expression, double filter) {
        if (filter > THRESHOLD) {
            if (p <= THRESHOLD) {
                return this.nodeRegulationColorMap.getColor((int) expression);
            } else if (p <= filter && filter != 1.0) {
                return nodeBaseGradient.getColor(p/filter);
            } else{
                return this.profile.getNode().getHit();
            }
        } else {
            if (p <= filter) {
                return this.nodeRegulationColorMap.getColor((int) expression);
            } else {
                return this.profile.getNode().getHit();
            }
        }
    }


    /*#################### EDGE ####################*/
    public final String getEdgeInitialColour(){
        return this.profile.getEdge().getInitial();
    }
    public final String getEdgeHighlightColour(){
        return this.profile.getEdge().getHighlight();
    }
    public final String getEdgeHitColour(){
        return this.profile.getEdge().getHit();
    }
    public final String getEdgeSelectionColour(){
        return this.profile.getEdge().getSelection();
    }
    public final String getEdgeFlagColour() {return this.profile.getEdge().getFlag();}
    public final String getEdgeFadeoutColour(){
        return this.profile.getEdge().getFadeout();
    }
    public final String getEdgeEnrichmentColour(double p) {
        if(p<=THRESHOLD) {
            return this.edgeEnrichment.getColor(p/THRESHOLD);
        }else{
            return this.profile.getEdge().getHit();
        }
    }
    public final String getEdgeEnrichmentColour(double p, double filter) {
        if (filter > THRESHOLD) {
            if (p <= THRESHOLD) {
                return this.edgeEnrichment.getColor(p/THRESHOLD);
            } else if (p <= filter && filter != 1.0) {
                return edgeBaseGradient.getColor(p/filter);
            } else {
                return this.profile.getEdge().getHit();
            }
        } else {
            if (p <= filter) {
                return this.edgeEnrichment.getColor(p/THRESHOLD);
            } else {
                return this.profile.getEdge().getHit();
            }
        }
    }
    public final String getEdgeCoverageColour(double p) {
        return this.edgeEnrichment.getColor(p);
    }
    public final String getEdgeExpressionColour(double p, double expression, double min, double max, double filter) {
        if (filter > THRESHOLD) {
            if (p <= THRESHOLD) {
                return this.edgeExpression.getColor(expression, min, max);
            } else if (p <= filter && filter != 1.0) {
                return edgeBaseGradient.getColor(p/filter);
            } else{
                return this.profile.getEdge().getHit();
            }
        } else {
            if (p <= filter) {
                return this.edgeExpression.getColor(expression, min, max);
            } else {
                return this.profile.getEdge().getHit();
            }
        }
    }

    public final String getEdgeRegulationColour(double p, double expression, double filter) {
        if (filter > THRESHOLD) {
            if (p <= THRESHOLD) {
                return this.edgeRegulationColorMap.getColor((int) expression);
            } else if (p <= filter && filter != 1.0) {
                return edgeBaseGradient.getColor(p/filter);
            } else{
                return this.profile.getEdge().getHit();
            }
        } else {
            if (p <= filter) {
                return this.edgeRegulationColorMap.getColor((int) expression);
            } else {
                return this.profile.getEdge().getHit();
            }
        }
    }


    /*################# THUMBNAIL ##################*/
    public final String getThumbnailInitialColour(){
        return this.profile.getThumbnail().getInitial();
    }
    public final String getThumbnailHighlightColour(){
        return this.profile.getThumbnail().getHighlight();
    }
    public final String getThumbnailSelectionColour(){
        return this.profile.getThumbnail().getSelection();
    }
    public final String getThumbnailFlagColour() {
        return this.profile.getThumbnail().getFlag();
    }
}
