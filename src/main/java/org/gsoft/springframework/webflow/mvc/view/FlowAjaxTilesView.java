package org.gsoft.springframework.webflow.mvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gsoft.springframework.js.ajax.tiles3.AjaxTilesView;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;
import org.springframework.webflow.execution.View;

public class FlowAjaxTilesView extends AjaxTilesView {
	protected String[] getRenderFragments(Map model, HttpServletRequest request, HttpServletResponse response) {
		RequestContext context = RequestContextHolder.getRequestContext();
		if (context == null) {
			return super.getRenderFragments(model, request, response);
		} else {
			String[] fragments = (String[]) context.getFlashScope().get(View.RENDER_FRAGMENTS_ATTRIBUTE);
			if (fragments == null) {
				return super.getRenderFragments(model, request, response);
			}
			return fragments;
		}
	}

}
