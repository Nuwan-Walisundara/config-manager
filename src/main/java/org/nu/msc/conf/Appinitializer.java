package org.nu.msc.conf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nu.msc.model.ConfigDTO;
import org.nu.msc.service.ConfigService;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.jetty.HttpsConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerDropwizard;

public class Appinitializer extends Application<ConfigDTO> {

	private final SwaggerDropwizard swaggerDropwizard = new SwaggerDropwizard();

	@Override
	public void initialize(Bootstrap<ConfigDTO> bootstrap) {
		swaggerDropwizard.onInitialize(bootstrap);
	}

	@Override
	public void run(ConfigDTO arg0, Environment env) throws Exception {

		env.jersey().register(new ConfigService());
		
		/**
		 * initialize configuration reading
		 */
		ConfigReader.init(arg0, env);
		
		HttpConnectorFactory connector = getHttpConnectionFactory(arg0);

		swaggerDropwizard.onRun(arg0, env, connector.getBindHost(), connector.getPort());

	}

	public static void main(String[] args) {
		try {
			new Appinitializer().run(args);
		} catch (Exception e) {
			System.out.println(	"Unable to start the server " + e.getMessage());
		} 
	}

	private HttpConnectorFactory getHttpConnectionFactory(Configuration configuration) {
		List<ConnectorFactory> connectorFactories = getConnectorFactories(configuration);
		for (ConnectorFactory connectorFactory : connectorFactories) {
			if (connectorFactory instanceof HttpsConnectorFactory) {
				return (HttpConnectorFactory) connectorFactory; // if we find 
																// https skip
																// the others
			}
		}
		for (ConnectorFactory connectorFactory : connectorFactories) {
			if (connectorFactory instanceof HttpConnectorFactory) {
				return (HttpConnectorFactory) connectorFactory; // if not https
																// pick http
			}
		}

		throw new IllegalStateException("Unable to find an HttpServerFactory");
	}

	private List<ConnectorFactory> getConnectorFactories(Configuration configuration) {
		ServerFactory serverFactory = configuration.getServerFactory();
		if (serverFactory instanceof SimpleServerFactory) {
			return Collections.singletonList(((SimpleServerFactory) serverFactory).getConnector());
		} else if (serverFactory instanceof DefaultServerFactory) {
			return new ArrayList<>(((DefaultServerFactory) serverFactory).getApplicationConnectors());
		} else {
			throw new IllegalStateException("Unknown ServerFactory implementation: " + serverFactory.getClass());
		}
	}
}
