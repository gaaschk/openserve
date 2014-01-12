package org.gsoft.openserv.util.support.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.ConversionServiceFactory;
import org.springframework.stereotype.Component;

@Component
public class ContextScanningFormattingConversionServiceFactoryBean implements BeanPostProcessor{
	private List<ConversionService> conversionServices = new ArrayList<>();
	@SuppressWarnings("rawtypes")
	private HashSet<Converter> converters = new HashSet<>();
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if(bean instanceof Converter){
			this.converters.add((Converter)bean);
			if(this.conversionServices.size() > 0){	
				Set<Converter> converterSet = new HashSet<Converter>();
				converterSet.add((Converter)bean);
				for(ConversionService convServ:conversionServices){
					System.out.println("****************Adding converter [" + beanName + "] to ConversionService [" + convServ.getClass().getName() + "]");
					ConversionServiceFactory.registerConverters(converterSet, (ConverterRegistry)convServ);
				}
			}
		}
		else if(bean instanceof ConversionService){
			this.conversionServices.add((ConversionService)bean);
			System.out.println("******************");
			System.out.println("Adding Custom Converters to ConversionService [" + beanName + "]");
			System.out.println("******************");
			for(Converter conv:converters){
				System.out.println("****************Adding converter [" + conv.getClass().getName() + "] to ConversionService [" + beanName + "]");
			}
			ConversionServiceFactory.registerConverters(converters, (ConverterRegistry)bean);
		}
		return bean;
	}
}
