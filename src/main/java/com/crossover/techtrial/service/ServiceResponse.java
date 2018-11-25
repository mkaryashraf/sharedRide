/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossover.techtrial.service;


import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Modified version of Optional to add response message
 * @author Makary Ashraf
 * @param <T>
 */
public final class ServiceResponse<T> {
    private static final ServiceResponse<?> EMPTY = new ServiceResponse<>();
    
    private final T value;
    private String responseMessage; 

    private ServiceResponse() {
        this.responseMessage = "";
        this.value = null;
    }
    
    public static<T> ServiceResponse<T> empty() {
        @SuppressWarnings("unchecked")
        ServiceResponse<T> t = (ServiceResponse<T>) EMPTY;
        return t;
    }
    
    private ServiceResponse(T value) {
        this.responseMessage = "";
        this.value = Objects.requireNonNull(value);
    }
    
    public static <T> ServiceResponse<T> of(T value) {
        return new ServiceResponse<>(value);
    }
    
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * @return the responseMessage
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * @param responseMessage the responseMessage to set
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ServiceResponse)) {
            return false;
        }

        ServiceResponse<?> other = (ServiceResponse<?>) obj;
        return Objects.equals(value, other.value);
    }
    
    
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
    
    @Override
    public String toString() {
        return value != null
            ? String.format("Optional[%s]", value)
            : "Optional.empty";
    }
}
