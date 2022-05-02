package com.example.calculator.byserviceproxy.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  public static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  static final String address= "com.example.calculator";

  CalculationServiceInterface calculationServiceInterface = new Calculator();
  public static void main(String[] args) {
    Vertx vertx= Vertx.vertx();
    vertx.exceptionHandler(error->{
      LOG.error("Unhandled Error:: ", error);
    });

    vertx.deployVerticle(new MainVerticle(),deployed->{
      if(deployed.failed()){
        LOG.error("Failed to deploy Main Verticle: ", deployed.cause());
        return;
      }
      LOG.info("Deployed Successfully {} !", MainVerticle.class.getName());
    });
    vertx.deployVerticle(new OutputVerticle(),deployed->{
      if(deployed.failed()){
        LOG.error("Failed to deploy Main Verticle: ", deployed.cause());
        return;
      }
      LOG.info("Deployed Successfully {} !", OutputVerticle.class.getName());
    });
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {

   // Calculator calculator=new Calculator();
   // ProxyHelper.registerService(VertxInterface.class,vertx,calculator,address);
    new ServiceBinder(vertx)
      .setAddress(address)
        .register(CalculationServiceInterface.class, calculationServiceInterface);
    startPromise.complete();
  }
}
