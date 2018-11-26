package chapter3.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class FirstInstance {
	
	private static Logger logger = LoggerFactory.getLogger(FirstInstance.class);

	public static void main(String[] args) {
		Vertx.clusteredVertx(new VertxOptions(), ar ->{
			if(ar.succeeded()) {
				logger.info("First instance has been started");
				Vertx vertx = ar.result();
				vertx.deployVerticle("chapter3.HeatSensor", new DeploymentOptions().setInstances(4));
				vertx.deployVerticle("chapter3.HttpServer");
			}else {
				logger.error("Could not start", ar.cause() );
			}
		});
	}

}
