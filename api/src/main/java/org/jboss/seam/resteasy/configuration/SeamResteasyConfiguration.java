package org.jboss.seam.resteasy.configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

/**
 * Holds configuration options for seam-resteasy extension. It can be used to configure the extension via XML descriptor
 * using seam-xml extension. Alternatively, you can configure the extension programatically by providing an 
 * @{link @Alternative} subclass of SeamResteasyConfiguration.
 * 
 * This class allows declarative exception mapping to be used. The way exceptions are treated in the application is based on
 * what @{link {@link #getExceptionMappings()} returns. Override this method or use seam-xml module to set up exception mapping
 * declaratively.
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@ApplicationScoped
public class SeamResteasyConfiguration
{
   private Set<Class<?>> resources = new HashSet<Class<?>>();
   private Set<Class<?>> excludedResources = new HashSet<Class<?>>();
   private Set<Class<?>> providers = new HashSet<Class<?>>();

   private Map<String, String> mediaTypeMappings = new HashMap<String, String>();
   private Map<String, String> languageMappings = new HashMap<String, String>();

   private Map<Class<? extends Throwable>, Integer> exceptionMappings = new HashMap<Class<? extends Throwable>, Integer>();
   
   private boolean registerValidationExceptionMapper = true;

   /**
    * Returns a set of resource classes to be registered. 
    */
   public Set<Class<?>> getResources()
   {
      return resources;
   }

   public void setResources(Set<Class<?>> resources)
   {
      this.resources = resources;
   }
   
   /**
    * Returns a set of resource classes that will be excluded from deployment. 
    */
   public Set<Class<?>> getExcludedResources()
   {
      return excludedResources;
   }

   public void setExcludedResources(Set<Class<?>> excludedResources)
   {
      this.excludedResources = excludedResources;
   }

   /**
    * Returns a set of provider classes to be registered. 
    */
   public Set<Class<?>> getProviders()
   {
      return providers;
   }

   public void setProviders(Set<Class<?>> providers)
   {
      this.providers = providers;
   }

   /**
    * Returns a map of media type mappings to be registered. 
    */
   public Map<String, String> getMediaTypeMappings()
   {
      return mediaTypeMappings;
   }

   
   public void setMediaTypeMappings(Map<String, String> mediaTypeMappings)
   {
      this.mediaTypeMappings = mediaTypeMappings;
   }

   /**
    * Returns a map of language mappings to be registered. 
    */
   public Map<String, String> getLanguageMappings()
   {
      return languageMappings;
   }

   public void setLanguageMappings(Map<String, String> languageMappings)
   {
      this.languageMappings = languageMappings;
   }

   /**
    * Returns a map of exception mappings. 
    */
   public Map<Class<? extends Throwable>, Integer> getExceptionMappings()
   {
      return exceptionMappings;
   }

   public void setExceptionMappings(Map<Class<? extends Throwable>, Integer> exceptionMapping)
   {
      this.exceptionMappings = exceptionMapping;
   }

   /**
    * If set to true, the default exception mapper for {@link org.jboss.seam.resteasy.validation.ValidationException} will be registered. 
    */
   public boolean isRegisterValidationExceptionMapper()
   {
      return registerValidationExceptionMapper;
   }

   public void setRegisterValidationExceptionMapper(boolean registerValidationExceptionMapper)
   {
      this.registerValidationExceptionMapper = registerValidationExceptionMapper;
   }
}
