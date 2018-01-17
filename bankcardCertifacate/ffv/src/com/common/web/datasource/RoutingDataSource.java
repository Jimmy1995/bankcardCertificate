package com.common.web.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {
	protected Object determineCurrentLookupKey() {
		return DataSourceHolder.getCustomerType();
	}
}