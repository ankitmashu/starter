package com.example.calculator.byserviceproxy.starter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

public class Calculator implements CalculationServiceInterface {

  @Override
  public void add(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler) {
    Double result = num1 + num2;
    System.out.println("add from Calculator class is called");
    resultHandler.handle(Future.succeededFuture(result));
  }

  @Override
  public void subtract(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler) {
    Double result = num1 - num2;
    System.out.println("Sub from Calculator class is called");
    resultHandler.handle(Future.succeededFuture(result));
  }

  @Override
  public void multiply(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler) {
    Double result = num1 * num2;
    System.out.println("Mul from Calculator class is called");
    resultHandler.handle(Future.succeededFuture(result));
  }

  @Override
  public void divide(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler) {
    Double result = num1 / num2;
    System.out.println("Div from Calculator class is called");
    resultHandler.handle(Future.succeededFuture(result));
  }

  @Override
  public void close() {

  }
}
