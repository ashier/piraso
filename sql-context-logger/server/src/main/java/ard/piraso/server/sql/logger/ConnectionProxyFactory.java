package ard.piraso.server.sql.logger;

import ard.piraso.server.logger.MethodCallLoggerListener;
import ard.piraso.server.logger.SimpleMethodLoggerListener;
import ard.piraso.server.logger.TraceableID;
import ard.piraso.server.proxy.RegexMethodInterceptorAdapter;
import ard.piraso.server.proxy.RegexMethodInterceptorEvent;
import ard.piraso.server.proxy.RegexProxyFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * SQL connection factory, this will create the proxy instance responsible for logging sql connection specific entries.
 */
public class ConnectionProxyFactory extends AbstractSQLProxyFactory<Connection> {

    public ConnectionProxyFactory(TraceableID id) {
        super(id, new RegexProxyFactory<Connection>(Connection.class));

        if(getPref().isConnectionMethodCallEnabled()) {
            factory.addMethodListener(".*", new MethodCallLoggerListener<Connection>(id, preference));
        }

        if(getPref().isConnectionEnabled()) {
            if(!getPref().isConnectionMethodCallEnabled()) {
                factory.addMethodListener("close|commit|rollback", new SimpleMethodLoggerListener<Connection>(id));
            }

            if(!getPref().isPreparedStatementEnabled()) {
                factory.addMethodListener("prepareStatement", new PreparedStatementListener());
            }
        }
    }

    private class PreparedStatementListener extends RegexMethodInterceptorAdapter<Connection> {
        @Override
        public void afterCall(RegexMethodInterceptorEvent<Connection> evt) {
            PreparedStatement statement = (PreparedStatement) evt.getReturnedValue();
            TraceableID newId = id.create("statement-", statement.hashCode());

            newId.addProperty(Connection.class, wrappedObject);
            String sql = (String) evt.getInvocation().getArguments()[0];

            evt.setReturnedValue(new PreparedStatementProxyFactory(newId, sql).getProxy(statement));
        }
    }
}