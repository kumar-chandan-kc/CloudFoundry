package com.trail.edm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.olingo.commons.api.ODataException;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.server.api.edm.provider.EdmProvider;
import org.apache.olingo.server.api.edm.provider.EntityContainer;
import org.apache.olingo.server.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.server.api.edm.provider.EntitySet;
import org.apache.olingo.server.api.edm.provider.EntityType;
import org.apache.olingo.server.api.edm.provider.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trail.edm.providers.EntityProvider;

@Component
public class GenericEdmProvider extends EdmProvider {

	@Autowired
	private ApplicationContext ctx;

	// Service Namespace
	public static final String NAMESPACE = "com.example.model";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(
			NAMESPACE, CONTAINER_NAME);

	@Override
	public List<Schema> getSchemas() throws ODataException {

		// create Schema
		Schema schema = new Schema();
		schema.setNamespace(NAMESPACE);

		Map<String, EntityProvider> entityProviders = ctx
				.getBeansOfType(EntityProvider.class);

		// add EntityTypes
		List<EntityType> entityTypes = new ArrayList<EntityType>();
		for (String entity : entityProviders.keySet()) {

			EntityProvider entityProvider = entityProviders.get(entity);
			entityTypes.add(entityProvider.getEntityType());

		}

		schema.setEntityTypes(entityTypes);

		// add EntityContainer
		schema.setEntityContainer(getEntityContainer());

		// finally
		List<Schema> schemas = new ArrayList<Schema>();
		schemas.add(schema);

		return schemas;
	}

	@Override
	public EntityType getEntityType(FullQualifiedName entityTypeName)
			throws ODataException {

		EntityType result = null;
		Map<String, EntityProvider> entityProviders = ctx
				.getBeansOfType(EntityProvider.class);

		for (String entity : entityProviders.keySet()) {

			EntityProvider entityProvider = entityProviders.get(entity);
			EntityType entityType = entityProvider.getEntityType();
			if (entityType.getName().equals(entityTypeName.getName())) {
				result = entityType;
				break;
			}

		}
		return result;

	}

	@Override
	public EntitySet getEntitySet(FullQualifiedName entityContainer,
			String entitySetName) throws ODataException {

		EntitySet result = null;
		Map<String, EntityProvider> entityProviders = ctx
				.getBeansOfType(EntityProvider.class);

		for (String entity : entityProviders.keySet()) {

			EntityProvider entityProvider = entityProviders.get(entity);
			EntityType entityType = entityProvider.getEntityType();
			if (entityProvider.getEntitySetName().equals(entitySetName)) {
				result = new EntitySet();
				result.setName(entityProvider.getEntitySetName());
				result.setType(entityProvider.getFullyQualifiedName());
				break;
			}

		}
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.olingo.server.api.edm.provider.EdmProvider#getEntityContainer
	 * ()
	 */
	@Override
	public EntityContainer getEntityContainer() throws ODataException {

		// create EntitySets
		List<EntitySet> entitySets = new ArrayList<EntitySet>();
		
		Map<String, EntityProvider> entityProviders = ctx
				.getBeansOfType(EntityProvider.class);

		for (String entity : entityProviders.keySet()) {
			EntityProvider entityProvider = entityProviders.get(entity);
			entitySets.add(getEntitySet(CONTAINER, entityProvider.getEntitySetName()));
		}
		
		

		// create EntityContainer
		EntityContainer entityContainer = new EntityContainer();
		entityContainer.setName(CONTAINER_NAME);
		entityContainer.setEntitySets(entitySets);
		

		return entityContainer;
	}

	@Override
	public EntityContainerInfo getEntityContainerInfo(
			FullQualifiedName entityContainerName) throws ODataException {

		// This method is invoked when displaying the service document at e.g.
		// http://localhost:8080/DemoService/DemoService.svc
		if (entityContainerName == null
				|| entityContainerName.equals(CONTAINER)) {
			EntityContainerInfo entityContainerInfo = new EntityContainerInfo();
			entityContainerInfo.setContainerName(CONTAINER);
			return entityContainerInfo;
		}

		return null;
	}
}