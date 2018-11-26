package chapter3.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

public class SecondInstance extends AbstractVerticle {
	private static final Logger logger = LoggerFactory.getLogger(SecondInstance.class);
	
	public static void main(String...args) {
		Vertx.clusteredVertx(new VertxOptions(), ar ->{
			if(ar.succeeded()) {
				logger.info("Second instance has been started");
				Vertx vertx = ar.result();
				vertx.deployVerticle("chapter3.HeatSensor",new DeploymentOptions().setInstances(4));
				vertx.deployVerticle("chapter3.Listener");
				vertx.deployVerticle("chapter3.SensorData");
				
				JsonObject conf = new JsonObject();				
				conf.put("port", 8081);
				
				vertx.deployVerticle("chapter3.HttpServer", new DeploymentOptions().setConfig(conf));
				
			}else {
				logger.error("Could not start", ar.cause());
			}
		});
	}
}
