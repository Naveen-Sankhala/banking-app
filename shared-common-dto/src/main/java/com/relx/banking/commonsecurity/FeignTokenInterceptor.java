/**
 * 
 */
package com.relx.banking.commonsecurity;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author Naveen.Sankhala
 * Jan 6, 2026
 */

public class FeignTokenInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {

		ServletRequestAttributes attrs =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attrs == null) 
			return;

		   String header =
		      attrs.getRequest().getHeader("Authorization");

		if (header != null) {
			template.header("Authorization", header);
		}
	}

}
