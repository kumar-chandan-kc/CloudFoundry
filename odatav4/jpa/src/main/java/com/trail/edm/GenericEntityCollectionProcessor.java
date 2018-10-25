package com.trail.edm;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntitySet;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.commons.core.data.EntityImpl;
import org.apache.olingo.commons.core.data.EntitySetImpl;
import org.apache.olingo.commons.core.data.PropertyImpl;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.queryoption.CountOption;
import org.apache.olingo.server.api.uri.queryoption.FilterOption;
import org.apache.olingo.server.api.uri.queryoption.SelectOption;
import org.apache.olingo.server.api.uri.queryoption.SkipOption;
import org.apache.olingo.server.api.uri.queryoption.TopOption;
import org.apache.olingo.server.api.uri.queryoption.expression.Expression;
import org.apache.olingo.server.api.uri.queryoption.expression.ExpressionVisitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.trail.edm.providers.EntityProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductEntityCollectionProcessor.
 */
@Component
public class GenericEntityCollectionProcessor implements
		EntityCollectionProcessor {

	@Autowired
	private ApplicationContext ctx;

	/** The odata. */
	private OData odata;

	// our processor is initialized with the OData context object
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.olingo.server.api.processor.Processor#init(org.apache.olingo
	 * .server.api.OData, org.apache.olingo.server.api.ServiceMetadata)
	 */
	public void init(OData odata, ServiceMetadata serviceMetadata) {
		this.odata = odata;
	}

	// the only method that is declared in the EntityCollectionProcessor
	// interface
	// this method is called, when the user fires a request to an EntitySet
	// in our example, the URL would be:
	// http://localhost:8080/ExampleService1/ExampleServlet1.svc/Products
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.olingo.server.api.processor.EntityCollectionProcessor#
	 * readEntityCollection(org.apache.olingo.server.api.ODataRequest,
	 * org.apache.olingo.server.api.ODataResponse,
	 * org.apache.olingo.server.api.uri.UriInfo,
	 * org.apache.olingo.commons.api.format.ContentType)
	 */
	public void readEntityCollection(ODataRequest request,
			ODataResponse response, UriInfo uriInfo, ContentType responseFormat)
			throws ODataApplicationException, SerializerException {

		// 1st we have retrieve the requested EntitySet from the uriInfo object
		// (representation of the parsed service URI)
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths
				.get(0); // in our example, the first segment is the EntitySet
		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();
		
		
		// 2nd: fetch the data from backend for this requested EntitySetName //
		// it has to be delivered as EntitySet object
		EntitySet entitySet = getData(uriInfo);
		
		List<Entity> entityList = entitySet.getEntities();
		
		
		CountOption countOption = uriInfo.getCountOption();
	    if (countOption != null) {
	      boolean isCount = countOption.getValue();
	      if (isCount) {
	        entitySet.setCount(entitySet.getEntities().size());
	      }
	    }
	    
	    TopOption topOption = uriInfo.getTopOption();
	    if (topOption != null) {
	      int topNumber = topOption.getValue();
	      if (topNumber >= 0) {
	        if(topNumber <= entityList.size()) {
	          entityList = entityList.subList(0, topNumber);
	        }  // else the client has requested more entities than available => return what we have
	      } else {
	        throw new ODataApplicationException("Invalid value for $top", HttpStatusCode.BAD_REQUEST.getStatusCode(),
	            Locale.ROOT);
	      }
	    }
	    
	    SkipOption skipOption = uriInfo.getSkipOption();
	    if (skipOption != null) {
	        int skipNumber = skipOption.getValue();
	        if (skipNumber >= 0) {
	            if(skipNumber <= entityList.size()) {
	                entityList = entityList.subList(skipNumber, entityList.size());
	            } else {
	                // The client skipped all entities
	                entityList.clear();
	            }
	        } else {
	            throw new ODataApplicationException("Invalid value for $skip", HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ROOT);
	        }
	    }
	   
	    FilterOption filterOption = uriInfo.getFilterOption();
	    if(filterOption != null) {
	    	Expression filterExpression = filterOption.getExpression();
	    	try {
	    		
	    		  Iterator<Entity> entityIterator = entityList.iterator();

	    		  // Evaluate the expression for each entity
	    		  // If the expression is evaluated to "true", keep the entity otherwise remove it from
	    		  // the entityList
	    		  while (entityIterator.hasNext()) {
	    		    // To evaluate the the expression, create an instance of the Filter Expression
	    		    // Visitor and pass the current entity to the constructor
	    		    Entity currentEntity = entityIterator.next();
	    		    FilterExpressionVisitor expressionVisitor = new FilterExpressionVisitor(currentEntity);

	    		    // Evaluating the expression
	    		    Object visitorResult = filterExpression.accept(expressionVisitor);
	    		    // The result of the filter expression must be of type Edm.Boolean
	    		     if(visitorResult instanceof Boolean) {
	    		        if(!Boolean.TRUE.equals(visitorResult)) {
	    		          // The expression evaluated to false (or null), so we have to remove the
	    		          // currentEntity from entityList
	    		          entityIterator.remove();
	    		        }
	    		     } else {
	    		         throw new ODataApplicationException("A filter expression must evaulate to type Edm.Boolean", HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ENGLISH);
	    		     }
	    		  } // End while
	    		} catch (ExpressionVisitException e) {
	    		   throw new ODataApplicationException("Exception in filter evaluation",
	    		                 HttpStatusCode.INTERNAL_SERVER_ERROR.getStatusCode(), Locale.ENGLISH);
	    		}
	    }
	   
	    
	    
	    EdmEntityType edmEntityType = edmEntitySet.getEntityType();
	    
	    SelectOption selectOption = uriInfo.getSelectOption();
	    String selectList = odata.createUriHelper().buildContextURLSelectList(edmEntityType,
                null, selectOption);
	    
	    
	    
	    EntitySet returnEntitySet= new EntitySetImpl();
	    
	    
	 // after applying the system query options, create the EntityCollection based on the reduced list
	    for (Entity entity : entityList) {
	      returnEntitySet.getEntities().add(entity);
	    }
	    
		// 3rd: create a serializer based on the requested format (json)
		ODataFormat format = ODataFormat.fromContentType(responseFormat);
		ODataSerializer serializer = odata.createSerializer(format);

		// 4th: Now serialize the content: transform from the EntitySet object
		// to InputStream
		
		ContextURL contextUrl = ContextURL.with().entitySet(edmEntitySet).selectList(selectList)
				.build();
//		EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with()
//                .contextURL(contextUrl)
//                .id(id)
//                .count(countOption)
//                .build();
		EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions
				.with().contextURL(contextUrl).select(selectOption).build();
		
//		ContextURL contextUrl = ContextURL.with()
//                .entitySet(edmEntitySet)
//                .selectList(selectList)
//                .build();
		InputStream serializedContent = serializer.entityCollection(
				edmEntityType, returnEntitySet, opts);


		// Finally: configure the response object: set the body, headers and
		// status code
		response.setContent(serializedContent);
		response.setStatusCode(HttpStatusCode.OK.getStatusCode());
		response.setHeader(HttpHeader.CONTENT_TYPE,
				responseFormat.toContentTypeString());
	}

	/**
	 * Helper method for providing some sample data.
	 *
	 * @param edmEntitySet
	 *            for which the data is requested
	 * @return data of requested entity set
	 */
	private EntitySet getData(UriInfo uriInfo) {
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths
				.get(0); // in our example, the first segment is the EntitySet
		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		EntitySet entitySet = null;

		Map<String, EntityProvider> entityProviders = ctx
				.getBeansOfType(EntityProvider.class);

		for (String entity : entityProviders.keySet()) {
			EntityProvider entityProvider = entityProviders.get(entity);
			if (entityProvider
					.getEntityType().getName()
					
					.equals(edmEntitySet.getEntityType().getName())) {
				entitySet = entityProvider.getEntitySet(uriInfo);
				break;
			}
		}
		return entitySet;
	}

}