package com.sduwh.match.sitemesh;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * Created by qxg on 17-6-30.
 */
public class DivTagRoleBundle implements TagRuleBundle {
    @Override
    public void install(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        state.addRule("mydiv", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("mydiv"), false));
        state.addRule("myscript", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("myscript"), false));
        state.addRule("mylink", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("mylink"), false));
    }

    @Override
    public void cleanUp(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {

    }
}
