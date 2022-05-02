package com.example.calculator.byserviceproxy.starter;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@VertxGen
@ProxyGen

public interface CalculationServiceInterface {

  void add(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler);
  void subtract(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler);
  void  multiply(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler);
  void divide(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler);

  static CalculationServiceInterface create(){
    return new Calculator();
    }
 @GenIgnore
    static CalculationServiceInterface createProxy(Vertx vertx, String address){
    return new CalculationServiceInterfaceVertxEBProxy(vertx, address);
  //return createProxy(vertx,address);
//return ProxyHelper.createProxy(VertxInterface.class, vertx, address);
    }

    @ProxyClose
  void close();
}
