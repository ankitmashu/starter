package com.example.calculator.byserviceproxy.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.serviceproxy.ServiceProxyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputVerticle extends AbstractVerticle {

  public static final Logger LOG = LoggerFactory.getLogger(OutputVerticle.class);


  Router router= Router.router(vertx);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    router.get("/add").handler(BodyHandler.create()).handler(this::add);
    router.get("/sub").handler(BodyHandler.create()).handler(this::sub);
    router.get("/mul").handler(BodyHandler.create()).handler(this::mul);
    router.get("/div").handler(BodyHandler.create()).handler(this::div);

    ServiceProxyBuilder builder= new ServiceProxyBuilder(vertx)
      .setAddress(MainVerticle.address);
    CalculationServiceInterface calculationServiceInterface = builder.build(CalculationServiceInterface.class);

    vertx.createHttpServer()
      .requestHandler(router)
      .exceptionHandler(error->{
        LOG.error("HTTP Server Error : ", error);
      })
        .listen(8888, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(http.cause());
        }
      });
  }

  private void div(RoutingContext context) {
    ServiceProxyBuilder builder= new ServiceProxyBuilder(vertx)
      .setAddress(MainVerticle.address);
    CalculationServiceInterface calculationServiceInterface = builder.build(CalculationServiceInterface.class);

    LOG.info("Inside div method from {} ", OutputVerticle.class.getName());
    JsonObject jsonObject = context.getBodyAsJson();

    String num1 = jsonObject.getString("num1");
    String num2 = jsonObject.getString("num2");
    double num1_double = Double.parseDouble(num1);
    double num2_double = Double.parseDouble(num2);

    JsonObject json_result = new JsonObject();
    if(num2_double == 0.0)
    {
      context.response().setStatusCode(400).setStatusMessage("Bad Request").end("Can't divide by zero");
//        response().end("Can't divide by zero ");
      LOG.error("Division by zero error ");
      return;
    }
    calculationServiceInterface.divide(num1_double, num2_double, div1 -> {
      if(div1.failed())
      {
        LOG.error("Can't divide :( becuase ", div1.cause());
        return;
      }
      json_result.put("Division_result", div1.result());
      context.response().end(json_result.encode());
    });
  }

  private void mul(RoutingContext context) {
    ServiceProxyBuilder builder= new ServiceProxyBuilder(vertx)
      .setAddress(MainVerticle.address);
    CalculationServiceInterface calculationServiceInterface = builder.build(CalculationServiceInterface.class);

    LOG.info("Inside the mul method from {}", OutputVerticle.class.getName());
    JsonObject jsonObject =  context.getBodyAsJson();
    String num1 = jsonObject.getString("num1");
    String num2 = jsonObject.getString("num2");
    double num1_double = Double.parseDouble(num1);
    double num2_double = Double.parseDouble(num2);
    JsonObject json_result = new JsonObject();

    calculationServiceInterface.multiply(num1_double, num2_double, mul1 -> {
      if(mul1.failed())
      {
        LOG.error("Can't multiply :( because ", mul1.cause());
        return;
      }
      json_result.put("Multiply_result ", mul1.result());
//      routingContext.response().end("Hello from multiply || "+ mul1.result());
      context.response().end(json_result.encode());
    });
  }

  private void sub(RoutingContext routingContext) {
    ServiceProxyBuilder builder= new ServiceProxyBuilder(vertx)
      .setAddress(MainVerticle.address);
    CalculationServiceInterface calculationServiceInterface = builder.build(CalculationServiceInterface.class);

    LOG.info("Inside the sub method from {}", OutputVerticle.class.getName());
    JsonObject jsonObject = routingContext.getBodyAsJson();
    String num1 = jsonObject.getString("num1");
    String num2 = jsonObject.getString("num2");
    double num1_double = Double.parseDouble(num1);
    double num2_double = Double.parseDouble(num2);
    JsonObject json_result = new JsonObject();


    calculationServiceInterface.subtract(num1_double, num2_double, sub1 -> {
      if(sub1.failed())
      {
        LOG.error("Can't subtract :( because ", sub1.cause());
        return;
      }
      json_result.put("Subtract_result ", sub1.result());
      routingContext.response().end(json_result.encode());
    });
  }

  private void add(RoutingContext routingContext) {
    ServiceProxyBuilder builder= new ServiceProxyBuilder(vertx)
      .setAddress(MainVerticle.address);
    CalculationServiceInterface calculationServiceInterface = builder.build(CalculationServiceInterface.class);

    LOG.info("Inside the add method from {}", OutputVerticle.class.getName());
    JsonObject jsonObject =  routingContext.getBodyAsJson();
    String num1 = jsonObject.getString("num1");
    String num2 = jsonObject.getString("num2");
    double num1_double = Double.parseDouble(num1);
    double num2_double = Double.parseDouble(num2);
    JsonObject json_result = new JsonObject();

    calculationServiceInterface.add(num1_double, num2_double, add1 -> {
      if(add1.failed())
      {
        LOG.error("Can't multiply :( because ", add1.cause());
        return;
      }
      json_result.put("Add_result ", add1.result());
      routingContext.response().end(json_result.encode());
    });
  }
}
