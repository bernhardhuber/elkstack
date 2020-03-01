/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.logmanagercamel;

import java.io.Closeable;
import java.util.Hashtable;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author berni3
 * @param <T>
 */
public class NamingLookupBuilder<T> {

    private static final Logger LOG = Logger.getLogger(NamingLookupBuilder.class.getName());

    private Hashtable environment;
    private String n;

    NamingLookupBuilder environment(Hashtable environment) {
        this.environment = environment;
        return this;
    }

    NamingLookupBuilder lookup(String n) {
        this.n = n;
        return this;
    }

    Optional<T> build() {
        Optional<T> result = Optional.empty();
        try (CloseableInitialContext cic = new CloseableInitialContext(new InitialContext(environment))) {
            Object object = cic.getCtx().lookup(n);
            result = Optional.ofNullable((T) object);
        } catch (CloseableInitialContextException | NamingException ex) {
            LOG.log(Level.WARNING, "lookup", ex);
        }
        return result;
    }

    static class CloseableInitialContextException extends RuntimeException {

        CloseableInitialContextException(String m, Exception ex) {
            super(m, ex);
        }
    }

    static class CloseableInitialContext implements Closeable {

        Context ctx;

        public CloseableInitialContext(Context ctx) {
            this.ctx = ctx;
        }

        public Context getCtx() {
            return ctx;
        }

        @Override
        public void close() throws CloseableInitialContextException {
            try {
                this.ctx.close();
            } catch (NamingException ex) {
                throw new CloseableInitialContextException("close", ex);
            }
        }

    }
}
