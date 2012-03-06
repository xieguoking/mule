/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.el;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.expression.ExpressionRuntimeException;

import java.util.Map;

/**
 * Allows for the execution of expressions within Mule using an expression language. Expression language
 * implementations should not only wrap an expression language runtime but populate the context such that
 * expression can be used to access, and optionally mutate, both Mule configuration and the current message
 * being processed via expression variables. Runtime exceptions should be caught and wrapped in a Mule
 * {@link ExpressionRuntimeException} before being re-thrown.
 * 
 * @since 3.3.0
 */
public interface ExpressionLanguage
{

    /**
     * Execute the expression returning the result. The expression will be executed by the expression language
     * implementation without making any event or message context available.
     * 
     * @param <T> the return type expected
     * @param expression the expression to be executed
     * @return the result of execution of the expression.
     */
    <T> T execute(String expression);

    /**
     * Execute the expression returning the result. The expression will be executed by the expression language
     * implementation without making any event or message context available. A Map of variables can be
     * provided that will be able to the expression when executed. Variable provided in the map will only be
     * available if there are no conflicts with context variables provided by the expression language
     * implementation.
     * 
     * @param <T> the return type expected
     * @param expression the expression to be executed
     * @param vars a map of expression variables
     * @return the result of execution of the expression.
     */
    <T> T execute(String expression, Map<String, ? extends Object> vars);

    /**
     * Execute the expression returning the result. The expression will be executed with MuleEvent context,
     * meaning the expression language implementation should provided access to the message.
     * 
     * @param <T> the return type expected
     * @param expression the expression to be executed
     * @param event the current event being processed
     * @return the result of execution of the expression.
     */
    <T> T execute(String expression, MuleEvent event);

    /**
     * Execute the expression returning the result. The expression will be executed with MuleEvent context,
     * meaning the expression language implementation should provided access to the message. A Map of
     * variables can be provided that will be able to the expression when executed. Variable provided in the
     * map will only be available if there are no conflict with context variables provided by the expression
     * language implementation.
     * 
     * @param <T> the return type expected
     * @param expression the expression to be executed
     * @param event the current event being processed
     * @param vars a map of expression variables
     * @return the result of execution of the expression.
     */
    <T> T execute(String expression, MuleEvent event, Map<String, ? extends Object> vars);

    /**
     * Validates the expression. All implementors should should validate expression syntactically. Semantic
     * validation is optional.
     * 
     * @param expression
     * @return
     */
    boolean isValid(String expression);

    /**
     * Required to provide gradual migration to use of MuleEvent signatures
     * 
     * @see ExpressionLanguage#execute(String, MuleEvent)
     */
    @Deprecated
    <T> T execute(String expression, MuleMessage message);

}