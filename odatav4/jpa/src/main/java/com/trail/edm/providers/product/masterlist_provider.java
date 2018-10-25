package com.trail.edm.providers.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntitySet;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.core.data.EntityImpl;
import org.apache.olingo.commons.core.data.EntitySetImpl;
import org.apache.olingo.commons.core.data.PropertyImpl;
import org.apache.olingo.server.api.edm.provider.EntityType;
import org.apache.olingo.server.api.edm.provider.Property;
import org.apache.olingo.server.api.edm.provider.PropertyRef;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trail.edm.providers.EntityProvider;
import com.trail.files.masterService;

/**
 * @author chandankakani
 *
 */
@Component
public class masterlist_provider implements EntityProvider {
	@Autowired
	private masterService ms;
	// Service Namespace
	public static final String NAMESPACE = "com.example.model";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(
			NAMESPACE, CONTAINER_NAME);

	// Entity Types Names
	public static final String ET_CATEGORY_NAME = "Sot";
	public static final FullQualifiedName ET_CATEGORY_FQN = new FullQualifiedName(
			NAMESPACE, ET_CATEGORY_NAME);

	// Entity Set Names
	public static final String ES_CATEGORIES_NAME = "Sots";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rohitghatol.spring.odata.edm.providers.EntityProvider#getEntityType()
	 */
	@Override
	public EntityType getEntityType() {
		Property sot = new Property().setName("SOT").setType(
				EdmPrimitiveTypeKind.String.getFullQualifiedName());
		Property desc = new Property().setName("Description").setType(
				EdmPrimitiveTypeKind.String.getFullQualifiedName());

		// create PropertyRef for Key element
		PropertyRef propertyRef = new PropertyRef();
		propertyRef.setPropertyName("SOT");

		// configure EntityType
		EntityType entityType = new EntityType();
		entityType.setName(ET_CATEGORY_NAME);
		entityType.setProperties(Arrays.asList(sot, desc));
		entityType.setKey(Arrays.asList(propertyRef));
		

		return entityType;
	}

	private EntitySet getData(EdmEntitySet edmEntitySet) {

		org.apache.olingo.commons.api.data.EntitySet entitySet = new EntitySetImpl();

		List<org.apache.olingo.commons.api.data.Entity> entityList = entitySet.getEntities();

		// add some sample product entities
		ArrayList x = (ArrayList) ms.getSOT();
		
		for(int i=0;i<x.size();i++)
		{
			entityList
			.add(new EntityImpl()
					.addProperty(
							new PropertyImpl(null, "SOT",
									ValueType.PRIMITIVE, ms.getSOT().get(i).getSot()))
					.addProperty(
							new PropertyImpl(null, "Description",
									ValueType.PRIMITIVE,
									ms.getSOT().get(i).getDesc())));
		}
		
		return entitySet;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rohitghatol.spring.odata.edm.providers.EntityProvider#getEntitySet
	 * (org.apache.olingo.server.api.uri.UriInfo)
	 */
	@Override
	public EntitySet getEntitySet(UriInfo uriInfo) {
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();

		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths
				.get(0); // in our example, the first segment is the EntitySet

		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		EntitySet entitySet = getData(edmEntitySet);

		return entitySet;
	}

	/**
	 * Helper method for providing some sample data.
	 *
	 * @param edmEntitySet
	 *            for which the data is requested
	 * @return data of requested entity set
	 */
	

	@Override
	public String getEntitySetName() {		
		return ES_CATEGORIES_NAME;
	}

	/* (non-Javadoc)
	 * @see com.rohitghatol.spring.odata.edm.providers.EntityProvider#getFullyQualifiedName()
	 */
	@Override
	public FullQualifiedName getFullyQualifiedName() {
		return ET_CATEGORY_FQN;
	}

}
