package org.gsoft.springframework.js.ajax.tiles3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.Definition;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.jsp.JspRequest;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;
import org.springframework.js.ajax.AjaxHandler;
import org.springframework.js.ajax.SpringJavascriptAjaxHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.tiles3.TilesView;

public class AjaxTilesView extends TilesView {

    private static final String FRAGMENTS_PARAM = "fragments";

    private ApplicationContext applicationContext;

    private AjaxHandler ajaxHandler = new SpringJavascriptAjaxHandler();

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    public AjaxHandler getAjaxHandler() {
        return ajaxHandler;
    }

    public void setAjaxHandler(AjaxHandler ajaxHandler) {
        this.ajaxHandler = ajaxHandler;
    }

    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        ServletContext servletContext = getServletContext();
        if (ajaxHandler.isAjaxRequest(request, response)) {
            String[] fragmentsToRender = getRenderFragments(model, request, response);
            if (fragmentsToRender.length == 0) {
                logger.warn("An Ajax request was detected, but no fragments were specified to be re-rendered.  "
                        + "Falling back to full page render.  This can cause unpredictable results when processing "
                        + "the ajax response on the client.");
                super.renderMergedOutputModel(model, request, response);
                return;
            }

            this.applicationContext = ServletUtil.getApplicationContext(getServletContext());
            BasicTilesContainer container = (BasicTilesContainer) TilesAccess.getContainer(this.applicationContext);
            if (container == null) {
                throw new ServletException("Tiles container is not initialized. "
                        + "Have you added a TilesConfigurer to your web application context?");
            }

            exposeModelAsRequestAttributes(model, request);
            JstlUtils.exposeLocalizationContext(new RequestContext(request, servletContext));
            Request tilesRequestContext =  new ServletRequest(this.applicationContext, request, response);
            Definition compositeDefinition = container.getDefinitionsFactory().getDefinition(getUrl(),
                    tilesRequestContext);
            Map flattenedAttributeMap = new HashMap();
            flattenAttributeMap(container, tilesRequestContext, flattenedAttributeMap, compositeDefinition, request,
                    response);
            addRuntimeAttributes(container, flattenedAttributeMap, request, response);
            if (fragmentsToRender.length > 1) {
                request.setAttribute(JspRequest.FORCE_INCLUDE_ATTRIBUTE_NAME, true);
            }

            for (int i = 0; i < fragmentsToRender.length; i++) {
                Attribute attributeToRender = (Attribute) flattenedAttributeMap.get(fragmentsToRender[i]);

                if (attributeToRender == null) {
                    throw new ServletException("No tiles attribute with a name of '" + fragmentsToRender[i]
                            + "' could be found for the current view: " + this);
                } else {
                    container.startContext(tilesRequestContext).inheritCascadedAttributes(compositeDefinition);
                    container.render(attributeToRender, tilesRequestContext);
                    container.endContext(tilesRequestContext);
                }
            }
        } else {
            super.renderMergedOutputModel(model, request, response);
        }
    }

    protected String[] getRenderFragments(Map model, HttpServletRequest request, HttpServletResponse response) {
        String attrName = request.getParameter(FRAGMENTS_PARAM);
        String[] renderFragments = StringUtils.commaDelimitedListToStringArray(attrName);
        return StringUtils.trimArrayElements(renderFragments);
    }

    /**
     * <p>
     * Iterate over all attributes in the given Tiles definition. Every attribute value that represents a template (i.e.
     * start with "/") or is a nested definition is added to a Map. The method class itself recursively to traverse
     * nested definitions.
     * </p>
     * 
     * @param container the TilesContainer
     * @param requestContext the TilesRequestContext
     * @param resultMap the output Map where attributes of interest are added to.
     * @param compositeDefinition the definition to search for attributes of interest.
     * @param request the servlet request
     * @param response the servlet response
     */
    protected void flattenAttributeMap(BasicTilesContainer container, Request requestContext,
            Map resultMap, Definition compositeDefinition, HttpServletRequest request, HttpServletResponse response) {
        Set<String> locAttr = compositeDefinition.getLocalAttributeNames();
        Set<String> cascAttr = compositeDefinition.getCascadedAttributeNames();


        for (String s : locAttr) {
            String attributeName = s;
            Attribute attribute = compositeDefinition.getAttribute(attributeName);
            if (attribute.getValue() == null || !(attribute.getValue() instanceof String)) {
                continue;
            }
            String value = attribute.getValue().toString();
            if (value.startsWith("/")) {
                resultMap.put(attributeName, attribute);
            } else if (container.isValidDefinition(value, new ServletRequest(this.applicationContext, request, response))) {
                resultMap.put(attributeName, attribute);
                Definition nestedDefinition = container.getDefinitionsFactory().getDefinition(value, requestContext);
                Assert.isTrue(nestedDefinition != compositeDefinition, "Circular nested definition: " + value);
                flattenAttributeMap(container, requestContext, resultMap, nestedDefinition, request, response);
            }
        }

        if(cascAttr == null)
            return;

        for (String s : cascAttr) {
            String attributeName = s;
            System.out.println(s);
            Attribute attribute = compositeDefinition.getAttribute(attributeName);
            if (attribute.getValue() == null || !(attribute.getValue() instanceof String)) {
                continue;
            }
            String value = attribute.getValue().toString();
            if (value.startsWith("/")) {
                resultMap.put(attributeName, attribute);
            } else if (container.isValidDefinition(value, new ServletRequest(this.applicationContext, request, response))) {
                resultMap.put(attributeName, attribute);
                Definition nestedDefinition = container.getDefinitionsFactory().getDefinition(value, requestContext);
                Assert.isTrue(nestedDefinition != compositeDefinition, "Circular nested definition: " + value);
                flattenAttributeMap(container, requestContext, resultMap, nestedDefinition, request, response);
            }
        }


    }

    /**
     * <p>
     * Iterate over dynamically added Tiles attributes (see "Runtime Composition" in the Tiles documentation) and add
     * them to the output Map passed as input.
     * </p>
     * 
     * @param container the Tiles container
     * @param resultMap the output Map where attributes of interest are added to.
     * @param request the Servlet request
     * @param response the Servlet response
     */
    protected void addRuntimeAttributes(BasicTilesContainer container, Map resultMap, HttpServletRequest request,
            HttpServletResponse response) {
        AttributeContext attributeContext = container.getAttributeContext(new ServletRequest(this.applicationContext, request, response));
        Set attributeNames = new HashSet();
        if (attributeContext.getLocalAttributeNames() != null) {
            attributeNames.addAll(attributeContext.getLocalAttributeNames());
        }
        if (attributeContext.getCascadedAttributeNames() != null) {
            attributeNames.addAll(attributeContext.getCascadedAttributeNames());
        }
        Iterator iterator = attributeNames.iterator();
        while (iterator.hasNext()) {
            String name = (String) iterator.next();
            Attribute attr = attributeContext.getAttribute(name);
            resultMap.put(name, attr);
        }
    }
}